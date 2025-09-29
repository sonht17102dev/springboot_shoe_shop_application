package com.sonht.e_commerce_webapp_spring_boot.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/*
 * Lớp RegistrationDto đại diện cho dữ liệu đăng ký người dùng với các thuộc tính như ID, 
 * mật khẩu, email, tên và số điện thoại.
 */
public class RegistrationDto {
    private Long id;
    @NotEmpty(message =  "Password không được để trống!")
    @Length(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự.")
    private String password;

    @NotEmpty(message =  "Email không được để trống!")
    @Email
    private String email;

    @NotEmpty(message =  "Tên không được để trống!")
    @NotBlank(message = "Tên không được có khoảng trắng ở đầu.") 
    private String name;

    @NotEmpty(message =  "Số điện thoại không được để trống!")
    @NotBlank(message = "Số điện thoại không được có khoảng trắng ở đầu.") 
    private String phone;
}
