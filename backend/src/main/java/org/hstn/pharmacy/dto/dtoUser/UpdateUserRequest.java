package org.hstn.pharmacy.dto.dtoUser;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotNull
    private Integer id;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]){8,}$")
    private String password;

    private String photoLink;

    @Size(min = 2, max = 20)
    private String firstName;

    @Size(min = 3, max = 25)
    private String lastName;

    @Size(min = 3, max = 100)
    private String email;
}
