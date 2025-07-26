package Game;

import java.util.ArrayList;
import java.util.List;

public class MonsterFactory {
    private final int gridStartX;
    private final int gridStartY;
    private final int cellSize;

    public final List<DraggableMonster> spawned = new ArrayList<>();

    public MonsterFactory(int gridStartX, int gridStartY, int cellSize) {
        this.gridStartX = gridStartX;
        this.gridStartY = gridStartY;
        this.cellSize = cellSize;
    }

    public DraggableMonster createSlimeAt(int row, int col) {
        Slime slime = new Slime(0, 0, cellSize);
        DraggableMonster d = new DraggableMonster(slime);
        d.placeAtGrid(row, col, gridStartX, gridStartY, cellSize);
        spawned.add(d);
        return d;
    }

    public void drawAll(java.awt.Graphics2D g2d) {
        for (DraggableMonster d : spawned) d.draw(g2d);
    }

    public void updateAll() {
        for (DraggableMonster d : spawned) d.update();
    }
}
