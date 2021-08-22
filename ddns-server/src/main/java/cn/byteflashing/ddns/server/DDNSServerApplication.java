package cn.byteflashing.ddns.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author leanderli
 * @see
 * @since 2021/1/29
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "cn.byteflashing.ddns")
public class DDNSServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DDNSServerApplication.class, args);
    }
}
