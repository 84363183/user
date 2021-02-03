package com.xinxing.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LoginDto", description = "登录信息")
public class LoginDto extends BaseDto {

    private final static long serialVersionUID = -4084852354387866671L;

    @Schema(name="email",description = "email")
    @NotBlank(message = "email不能为空")
    @Email(message = "email格式错误")
    private String email;
    @Schema(name="password",description = "密码")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合")
    private String password;
}
