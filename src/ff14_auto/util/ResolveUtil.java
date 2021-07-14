package ff14_auto.util;

import ff14_auto.entity.MusicEntity;
import ff14_auto.entity.NoteEntity;
import ff14_auto.exceptions.ResolveException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 陈征
 * @date : 2021-07-14 15:31
 */

public class ResolveUtil {
    public static MusicEntity musicEntity = SpringContextUtil.getContext().getBean("musicEntity", MusicEntity.class);

    /**
     * @return [int time, int pitch, int multipleNoteCount]
     */
    public static List<Integer> resolveNote(String noteStr, int time) throws ResolveException {
        int multipleNoteCount = 0;
        int pitch = resolvePitch(noteStr.charAt(0));
        for (char noteChar : noteStr.substring(1).toCharArray()) {
            if (noteChar > '0' - 1 && noteChar < '8') {
                if (time != musicEntity.getBpm()) {
                    throw new ResolveException("第 " + (musicEntity.getNotes().size() + 1) + " 个音符解析错误: " + noteStr);
                }
                musicEntity.addNote(new NoteEntity(20, musicEntity.getClef() + pitch));
                pitch = resolvePitch(noteChar);
                multipleNoteCount += 1;
            } else {
                List<Integer> res = resolveTime(noteChar, time, pitch);
                time = res.get(0);
                pitch = res.get(1);
            }
        }
        List<Integer> res = new ArrayList<>();
        res.add(time);
        res.add(pitch);
        res.add(multipleNoteCount);
        return res;
    }

    public static void resolveClef(String str) {
        int clef = 12;
        switch (str.charAt(0)) {
            case 'D':
                clef += 2;
                break;
            case 'E':
                clef += 4;
                break;
            case 'F':
                clef += 5;
                break;
            case 'G':
                clef += 7;
                break;
            case 'A':
                clef += 9;
                break;
            case 'B':
                clef += 11;
                break;
        }
        if (str.length() > 1) {
            switch (str.charAt(1)) {
                case '#':
                    clef += 1;
                    break;
                case 'b':
                    clef -= 1;
                    break;
            }
        }
        musicEntity.setClef(clef);
    }

    public static void resolveBPM(String str) {
        musicEntity.setBpm(MusicEntity.BPM / Integer.parseInt(str));
    }

    public static int resolvePitch(char ch) {
        int res = 0;
        switch (ch) {
            case '1':
                res += 0;
                break;
            case '2':
                res += 2;
                break;
            case '3':
                res += 4;
                break;
            case '4':
                res += 5;
                break;
            case '5':
                res += 7;
                break;
            case '6':
                res += 9;
                break;
            case '7':
                res += 11;
                break;
            default:
                res = MusicEntity.SLEEP;
                break;
        }
        return res;
    }

    /**
     * @return [int time, int pitch]
     */
    public static List<Integer> resolveTime(char ch, int time, int pitch) {
        int delay = 1;
        switch (ch) {
            //上升1个八度
            case '`':
                pitch += 12;
                break;
            //下降一个八度
            case '.':
                pitch -= 12;
                break;
            case '_':
                time /= 2; //时值减半（初始为四分音符的时值）
                delay += 1;
                break;
            //时值加一个四分音符的时值
            case '-':
                time += musicEntity.getBpm();
                break;
            //升号
            case '#':
                pitch += 1;
                break;
            //降号
            case 'b':
                pitch -= 1;
                break;
            //附点音符
            case ':':
                time += musicEntity.getBpm() / (2 * delay);
                break;
        }
        List<Integer> res = new ArrayList<>();
        res.add(time);
        res.add(pitch);
        return res;
    }
}
