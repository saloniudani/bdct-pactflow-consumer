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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactConsumerTest
@PactTestFor(providerName = "pactflow-bdct")
public class UserServicePactTest {

    @Autowired
    private UserService userService;


    @Pact(provider = "pactflow-bdct", consumer = "pactflow-bdct-consumer")
    public RequestResponsePact getAllUsers(PactDslWithProvider builder) {
        return builder
                .uponReceiving("get all users")
                    .path("/v1/users?page=0&pageSize=2")
                    .method("GET")
                    .query("page=${page}")
                    .query("pageSize=${pageSize}")
                .willRespondWith()
                    .status(200)
                    .body(
                        new PactDslJsonBody()
                                .integerType("totalElements")
                                .integerType("numberOfElements")
                                .integerType("size")
                                .integerType("number")
                                .integerType("totalPages")
                                .eachLike("results")
                                    .stringType("userId")
                                    .stringType("name")
                                .closeObject()
                                .closeArray())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getAllUsers", pactVersion = PactSpecVersion.V3)
    void testAllProducts(MockServer mockServer) {
        userService.setBaseUrl(mockServer.getUrl());
        UserList users = userService.getAllUsers();
        //assertThat(users.getResults(), hasSize(2));
        //assertThat(users.getResults().get(0), is(equalTo(new User("jim.corebett@gmail.com","Jim Corbett"))));
    }
}
