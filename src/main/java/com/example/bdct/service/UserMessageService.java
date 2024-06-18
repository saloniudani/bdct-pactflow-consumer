package com.example.bdct.service;

import com.example.bdct.model.User;
import com.example.bdct.model.UserList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserMessageService {

    @Autowired
    UserService userService;

    public List<String> sendMessage() {
        UserList users = userService.getAllUsers(0,2);
        return users.getResults().stream().map(User::getName).collect(Collectors.toList());
    }
}
