package br.com.fiap.trafficManagement.controller;

import br.com.fiap.trafficManagement.dto.UserExhibitionDto;
import br.com.fiap.trafficManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserExhibitionDto> queryAllUsers(Pageable page) {
        return service.queryAllUsers(page);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserExhibitionDto queryById(@PathVariable Long id) {
        return service.queryById(id);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

}
