package Game;

public class Slime extends Melee {
    private final int size;

    public Slime() {
        this.hp = 1800;
        this.attack = 200;
        this.size = 48;
        loadSprite("/sprites/slime.png");
    }
}
