package ff14_auto.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:36
 */

@Data
@Component
public class MusicEntity {
    private List<NoteEntity> notes;

    public static final int SLEEP = -100;

    public static final int BPM = 60000;

    private int bpm;

    private int clef;

    private int out;

    public void init() {
        notes = new ArrayList<>();
        out = 0;
    }

    public boolean hasOut() {
        return out > 0;
    }

    public boolean ready() {
        return notes != null && notes.size() != 0;
    }

    public void addNote(NoteEntity noteEntity) {
        notes.add(noteEntity);
    }
}
