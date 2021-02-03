package com.xinxing.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "HeartDto", description = "心跳信息")
public class HeartDto extends BaseDto {

    private final static long serialVersionUID = 2750093584586069737L;

    @Schema(name="version",description = "版本")
    private String version;
    @Schema(name="releasedAt",description = "发布时间")
    private String releasedAt;
}
