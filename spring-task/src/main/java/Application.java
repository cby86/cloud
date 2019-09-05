import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages  = {"com.spring.cloud"})
@EnableTask
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public TaskListener taskListener() {
        return new TaskListener();
    }
    public class TaskListener implements TaskExecutionListener {

        private final  Logger LOGGER = LoggerFactory.getLogger(TaskListener.class.getName());

        @Override
        public void onTaskEnd(TaskExecution arg0) {
            // TODO Auto-generated method stub
            LOGGER.info("End of Task");
        }

        @Override
        public void onTaskFailed(TaskExecution arg0, Throwable arg1) {
            // TODO Auto-generated method stub
            LOGGER.info("Task Fail");
        }

        @Override
        public void onTaskStartup(TaskExecution arg0) {
            // TODO Auto-generated method stub
            LOGGER.info("Task Startup");
        }
    }

}