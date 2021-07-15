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

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }
}
