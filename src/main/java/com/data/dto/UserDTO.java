package com.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    @NotBlank(message = "Tên đăng nhập không được để trống!")
    private String username;
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Không đúng định dạng email!")
    private String email;
    private String role;
    private boolean status;
}
