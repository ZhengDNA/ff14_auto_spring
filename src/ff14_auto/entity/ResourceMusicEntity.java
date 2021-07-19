package ff14_auto.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author : 陈征
 * @date : 2021-07-20 01:03
 */

@Data
@Component
public class ResourceMusicEntity {
    private int bpm;

    private String clef;

    private List<String> notes;

    public ResourceMusicEntity() {
    }

    public ResourceMusicEntity(Map entity) {
        bpm = (int) entity.get("bpm");
        clef = (String) entity.get("clef");
        notes = (List<String>) entity.get("notes");
    }
}
