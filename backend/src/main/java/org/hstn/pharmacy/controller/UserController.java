package org.hstn.pharmacy.controller;

import lombok.extern.slf4j.Slf4j;
import org.hstn.pharmacy.controller.api.UserApi;
import org.hstn.pharmacy.dto.dtoUser.UpdateUserRequest;
import org.hstn.pharmacy.dto.dtoUser.UserResponse;
import org.hstn.pharmacy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Оновлення даних поточного користувача
    @Override
    @PutMapping("/user/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        try {
            Integer currentUserId = getCurrentUserId();
            UserResponse updatedUser = userService.updateUser(currentUserId, request);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Failed to update user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse(null, "Error", "Error", "error@error.com",
                            "USER", "ERROR", null));
        }
    }

    // Оновлення фото користувача
    @PutMapping("/user/updatePhoto")
    public ResponseEntity<UserResponse> updatePhoto(@RequestParam Integer userId,
                                                    @RequestParam String photoLink) {
        try {
            UserResponse updatedUser = userService.updatePhotoLink(userId, photoLink);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Failed to update photo for user id: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Отримання всіх користувачів (тільки для адмінів)
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Failed to get users list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Отримання конкретного користувача по ID (тільки для адмінів)
    @GetMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        try {
            UserResponse user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Failed to get user with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Видалення користувача (тільки для адмінів)
    @DeleteMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to delete user with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Оновлення даних користувача адміном
    @PutMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> adminUpdateUser(@PathVariable Integer id,
                                                        @RequestBody UpdateUserRequest request) {
        try {
            UserResponse updatedUser = userService.updateUser(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Admin failed to update user with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            UserResponse userResponse = userService.getUserByEmail(username);
            return userResponse != null ? userResponse.getId() : null;
        }
        throw new IllegalStateException("User not authenticated");
    }
}