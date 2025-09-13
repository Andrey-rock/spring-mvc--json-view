package com.example.json_view;

import com.example.json_view.controller.UserController;
import com.example.json_view.model.Order;
import com.example.json_view.model.User;
import com.example.json_view.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;


import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Тесты наиболее значимых ситуаций.
Написаны на контроллеры, т.к. необходимо проверить как объекты преобразуются в json
в зависимости от контекста запроса.
Учтены кейсы с представлениями и возможными ошибками.
 */

@WebMvcTest(UserController.class)
public class UserTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private UserService userService;

    List<String> products = List.of("кроссовки", "шорты", "часы");

    Order order1 = new Order(1, products, 10000, "оплачено");
    Order order2 = new Order(2, products, 15000, "доставка");
    Order order3 = new Order(3, products, 20000, "получено");

    List<Order> orders1 = List.of(order1, order2, order3);
    List<Order> orders2 = List.of(order1, order2);
    List<Order> orders3 = List.of(order2, order3);

    User user1 = new User(1, "Иван", "ivanPupkin@gmail.com", orders1);
    User user2 = new User(2, "Дима", "Dmitriy@mail.ru", orders2);
    User user3 = new User(3, "Маша", "Masha@gmail.com", orders3);

    List<User> users = List.of(user1, user2, user3);

    @Test
    void getAllUsers_ShouldReturnUsersWithoutOrders() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/users")//
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Иван"))
                .andExpect(jsonPath("$[0].email").value("ivanPupkin@gmail.com"))
                .andExpect(jsonPath("$[0].orderList").doesNotExist())
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Дима"))
                .andExpect(jsonPath("$[1].email").value("Dmitriy@mail.ru"))
                .andExpect(jsonPath("$[1].orderList").doesNotExist())
                .andExpect(jsonPath("$[2].id").value("3"))
                .andExpect(jsonPath("$[2].name").value("Маша"))
                .andExpect(jsonPath("$[2].email").value("Masha@gmail.com"))
                .andExpect(jsonPath("$[2].orderList").doesNotExist());
    }

    @Test
    void getUser_WithValidId_ShouldReturnUserWithOrders() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/user/3")//
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value("Маша"))
                .andExpect(jsonPath("$.email").value("Masha@gmail.com"))
                .andExpect(jsonPath("$.orderList[0].id").value("2"))
                .andExpect(jsonPath("$.orderList[0].totalSum").value("15000.0"))
                .andExpect(jsonPath("$.orderList[0].status").value("доставка"));
    }

    @Test
    void getUser_WithInvalidId_ShouldReturnErrorMessage() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/user/33")//
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void createUser_WithValidData_ShouldCreatedUserAndReturnMassage() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user1);

        mvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("User created"));
    }

    @Test
    void createUser_WithInvalidData_ShouldReturnErrorMessage() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        user1.setEmail("invalidEmail");
        String jsonUser = mapper.writeValueAsString(user1);

        mvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                //строка длинная, поэтому проверяем просто что она есть
                .andExpect(jsonPath("$.message").value(any(String.class)));
    }
}
