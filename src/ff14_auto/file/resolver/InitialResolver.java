package ff14_auto.file.resolver;

import ff14_auto.util.ResolveUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Scanner;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:35
 */

@Component
public class InitialResolver implements FileResolver {
    @Override
    public boolean check(char firstChar) {
        return firstChar < 'H' && firstChar > 'A' - 1;
    }

    @Override
    public boolean resolve(File file) throws Exception {
        musicEntity.init();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            ResolveUtil.resolveClef(scanner.nextLine());
            ResolveUtil.resolveBPM(scanner.nextLine());
            ResolveUtil.resolveNotes(scanner.nextLine());
        }
        scanner.close();
        return musicEntity.hasOut();
    }
}
