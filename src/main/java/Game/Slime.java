package Game;

import java.awt.*;

public class Slime extends Melee {
    public Slime(int x, int y, int size) {
        this.hp = 50;
        this.attack = 10;
        this.size = size;
        this.x = x;
        this.y = y;
        loadSprite("/sprites/slime.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {
        if (sprite != null) {
            g2d.drawImage(sprite, x, y, size, size, null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x, y, size, size);
        }
    }
}
