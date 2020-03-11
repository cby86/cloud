import com.spring.cloud.support.mvc.EnableResourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages  = {"com.spring.cloud"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.spring.cloud.service.feign"})
@EnableHystrix
@EnableCircuitBreaker
@EnableResourceRegister
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}