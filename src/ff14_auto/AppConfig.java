package ff14_auto;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:34
 */

@Configuration
@ComponentScan
public class AppConfig {
    public ApplicationContext config(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        return context;
    }
}
