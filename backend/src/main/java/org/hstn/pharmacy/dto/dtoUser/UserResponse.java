package org.hstn.pharmacy.dto.dtoUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String state;
    private String photoLink;
}
