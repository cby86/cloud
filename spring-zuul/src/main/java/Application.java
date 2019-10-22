import com.spring.cloud.AuthPreRequestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages  = {"com.spring.cloud"})
@EnableZuulProxy
@EnableEurekaClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AuthPreRequestFilter authPreRequestFilter() {
        return new AuthPreRequestFilter();
    }
}