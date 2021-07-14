package ff14_auto.file.resolver;


import ff14_auto.entity.MusicEntity;
import ff14_auto.util.SpringContextUtil;

import java.io.File;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:35
 */

public interface FileResolver {
    MusicEntity musicEntity = SpringContextUtil.getContext().getBean("musicEntity", MusicEntity.class);

    boolean check(char firstChar);

    void resolve(File file) throws Exception;
}
