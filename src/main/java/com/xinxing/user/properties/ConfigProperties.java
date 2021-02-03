package com.xinxing.user.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = ConfigProperties.PREFIX)
public class ConfigProperties {
    public static final String PREFIX = "app.user";

    private String version;
    private String releasedAt;
}
