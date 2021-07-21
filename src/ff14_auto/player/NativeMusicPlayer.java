package ff14_auto.player;

/**
 * @author : 陈征
 * @date : 2021-07-21 20:48
 */

public class NativeMusicPlayer {
    public static native void press(char keycode);

    public static native void release(char keycode);
}
