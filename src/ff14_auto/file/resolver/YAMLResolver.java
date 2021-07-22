package ff14_auto.file.resolver;

import ff14_auto.entity.ResourceMusicEntity;
import ff14_auto.exceptions.ResolveException;
import ff14_auto.util.ResolveUtil;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


/**
 * @author : 陈征
 * @date : 2021-07-02 19:35
 */

@Component
public class YAMLResolver implements FileResolver {
    @Override
    public boolean check(char firstChar) {
        return firstChar == '-';
    }

    @Override
    public boolean resolve(File file) throws FileNotFoundException, ResolveException {
        musicEntity.init();
        Yaml yaml = new Yaml();
        List<Map> entities = yaml.load(new FileInputStream(file));
        for (Map entity : entities) {
            ResolveUtil.resolveNotes(new ResourceMusicEntity(entity));
        }
        return musicEntity.hasOut();
    }
}
