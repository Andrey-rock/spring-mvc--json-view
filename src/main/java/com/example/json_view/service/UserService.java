package com.example.json_view.service;

import com.example.json_view.exception.UserNoSuchException;
import com.example.json_view.model.Order;
import com.example.json_view.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class UserService {

    private final List<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    public User getUser(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(UserNoSuchException::new);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }

    public void deleteUser(int id) {
        users.removeIf(user -> user.getId() == id);
    }

    @PostConstruct
    public void init() {

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

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }
}
