package cloud.jillion.toolkit.ddns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author leanderli
 * @see
 * @since 2021/1/29
 */
@SpringBootApplication
@EnableScheduling
public class DDNSApplication {

    public static void main(String[] args) {
        SpringApplication.run(DDNSApplication.class, args);
    }
}
