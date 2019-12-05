import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.spring.cloud"})
@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"com.spring.cloud.service.feign"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}