package ff14_auto.file.resolver;

import ff14_auto.entity.MusicEntity;
import ff14_auto.entity.NoteEntity;
import ff14_auto.exceptions.ResolveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
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
    public MusicEntity resolve(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        return null;
    }

    public boolean isFF14Window() {

        return false;
    }

    public List<NoteEntity> resolveNotes(String str) {
        return null;
    }

    public void resolvePitch(String str) {
        int pitch = 12;
        switch (str.charAt(0)) {
            case 'D' -> pitch += 2;
            case 'E' -> pitch += 4;
            case 'F' -> pitch += 5;
            case 'G' -> pitch += 7;
            case 'A' -> pitch += 9;
            case 'B' -> pitch += 11;
        }
        switch (str.charAt(1)) {
            case '#' -> pitch += 1;
            case 'b' -> pitch -= 1;
        }
        musicEntity.setPitch(pitch);
    }

    public void resolveBPM(String str) {

    }
}
