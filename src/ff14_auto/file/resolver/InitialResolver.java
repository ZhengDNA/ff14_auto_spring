package ff14_auto.file.resolver;

import ff14_auto.entity.MusicEntity;
import ff14_auto.entity.NoteEntity;
import ff14_auto.exceptions.ResolveException;
import ff14_auto.util.ResolveUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:35
 */

@Component
public class InitialResolver implements FileResolver {
//    @Autowired
//    private MusicEntity musicEntity;

    @Override
    public boolean check(char firstChar) {
        return firstChar < 'H' && firstChar > 'A' - 1;
    }

    @Override
    public void resolve(File file) throws Exception {
        musicEntity.init();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            ResolveUtil.resolveClef(scanner.nextLine());
            ResolveUtil.resolveBPM(scanner.nextLine());
            resolveNotes(scanner.nextLine());
        }
        scanner.close();
    }


    public void resolveNotes(String notes) throws ResolveException {
        final String[] noteStrs = notes.split(" ");
        for (String noteStr : noteStrs) {
            final List<Integer> res = ResolveUtil.resolveNote(noteStr, musicEntity.getBpm());
            int time = res.get(0);
            int pitch = res.get(1);
            int multipleNoteCount = res.get(2);
            musicEntity.addNote(new NoteEntity(time - multipleNoteCount * 20 > 20 ? time - multipleNoteCount : time, musicEntity.getClef() + pitch));
        }
    }
}
