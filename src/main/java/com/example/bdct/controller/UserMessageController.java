package com.example.bdct.controller;

import com.example.bdct.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserMessageController {

    @Autowired
    UserMessageService userMessageService;

    @GetMapping("/v1/message")
    public ResponseEntity<String> messageUsers() {
        List<String> users = userMessageService.sendMessage();
        return ResponseEntity.ok("Message sent to user "+users);

    }
}
