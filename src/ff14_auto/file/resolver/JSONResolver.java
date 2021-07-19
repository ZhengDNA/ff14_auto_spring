package ff14_auto.file.resolver;

import com.alibaba.fastjson.JSON;
import ff14_auto.entity.JSONMusicEntity;
import ff14_auto.entity.NoteEntity;
import ff14_auto.exceptions.ResolveException;
import ff14_auto.util.ResolveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:35
 */

@Component
public class JSONResolver implements FileResolver {
    @Override
    public boolean check(char firstChar) {
        return firstChar == '[';
    }

    @Override
    public boolean resolve(File file) throws FileNotFoundException, ResolveException {
        final Scanner scanner = new Scanner(new FileInputStream(file));
        StringBuilder jsonStr = new StringBuilder();
        while (scanner.hasNext()) {
            jsonStr.append(scanner.nextLine());
        }
        List<JSONMusicEntity> jsonMusicEntities = JSON.parseArray(jsonStr.toString(), JSONMusicEntity.class);
        for (JSONMusicEntity jsonMusicEntity : jsonMusicEntities) {
            ResolveUtil.resolveBPM(jsonMusicEntity.getBpm());
            ResolveUtil.resolveClef(jsonMusicEntity.getClef());
            ResolveUtil.resolveNotes(jsonMusicEntity.getNotes());
        }
        return musicEntity.hasOut();
    }
}
