package ff14_auto.player;

import ff14_auto.entity.MusicEntity;
import ff14_auto.entity.NoteEntity;
import ff14_auto.exceptions.MusicNotReadyException;
import ff14_auto.util.FakeTime;
import ff14_auto.util.WindowCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author : 陈征
 * @date : 2021-07-02 19:37
 */

@Component
public class MusicPlayer{
    @Autowired
    private MusicEntity musicEntity;

    private final List<Character> keys = new ArrayList<>(Arrays.asList('Z', '1', 'X', '2', 'C', 'V', '3', 'B', '4', 'N', '5', 'M', 'A', '6', 'S', '7', 'D', 'F', '8', 'G', '9',
            'H', '0', 'J', 'Q', 'K', 'W', 'L', 'E', 'R', 'O', 'T', 'P', 'Y', (char) 188, 'U', 'I'));

    public static final int keyNum = 37;

    public void play() throws Exception {
        if (!musicEntity.ready()) {
            throw new MusicNotReadyException("MusicEntity 异常!");
        }
        System.out.println("切换到最终幻想14后开始弹奏");
        while (!WindowCheck.isFF14Window()){
            Thread.sleep(100);
        }
        System.out.println("开始弹奏");
        FakeTime.wakeSleep(100);
        for (NoteEntity note : musicEntity.getNotes()) {
            try {
                NativeMusicPlayer.press(keys.get(note.note));
            } catch (Exception e) {
                FakeTime.wakeSleep(note.time);
                continue;
            }
            FakeTime.wakeSleep(note.time / 8 * 7);
            NativeMusicPlayer.release(keys.get(note.note));
            FakeTime.wakeSleep(note.time / 8);
        }
        System.out.println("弹奏完成。");
    }
}
