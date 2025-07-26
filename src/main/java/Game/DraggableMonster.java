package Game;

import java.awt.*;

public class DraggableMonster extends Draggable {
    private final Entity monster;

    public DraggableMonster(Entity monster) {
        this.monster = monster;
        this.size = monster.size;
        this.x = monster.x;
        this.y = monster.y;
    }

    @Override
    public void draw(Graphics2D g2d) {
        monster.x = x;
        monster.y = y;
        monster.draw(g2d);
    }

    @Override
    public void update() {
        monster.update();
    }
}