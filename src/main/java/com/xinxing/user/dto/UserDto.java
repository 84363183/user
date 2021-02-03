package com.xinxing.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Schema(name="UserDto",description = "用户信息")
public class UserDto extends BaseDto{

    private final static long serialVersionUID = 2750093584586069737L;
    @Schema(hidden = true)
    private String id;
    @Schema(name="first",description = "名")
    @NotBlank(message = "名不能为空")
    private String first;
    @Schema(name="last",description = "姓")
    @NotBlank(message = "姓不能为空")
    private String last;
    @Schema(name="email",description = "邮件地址")
    @Email(message = "邮件格式不对")
    private String email;
    @Schema(name="password",description = "密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

}
