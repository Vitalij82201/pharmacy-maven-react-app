package org.hstn.pharmacy.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.dtoUser.*;
import org.hstn.pharmacy.entity.entityUser.ConfirmationCode;
import org.hstn.pharmacy.entity.entityUser.User;
import org.hstn.pharmacy.exceptions.NotFoundException;
import org.hstn.pharmacy.exceptions.RestException;
import org.hstn.pharmacy.mapper.UserMapper;
import org.hstn.pharmacy.repository.user.UserRepository;
import org.hstn.pharmacy.service.mail.UserMailSender;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserMailSender userMailSender;
    private final ConfirmationCodeService confirmationCodeService;


    @Transactional

    public UserResponse registerUser(CreateRequestUser request) {

        if (userRepository.existsByEmail(request.getEmail())){
            throw new RestException(HttpStatus.CONFLICT, "Пользователь с email " + request.getEmail() + " уже существует");
        }

        User user = userMapper.toEntity(request);
        user.setRole(User.Role.USER);
        user.setState(User.State.NOT_CONFIRMED);
        userRepository.save(user);

        String confirmationCode = confirmationCodeService.createConfirmationCode(user);

        userMailSender.sendEmail(user,confirmationCode);

        return userMapper.toResponse(user);
    }


    @Transactional
    public StandardResponseDto confirmUser(String confirmationCode){
        ConfirmationCode code = confirmationCodeService.findByCodeExpireDateTimeAfter(confirmationCode, LocalDateTime.now());

        User user = code.getUser();

        user.setState(User.State.CONFIRMED);

        userRepository.save(user);

        return new StandardResponseDto("Регистрация успешно завершена");

    }

    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhotoLink(request.getPhotoLink());

        User updatedUser = userRepository.save(user);
        return convertToUserResponse(updatedUser);
    }
   

    public UserResponse updateUserForAdmin(UpdateUserRequestForAdmin request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        userMapper.updateUserFromAdminDto(request, user);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public UserResponse getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToUserResponse)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public List<UserResponse> getUsersByLastName(String lastName) {
        List<User> users = userRepository.findByLastName(lastName);
        return users == null ? Collections.emptyList() : users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        // Спочатку видаляємо пов'язані коди підтвердження
        confirmationCodeService.deleteByUserId(id);

        // Потім видаляємо користувача
        userRepository.delete(user); // Краще використовувати delete(entity), ніж deleteById
    }

    public List<UserResponse> getUsersByFullName(String firstName, String lastName) {
        List<User> users = userRepository.findByFirstNameAndLastName(firstName, lastName);
        return users == null ? Collections.emptyList() : users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Использование нового метода для обновления photoLink

    public UserResponse updatePhotoLink(Integer userId, String photoLink) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setPhotoLink(photoLink);
        User updatedUser = userRepository.save(user);
        return convertToUserResponse(updatedUser);
    }
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name()) // Конвертуємо enum у String
                .state(user.getState().name()) // Конвертуємо enum у String
                .photoLink(user.getPhotoLink())
                .build();
    }


}
