package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.*;

public class SnapShotAction extends MenuAction {
    public SnapShotAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return SNAPSHOT_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            world = window.getWorld();
            if (world == null) {
                createWorld();
                world.generateAdam();
                window.paint();
            }
            world.stop();
            setText(CONTINUE_TEXT);
            world.makeSnapShot();
        };
    }
}
