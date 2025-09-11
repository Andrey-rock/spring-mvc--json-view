package com.example.json_view.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonView(Views.UserSummary.class)
    private int id;

    @JsonView(Views.UserSummary.class)
    private String name;

    @Email
    @JsonView(Views.UserSummary.class)
    private String email;

    @JsonView(Views.UserDetails.class)
    private List<Order> orderList;
}
