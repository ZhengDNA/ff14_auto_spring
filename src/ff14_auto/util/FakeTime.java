package ff14_auto.util;

/**
 * @author : 陈征
 * @date : 2021-07-10 23:06
 */

public class FakeTime {
    public static void wakeSleep(int milliSec) {
        long start = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - start > milliSec) {
                break;
            }
        }
    }

    public static void count(int times, boolean reverse) {
        System.out.println((reverse ? "倒" : "正") + "数: " + times);
        int i = 0;
        final String backSpaces = "\b".repeat(String.valueOf(times).length());
        while (i < times) {
            System.out.print(backSpaces + (reverse ? times - i : i + 1));
            try {
                Thread.sleep(999);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i += 1;
        }
        System.out.println((reverse ? "\t倒" : "\t正") + "数结束");
    }

    public static void count(int times) {
        count(times, false);
    }
}
