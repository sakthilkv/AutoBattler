package Game;

import java.awt.event.*;

public class MouseHandler extends MouseAdapter {

    private final Monster monster;
    private final int gridStartX, gridStartY, cellSize;
    private final int deckX = 20;
    private final int deckY = 0;
    private final int deckRows = 7;

    public MouseHandler(Monster monster, int gridStartX, int gridStartY, int cellSize) {
        this.monster = monster;
        this.gridStartX = gridStartX;
        this.gridStartY = gridStartY;
        this.cellSize = cellSize;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (monster.getBounds().contains(e.getPoint())) {
            monster.dragging = true;
            monster.offsetX = e.getX() - monster.x;
            monster.offsetY = e.getY() - monster.y;
            monster.savePosition();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (monster.dragging) {
            monster.x = e.getX() - monster.offsetX;
            monster.y = e.getY() - monster.offsetY;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!monster.dragging) return;
        monster.dragging = false;

        int mx = monster.x;
        int my = monster.y;

        boolean snapped = false;

        if (mx >= deckX && mx < deckX + cellSize &&
                my >= deckY && my < deckY + deckRows * cellSize) {
            monster.x = deckX;
            monster.y = ((my - deckY + cellSize / 2) / cellSize) * cellSize + deckY;
            snapped = true;
        }

        int gridWidth = 8 * cellSize;
        int gridHeight = 5 * cellSize;

        if (mx >= gridStartX &&
                my >= gridStartY &&
                mx + monster.size <= gridStartX + gridWidth &&
                my + monster.size <= gridStartY + gridHeight) {
            monster.snapToGrid(gridStartX, gridStartY, cellSize);
            snapped = true;
        }

        if (!snapped) {
            monster.revert();
        }
    }
}
