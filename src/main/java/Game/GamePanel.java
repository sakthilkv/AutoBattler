package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

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
    final int FPS = 60;
    int actualFPS = 0;

    MonsterFactory factory;
    int gridStartX, gridStartY, innerBoxSize;

    Image groundTile = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tiles/Ground_Tile_02_A.png"))).getImage();
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        innerBoxSize = tileSize * 3;
        gridStartX = (screenWidth - innerBoxSize * 8) / 2;
        gridStartY = (screenHeight - innerBoxSize * 5 - 100) / 2;

        factory = new MonsterFactory(gridStartX, gridStartY, innerBoxSize);
        factory.createDeckCharacter(Slime.class,2, 1);
        factory.createEnemyCharacter(Mushroom.class,2, 5);

        for (DraggableMonster d : factory.spawned) {
            MouseHandler handler = new MouseHandler(d, gridStartX, gridStartY, innerBoxSize);
            addMouseListener(handler);
            addMouseMotionListener(handler);
        }

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000.0 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currTime;
        long timer = 0;
        long drawCount = 0;

        while(gameThread.isAlive()){
            currTime = System.nanoTime();

            delta += (currTime - lastTime) / drawInterval;
            timer += (currTime - lastTime);
            lastTime = currTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                actualFPS = (int) drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){}
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGround(g);
        drawBattleGrid(g);
        drawPlayerDeck(g);
        Graphics2D g2d = (Graphics2D) g;
        factory.drawAll(g2d);
        drawFPS(g2d);
        for (DraggableMonster d : factory.spawned) drawMonsterInfo(g2d,d.monster);
        for (Entity d : factory.enemies) drawMonsterInfo(g2d,d);
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

        Image[] tileImages = new Image[9];
        for (int i = 0; i < 9; i++) {
            tileImages[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tiles/Decor_Tile_B_0" + (i + 1) + ".png"))).getImage();
        }

        int[][] gridTiles = new int[gridRows][gridCols];
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                if (row == 0 && col == 0) gridTiles[row][col] = 1;
                else if (row == 0 && col == gridCols - 1) gridTiles[row][col] = 3;
                else if (row == gridRows - 1 && col == 0) gridTiles[row][col] = 7;
                else if (row == gridRows - 1 && col == gridCols - 1) gridTiles[row][col] = 9;
                else if (row == 0) gridTiles[row][col] = 2;
                else if (row == gridRows - 1) gridTiles[row][col] = 8;
                else if (col == 0) gridTiles[row][col] = 4;
                else if (col == gridCols - 1) gridTiles[row][col] = 6;
                else gridTiles[row][col] = 5;
            }
        }

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                int tileType = gridTiles[row][col];
                int x = startX + col * innerBoxSize;
                int y = startY + row * innerBoxSize;
                g2d.drawImage(tileImages[tileType - 1], x, y, innerBoxSize, innerBoxSize, null);
            }
        }
        g2d.setColor(new Color(0, 0, 0, 80));
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
        int containerSize = tileSize * 3;
        int deckRows = 7;
        int startX = 20;
        int startY = 0;

        Image deckTile = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tiles/Deck_Tile.png"))).getImage();
        Image deckTileT = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tiles/Decor_Tile_B_02.png"))).getImage();
        Image deckTileB = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tiles/Decor_Tile_B_08.png"))).getImage();
        for (int row = 0; row < deckRows; row++) {
            int y = startY + row * containerSize;
            switch(row){
                case 0:
                    g2d.drawImage(deckTileT, startX, y, containerSize, containerSize, null);
                    break;
                case 6:
                    g2d.drawImage(deckTileB, startX, y, containerSize, containerSize, null);
                    break;
                default:
                    g2d.drawImage(deckTile, startX, y, containerSize, containerSize, null);
                    break;
            }
        }

        g2d.setColor(new Color(0, 0, 0, 80));
        for (int row = 0; row < deckRows; row++) {
            int y = startY + row * containerSize;
            g2d.drawRect(startX, y, containerSize, containerSize);
        }

        g2d.dispose();
    }

    public void drawMonsterInfo(Graphics2D g2d, Entity m) {
        int barWidth = m.size;
        int barHeight = 8;
        int infoX = m.x;
        int infoY = m.y - barHeight - 6;

        int currentHP = Math.max(0, Math.min(m.hp, m.maxHp));
        float hpRatio = (float) currentHP / m.maxHp;
        int filledWidth = (int) (barWidth * hpRatio);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(infoX, infoY, barWidth, barHeight);

        g2d.setColor(new Color(41, 175, 52, 255));
        g2d.fillRect(infoX, infoY, filledWidth, barHeight);

        g2d.setColor(Color.YELLOW);
        g2d.drawRect(infoX, infoY, barWidth, barHeight);
    }

    private void drawFPS(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Consolas", Font.PLAIN, 18));
        g2d.drawString("FPS: " + actualFPS, 10, 20);
    }

    public void drawGround(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int tileSizePx = tileSize * 3;

        for (int y = 0; y < screenHeight; y += tileSizePx) {
            for (int x = 0; x < screenWidth; x += tileSizePx) {
                g2d.drawImage(groundTile, x, y, tileSizePx, tileSizePx, null);
            }
        }

        g2d.dispose();
    }

}
