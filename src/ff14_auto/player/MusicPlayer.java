package ff14_auto.player;

import ff14_auto.entity.MusicEntity;
import ff14_auto.entity.NoteEntity;
import ff14_auto.exceptions.MusicNotReadyException;
import ff14_auto.exceptions.PlayException;
import ff14_auto.util.FakeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author : 陈征
 * @date : 2021-07-02 19:37
 */

@Component
public class MusicPlayer extends Robot {
    @Autowired
    private MusicEntity musicEntity;

    private final List<Character> keys = new ArrayList<>(Arrays.asList('Z', '1', 'X', '2', 'C', 'V', '3', 'B', '4', 'N', '5', 'M', 'A', '6', 'S', '7', 'D', 'F', '8', 'G', '9',
            'H', '0', 'J', 'Q', 'K', 'W', 'L', 'E', 'R', 'O', 'T', 'P', 'Y', (char) 188, 'U', 'I'));

    public MusicPlayer() throws AWTException {
    }

    public void play() throws Exception {
        if (!musicEntity.ready()) {
            throw new MusicNotReadyException("MusicEntity 异常!");
        }
        for (NoteEntity note : musicEntity.getNotes()) {
            try {
                keyPress(keys.get(note.note));
            } catch (Exception e) {
                throw new PlayException("第 " + musicEntity.searchNote(note) + " 个音符弹奏出错");
            }
            FakeTime.wakeSleep(note.time / 8 * 7);
            keyRelease(keys.get(note.note));
            FakeTime.wakeSleep(note.time / 8);
        }
        System.out.println("弹奏完成。");
    }
}
