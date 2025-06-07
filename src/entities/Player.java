package entities;

import java.awt.image.BufferedImage;

public class Player extends Entity {
    public double speed = 1.3;
    public boolean right, left, up, down;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void tick() {
        if (right) {
            x += speed;
        } else if (left) {
            x -= speed;
        }

        if (up) {
            y -= speed;
        } else if (down) {
            y += speed;
        }
    }
}
