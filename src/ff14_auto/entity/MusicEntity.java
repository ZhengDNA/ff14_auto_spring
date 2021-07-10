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
    private List<NoteEntity> notes;

    public static final int SLEEP = -1;

    public static final int BPM = 60000;

    private int bpm;

    private int clef;

    public void init() {
        notes = new ArrayList<>();
    }

    public boolean ready() {
        return notes != null && notes.size() != 0;
    }

    public void addNote(NoteEntity noteEntity){
        notes.add(noteEntity);
    }

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getClef() {
        return clef;
    }

    public void setClef(int clef) {
        this.clef = clef;
    }
}
