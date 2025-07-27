package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {
    protected int hp;
    protected int attack;
    protected int range;
    protected int x, y, size;

    public abstract void update();
    public abstract void draw(Graphics2D g2d, boolean enemy);
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected BufferedImage sprite;

    protected void loadSprite(String path) {
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
