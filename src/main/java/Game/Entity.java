package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    protected int hp;
    protected int attack;
    protected int range;
    protected int x, y;
    protected BufferedImage sprite;

    public void update() {

    }

    public void draw(Graphics2D g2d) {

    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void loadSprite(String path) {
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

}
