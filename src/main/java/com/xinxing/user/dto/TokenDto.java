package com.xinxing.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TokenDto", description = "token信息")
public class TokenDto extends BaseDto {

    private final static long serialVersionUID = -2101772871373780847L;

    @Schema(name="token",description = "token")
    @NotBlank(message = "token不能为空")
    private String token;
}
