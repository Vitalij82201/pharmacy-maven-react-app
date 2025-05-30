package org.hstn.pharmacy.controller;

import org.hstn.pharmacy.controller.api.AdminApi;
import org.hstn.pharmacy.dto.dtoUser.UpdateUserRequestForAdmin;
import org.hstn.pharmacy.dto.dtoUser.UserResponse;
import org.hstn.pharmacy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController implements AdminApi {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponse> updateUserForAdmin(UpdateUserRequestForAdmin request) {
        return ResponseEntity.ok(userService.updateUserForAdmin(request));
    }

    @Override
    public ResponseEntity<UserResponse> findUserById(Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserResponse> findUserById(String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @Override
    public ResponseEntity<List<UserResponse>> findUserByLastName(String lastName) {
        return ResponseEntity.ok(userService.getUsersByLastName(lastName));
    }

    @Override
    public ResponseEntity<List<UserResponse>> findUserByFullName(String firstName, String lastName) {
        return ResponseEntity.ok(userService.getUsersByFullName(firstName, lastName));
    }
}
