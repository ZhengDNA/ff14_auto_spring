package ff14_auto.player;

import ff14_auto.entity.MusicEntity;
import ff14_auto.exceptions.MusicNotReadyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author : 陈征
 * @date : 2021-07-02 19:37
 */

@Component
public class MusicPlayer extends Robot {
    @Autowired
    private MusicEntity musicEntity;

    public MusicPlayer() throws AWTException {

    }

    public void start() throws MusicNotReadyException {
        if (!musicEntity.ready()){
            throw new MusicNotReadyException("MusicEntity 异常!");
        }

    }
}
