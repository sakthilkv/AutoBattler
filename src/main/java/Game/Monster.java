package Game;

import java.awt.*;

public class Monster {
    public int x, y, size;
    public int prevX, prevY;
    public int offsetX, offsetY;
    public boolean dragging = false;

    public Monster(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        savePosition();
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public void savePosition() {
        prevX = x;
        prevY = y;
    }

    public void revert() {
        x = prevX;
        y = prevY;
    }

    public void snapToGrid(int gridStartX, int gridStartY, int cellSize) {
        this.x = ((x - gridStartX + cellSize / 2) / cellSize) * cellSize + gridStartX;
        this.y = ((y - gridStartY + cellSize / 2) / cellSize) * cellSize + gridStartY;
    }

    public void placeAtGrid(int row, int col, int gridStartX, int gridStartY, int cellSize) {
        this.x = gridStartX + col * cellSize;
        this.y = gridStartY + row * cellSize;
        savePosition();
    }

}
