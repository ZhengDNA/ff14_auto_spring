package ff14_auto.util;

import ff14_auto.entity.MusicEntity;
import ff14_auto.entity.NoteEntity;
import ff14_auto.entity.ResourceMusicEntity;
import ff14_auto.exceptions.ResolveException;
import ff14_auto.player.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 陈征
 * @date : 2021-07-14 15:31
 */

public class ResolveUtil {
    private static final MusicEntity musicEntity = SpringContextUtil.getContext().getBean("musicEntity", MusicEntity.class);

    /**
     * @return [int time, int pitch, int multipleNoteCount]
     */
    public static void resolveNote(String noteStr) throws ResolveException {
        if (noteStr.length()==0){
            throw new ResolveException("出现多余的空格！");
        }
        int time = musicEntity.getBpm();
        int multipleNoteCount = 0;
        int pitch = resolvePitch(noteStr.charAt(0));
        for (char noteChar : noteStr.substring(1).toCharArray()) {
            if (noteChar > '0' - 1 && noteChar < '8') {
                if (time != musicEntity.getBpm()) {
                    throw new ResolveException("第 " + (musicEntity.getNotes().size() + 1) + " 个音符解析错误: " + noteStr);
                }
                musicEntity.addNote(new NoteEntity(20, musicEntity.getClef() + pitch));
                int out = musicEntity.getClef() + pitch + 1 - MusicPlayer.keyNum;
                musicEntity.setOut(Math.max(out, musicEntity.getOut()));
                pitch = resolvePitch(noteChar);
                multipleNoteCount += 1;
            } else {
                List<Integer> res = resolveTime(noteChar, time, pitch);
                time = res.get(0);
                pitch = res.get(1);
            }
        }
        musicEntity.addNote(new NoteEntity(time - multipleNoteCount * 20 > 20 ? time - multipleNoteCount : time, musicEntity.getClef() + pitch));
        int out = musicEntity.getClef() + pitch + 1 - MusicPlayer.keyNum;
        musicEntity.setOut(Math.max(out, musicEntity.getOut()));
    }

    static public void resolveNotes(String notes) throws ResolveException {
        final String[] noteStrs = notes.split(" ");
        for (String noteStr : noteStrs) {
            resolveNote(noteStr);
        }
    }

    static public void resolveNotes(List<String> notes) throws ResolveException {
        for (String noteStr : notes) {
            resolveNote(noteStr);
        }
    }

    static public void resolveNotes(ResourceMusicEntity entity) throws ResolveException {
        resolveBPM(entity.getBpm());
        resolveClef(entity.getClef());
        resolveNotes(entity.getNotes());
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

    public static void resolveBPM(int bpm) {
        musicEntity.setBpm(MusicEntity.BPM / bpm);
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

    public static void limitNotes() {
        for (NoteEntity note : musicEntity.getNotes()) {
            note.note -= musicEntity.getOut();
        }
        System.out.println("已把音符限制在按键范围内。");
    }
}
