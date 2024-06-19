package com.example.bdct;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.bdct.model.User;
import com.example.bdct.model.UserList;
import com.example.bdct.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactConsumerTest
@PactTestFor(providerName = "bdct-pactflow-provider")
@Slf4j
public class UserServicePactTest {

    @Autowired
    private UserService userService;


    @Pact(provider = "bdct-pactflow-provider", consumer = "bdct-pactflow-consumer")
    public RequestResponsePact getAllUsers(PactDslWithProvider builder) {
        return builder
                .uponReceiving("get all users")
                    .path("/v1/users")
                    .method("GET")
                    .query("page=0&page_size=2")
                .willRespondWith()
                    .status(200)
                    .body(
                        new PactDslJsonBody()
                                .integerType("totalElements",10)
                                .integerType("numberOfElements",2)
                                .integerType("size",2)
                                .integerType("number",0)
                                .integerType("totalPages",5)
                                .minArrayLike("results", 1, 2)
                                .stringType("userId", "jim.corebett@gmail.com")
                                .stringType("name", "Jim Corbett")
                                .closeObject()
                                .closeArray()
                                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getAllUsers", pactVersion = PactSpecVersion.V3)
    void testAllProducts(MockServer mockServer) {
        userService.setBaseUrl(mockServer.getUrl());
        UserList users = userService.getAllUsers(0,2);
        assertThat(users.getResults(), hasSize(2));
        assertThat(users.getResults().get(0), is(equalTo(new User("jim.corebett@gmail.com","Jim Corbett"))));
    }
}
