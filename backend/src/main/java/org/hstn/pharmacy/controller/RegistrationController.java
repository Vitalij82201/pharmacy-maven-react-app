package org.hstn.pharmacy.controller;

import org.hstn.pharmacy.controller.api.PublicApi;
import org.hstn.pharmacy.dto.dtoUser.CreateRequestUser;
import org.hstn.pharmacy.dto.dtoUser.StandardResponseDto;
import org.hstn.pharmacy.dto.dtoUser.UserResponse;
import org.hstn.pharmacy.service.ConfirmationCodeService;
import org.hstn.pharmacy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController implements PublicApi {

    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;

    public RegistrationController(UserService userService, ConfirmationCodeService confirmationCodeService) {
        this.userService = userService;
        this.confirmationCodeService = confirmationCodeService;
    }

    @Override
    public ResponseEntity<UserResponse> registerUser(CreateRequestUser request) {
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<StandardResponseDto> confirmUser(String confirmationCode) {
        return ResponseEntity.ok(userService.confirmUser(confirmationCode));
    }
}
