package Game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48 x 48
    final int maxScreenCol = 1920/tileSize;
    final int maxScreenRow = 1080/tileSize;
    final int screenWidth = tileSize * maxScreenCol; //1920
    final int screenHeight = tileSize * maxScreenRow; //1080

    Thread gameThread;

    Monster monster;
    int gridStartX, gridStartY, innerBoxSize;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        innerBoxSize = tileSize * 3;

        gridStartX = (screenWidth - innerBoxSize * 8) / 2;
        gridStartY = (screenHeight - innerBoxSize * 5 - 100) / 2;

        monster = new Monster(20, 0, innerBoxSize);
        monster.placeAtGrid(2, 3, gridStartX, gridStartY, innerBoxSize);
        MouseHandler handler = new MouseHandler(monster, gridStartX, gridStartY, innerBoxSize);
        addMouseListener(handler);
        addMouseMotionListener(handler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        while(gameThread.isAlive()){
            update();
            repaint();
        }
    }

    public void update(){}
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        drawBattleGrid(g);
        drawPlayerDeck(g);
        Graphics2D g2d = (Graphics2D) g;
        monster.draw(g2d);
        drawMonsterInfo(g2d, monster);
    }

    public void drawBattleGrid(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.GREEN);
        int innerBoxSize = tileSize * 3;
        int gridCols = 8;
        int gridRows = 5;
        int width = innerBoxSize * gridCols;
        int height = innerBoxSize * gridRows;

        int startX = (getWidth() - width) / 2;
        int startY = (getHeight() - height - 100) / 2;

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                int x = startX + col * innerBoxSize;
                int y = startY + row * innerBoxSize;
                g2d.drawRect(x, y, innerBoxSize, innerBoxSize);
            }
        }
        g2d.dispose();
    }

    public void drawPlayerDeck(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.GREEN);
        int containerSize = tileSize*3;
        int deckRows = 7;
        int startX = 20;
        int startY = 0;
        for(int row = 0; row < deckRows; row++){
            int y = startY + row * containerSize;
            g2d.drawRect(startX, y, containerSize, containerSize);
        }
        g2d.dispose();
    }

    public void drawMonsterInfo(Graphics2D g2d, Monster m) {
        int infoX = m.x;
        int infoY = m.y - 20;

        String info = "X: " + m.x + ", Y: " + m.y;

        FontMetrics fm = g2d.getFontMetrics();
        int padding = 4;
        int boxWidth = fm.stringWidth(info) + padding * 2;
        int boxHeight = fm.getHeight();

        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRoundRect(infoX, infoY, boxWidth, boxHeight, 10, 10);

        // Draw border
        g2d.setColor(Color.WHITE);
        g2d.drawRoundRect(infoX, infoY, boxWidth, boxHeight, 10, 10);

        g2d.setColor(Color.GREEN);
        g2d.drawString(info, infoX + padding, infoY + fm.getAscent());
    }

}
