package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.bot.SBot;
import ru.cyberbiology.test.prototype.IWindow;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RgbSortAction extends SortAction {
    public RgbSortAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return "RGB Sort Demo";
    }

    @Override
    void sort() {
        var wd = world.matrix.length;
        var ht = world.matrix[0].length;
        List<Bot> list = new LinkedList<>();
        // collect cells
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                list.add((Bot) world.matrix[i][j]);
            }
        }
        // compare and sort cells
        list.sort((b1, b2) -> {
            if (b1.c_red == b2.c_red) {
                if (b1.c_green == b2.c_green) {
                    if (b1.c_blue == b2.c_blue) {
                        return 0;
                    }
                    return b1.c_blue > b2.c_blue ? 1 : - 1;
                }
                return b1.c_green > b2.c_green ? 1 : - 1;
            }
            return b1.c_red > b2.c_red ? 1 : - 1;
        });
        // prep to move cells
        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                var cell = ((SBot) world.matrix[i][j]);
                final var ind = list.indexOf(cell);
                int targX = ind % wd;
                int targY = ind / wd;
                cell.prep(targX, targY, DEFAULT_STEPS, () -> {
                    int count = counter.incrementAndGet();
                    cell.swap(world.matrix[targX][targY]);
                    if (count >= wd * ht) {
                        world.matrix = world.swapMatrix;
                        window.paint();
                    }
                });
            }
        }
    }
}
