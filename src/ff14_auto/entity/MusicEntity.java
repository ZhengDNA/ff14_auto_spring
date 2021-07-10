package ff14_auto.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:36
 */

@Component
public class MusicEntity {
    private List<NoteEntity> nodes;

    private int BPM;

    private int pitch;

    public void init() {
        nodes = new ArrayList<>();
    }

    public List<NoteEntity> getNodes() {
        return nodes;
    }

    public boolean ready() {
        return nodes != null && nodes.size() != 0;
    }

    public int getBPM() {
        return BPM;
    }

    public void setBPM(int BPM) {
        this.BPM = BPM;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }
}
