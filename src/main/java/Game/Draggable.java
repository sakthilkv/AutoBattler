package Game;

import java.awt.*;

public abstract class Draggable {
    public int x, y, size;
    public int offsetX, offsetY;
    public boolean dragging = false;
    public int prevX, prevY;

    public void startDrag(int mouseX, int mouseY) {
        dragging = true;
        offsetX = mouseX - x;
        offsetY = mouseY - y;
        savePosition();
    }

    public void dragTo(int mouseX, int mouseY) {
        x = mouseX - offsetX;
        y = mouseY - offsetY;
    }

    public void stopDrag() {
        dragging = false;
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

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public abstract void draw(Graphics2D g2d);
    public abstract void update();
}
