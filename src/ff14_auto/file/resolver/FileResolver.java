package ff14_auto.file.resolver;


import java.io.File;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:35
 */

public interface FileResolver {

    boolean check(char firstChar);

    void resolve(File file) throws Exception;
}
