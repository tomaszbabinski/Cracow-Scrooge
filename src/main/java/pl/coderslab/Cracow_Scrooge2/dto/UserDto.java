package pl.coderslab.Cracow_Scrooge2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "*Please add first name")
    private String firstName;

    @NotEmpty(message = "*Please add last name")
    private String lastName;

    @NotEmpty(message = "*Please add password")
    private String password;

    @NotEmpty(message = "*Please repeat the password")
    private String passwordRepeat;

    @NotEmpty(message = "*Please add email")
    private String email;
}
