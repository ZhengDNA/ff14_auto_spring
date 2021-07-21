package ff14_auto;

import ff14_auto.entity.MusicEntity;
import ff14_auto.exceptions.BaseCustomException;
import ff14_auto.file.FileLoader;
import ff14_auto.file.resolver.FileResolver;
import ff14_auto.player.MusicPlayer;
import ff14_auto.util.ResolveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        // 加载运行库
        System.load(new File("src" + File.separator + "ff14_auto.dll").getAbsolutePath());
        return context;
    }

    public void run() throws IOException {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("输入文件路径: ");
            final String path = input.nextLine();
            try {
                if (fileLoader.load(path)) {
                    System.out.print("有音符超出最高按键范围，是否把音符限制在最高按键范围内? (yes/no): ");
                    if ("yes".equals(input.nextLine())) {
                        ResolveUtil.limitNotes();
                    }
                }
//                FakeTime.count(5, true);
                musicPlayer.play();
            } catch (BaseCustomException | FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("出现未知错误，请截图错误报告并联系开发者");
            } finally {
                System.out.println();
            }
        }
    }
}
