package cloud.jillion.toolkit.ddns.autoconfigure;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leanderli
 * @see
 * @since 2021/1/28
 */
@Configuration
@EnableConfigurationProperties(AcsProperties.class)
public class AcsClientAutoConfiguration {

    @Bean
    public IAcsClient acsClient(AcsProperties properties) {
        IClientProfile profile = DefaultProfile.getProfile(
                properties.getRegionId(), properties.getAccessKeyId(), properties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }
}
