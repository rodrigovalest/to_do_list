package com.todolist.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String password;

//    public User toUser() {
//        User user = new User();
//        user.setUsername(this.username);
//        user.setPassword(this.password);
//
//        return user;
//    }
}
