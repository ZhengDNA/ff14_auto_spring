package ff14_auto.file;

import ff14_auto.entity.MusicEntity;
import ff14_auto.exceptions.FileEmptyException;
import ff14_auto.exceptions.NoMatchResolverException;
import ff14_auto.file.resolver.FileResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:34
 */

@Component
public class FileLoader {
    private List<FileResolver> resolvers;

    public void setResolvers(List<FileResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public void printSth(String a) {
        System.out.println(a);
    }

    public void printSth() {
        assert resolvers != null;
        for (FileResolver fileResolver : resolvers) {
            System.out.println(fileResolver);
        }
    }

    public void load(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("文件未找到! \n路径: " + file.getAbsolutePath());
        }
        Reader reader = new FileReader(file);
        if (!reader.ready()) {
            throw new FileEmptyException("文件为空! \n路径: " + file.getAbsolutePath());
        }
        char c = (char) reader.read();
        while (c != ' ' && c != '\n') {
            c = (char) reader.read();
        }
        for (FileResolver resolver : resolvers) {
            if (resolver.check(c)) {
                resolver.resolve(file);
                return;
            }
        }
        throw new NoMatchResolverException("没有匹配的解析器! \n路径: " + file.getAbsolutePath());
    }
}
