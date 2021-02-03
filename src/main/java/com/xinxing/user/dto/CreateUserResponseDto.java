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
@Schema(name="CreateUserResponseDto",description = "创建用户返回信息")
public class CreateUserResponseDto extends BaseDto{

    private final static long serialVersionUID = -8980846675114896389L;
    private String id;
}
