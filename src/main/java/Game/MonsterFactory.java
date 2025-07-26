package Game;

import java.util.ArrayList;
import java.util.List;

public class MonsterFactory {
    private final int gridStartX;
    private final int gridStartY;
    private final int cellSize;

    public final List<DraggableMonster> spawned = new ArrayList<>();
    public final List<Entity> enemies = new ArrayList<>();
    public MonsterFactory(int gridStartX, int gridStartY, int cellSize) {
        this.gridStartX = gridStartX;
        this.gridStartY = gridStartY;
        this.cellSize = cellSize;
    }

    public DraggableMonster createDeckCharacter(Class<? extends Entity> clazz, int row, int col) {
        try {
            Entity monster = clazz.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(0, 0, cellSize);
            DraggableMonster d = new DraggableMonster(monster);
            d.placeAtGrid(row, col, gridStartX, gridStartY, cellSize);
            spawned.add(d);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Entity createEnemyCharacter(Class<? extends Entity> clazz, int row, int col) {
        try {
            Entity monster = clazz.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(0, 0, cellSize);
            monster.x = gridStartX + col * cellSize;
            monster.y = gridStartY + row * cellSize;
            enemies.add(monster);
            return monster;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void drawAll(java.awt.Graphics2D g2d) {
        for (DraggableMonster d : spawned) d.draw(g2d);
        for (Entity d : enemies) d.draw(g2d);
    }

    public void updateAll() {
        for (DraggableMonster d : spawned) d.update();
    }
}
