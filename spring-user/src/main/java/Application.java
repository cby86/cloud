import com.spring.cloud.auto.EnableMqMessage;
import com.spring.cloud.support.mvc.EnableResourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages  = {"com.spring.cloud"})
@EnableHystrix
@EnableCircuitBreaker
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.spring.cloud.repository"})
@EntityScan(basePackages = {"com.spring.cloud.entity"})
@EnableMqMessage
@EnableResourceRegister
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
