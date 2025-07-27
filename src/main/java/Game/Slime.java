package Game;

import java.awt.*;
import java.awt.geom.AffineTransform;

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
    public void draw(Graphics2D g2d, boolean enemy) {
        if (sprite != null) {
            if (enemy) {
                AffineTransform original = g2d.getTransform();
                g2d.translate(x + size, y);
                g2d.scale(-1, 1);
                g2d.drawImage(sprite, 0, 0, size, size, null);
                g2d.setTransform(original);
            } else {
                g2d.drawImage(sprite, x, y, size, size, null);
            }
        } else {
            g2d.setColor(enemy ? Color.RED : Color.GREEN);
            g2d.fillRect(x, y, size, size);
        }
    }
}
