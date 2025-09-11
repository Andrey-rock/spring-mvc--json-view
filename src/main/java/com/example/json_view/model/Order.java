package com.example.json_view.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @JsonView(Views.UserSummary.class)
    private int id;
    @JsonView(Views.UserSummary.class)
    private List<String> products;
    @JsonView(Views.UserSummary.class)
    private double totalSum;
    @JsonView(Views.UserSummary.class)
    private String status;
}
