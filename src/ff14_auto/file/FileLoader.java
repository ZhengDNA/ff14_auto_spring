package ff14_auto.file;

import ff14_auto.entity.MusicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:34
 */

@Component
public class FileLoader {
    @Autowired
    private MusicEntity musicEntity;
    public void printSth(){
        assert musicEntity!=null;
    }
}
