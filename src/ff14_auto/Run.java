package ff14_auto;

import org.springframework.stereotype.Component;



/**
 * @author : 陈征
 * @date : 2021-07-02 19:34
 */

@Component
public class Run {
    public static void main(String[] args){
        App.config().getBean("app",App.class).run();
    }
}