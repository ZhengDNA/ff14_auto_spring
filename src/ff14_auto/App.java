package ff14_auto;

import ff14_auto.entity.MusicEntity;
import ff14_auto.file.FileLoader;
import ff14_auto.file.resolver.FileResolver;
import ff14_auto.player.MusicPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:34
 */

@Configuration
@ComponentScan
public class App {
    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private MusicPlayer musicPlayer;

    public static ApplicationContext config() {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        // 初始化 Resolver
        List<FileResolver> resolvers = new ArrayList<>();
        for (Map.Entry entry : context.getBeansOfType(FileResolver.class).entrySet()) {
            resolvers.add((FileResolver) entry.getValue());
        }
        context.getBean("fileLoader", FileLoader.class).setResolvers(resolvers);
        // 初始化 MusicEntity
        context.getBean("musicEntity", MusicEntity.class).init();
        return context;
    }

    public void run() {

    }
}
