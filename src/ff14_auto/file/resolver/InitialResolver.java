package ff14_auto.file.resolver;

import ff14_auto.entity.MusicEntity;
import ff14_auto.entity.NoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:35
 */

@Component
public class InitialResolver implements FileResolver {
    @Autowired
    private MusicEntity musicEntity;

    @Override
    public boolean check(char firstChar) {
        return firstChar < 'H' && firstChar > 'A' - 1;
    }

    @Override
    public void resolve(File file) throws IOException {
        musicEntity.init();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            resolveClef(scanner.nextLine());
            resolveBPM(scanner.nextLine());
            resolveNotes(scanner.nextLine());
        }
        scanner.close();
    }

    private void resolveNotes(String str) {
        int multipleNoteCount = 0;
        final String[] noteStrs = str.split(" ");
        for (String noteStr : noteStrs) {
            // 音高偏移量
            int pitch;
            int time = musicEntity.getBpm();
            pitch = resolvePitch(noteStr.charAt(0));
            for (char noteChar : noteStr.substring(1).toCharArray()) {
                if (noteChar > '0' - 1 && noteChar < '8') {
                    musicEntity.addNote(new NoteEntity(20, musicEntity.getClef() + pitch));
                    pitch = resolvePitch(noteChar);
                    multipleNoteCount += 1;
                } else {
                    List<Integer> res = resolveTime(noteChar, time, pitch);
                    time = res.get(0);
                    pitch = res.get(1);
                }
            }
            musicEntity.addNote(new NoteEntity(time, musicEntity.getClef() + pitch));
            multipleNoteCount = 0;
        }
    }

    private void resolveClef(String str) {
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

    private void resolveBPM(String str) {
        musicEntity.setBpm(MusicEntity.BPM / Integer.parseInt(str));
    }

    private int resolvePitch(char ch) {
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

    private List<Integer> resolveTime(char ch, int time, int pitch) {
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
