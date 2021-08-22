package cn.byteflashing.ddns.core.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author leanderli
 * @see
 * @since 2021/1/28
 */
@Data
@ConfigurationProperties(prefix = AcsProperties.ACS_PROP_PREFIX)
public class AcsProperties {

    public static final String ACS_PROP_PREFIX = "ali.acs.client";

    private String regionId, accessKeyId, accessKeySecret;
}
