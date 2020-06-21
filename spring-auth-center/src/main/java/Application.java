import com.spring.cloud.support.mvc.EnableResourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.spring.cloud"})
@EnableDiscoveryClient
@EnableAuthorizationServer
@EnableFeignClients(basePackages = {"com.spring.cloud.service.feign"})
@EnableHystrix
@EnableCircuitBreaker
@EnableTransactionManagement
@EnableResourceRegister
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
