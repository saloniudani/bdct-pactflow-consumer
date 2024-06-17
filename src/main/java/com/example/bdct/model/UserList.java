package com.example.bdct.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * UserList
 */
@Data
@AllArgsConstructor
public class UserList {


  private List<User> results;

  private Long totalElements;

  private Integer numberOfElements;

  private Integer size;

  private Integer number;

  private Integer totalPages;

}

