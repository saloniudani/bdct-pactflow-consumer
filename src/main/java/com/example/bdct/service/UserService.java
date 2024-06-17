package com.example.bdct.service;

import com.example.bdct.model.UserList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class UserService {

    String baseurl = "http://localhost:8080";

    public UserList getAllUsers() {
        RestClient restClient = RestClient.create();
        URI uri = UriComponentsBuilder.fromHttpUrl(baseurl).path("/v1/users")
                .queryParam("page", 0).queryParam("pageSize", 2).build().toUri();
        return restClient.get().uri(uri).accept(MediaType.APPLICATION_JSON).retrieve().body(UserList.class);
    }

    public void setBaseUrl(String url) {
        this.baseurl = url;
    }
}
