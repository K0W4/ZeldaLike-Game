package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Player extends Entity {
    public int dir = 0;
    public double speed = 1.3;
    public boolean right, left, up, down;

    private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];

        for (int i = 0; i < 4; i++) {
            rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
            leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
        }
    }

    public void tick() {
        moved = false;
        if (right) {
            moved = true;
            dir = 0; 
            x += speed;
        } else if (left) {
            moved = true;
            dir = 1;
            x -= speed;
        }

        if (up) {
            moved = true;
            y -= speed;
        } else if (down) {
            moved = true;
            y += speed;
        }

        if (moved) {
            frames++;
            if (frames > maxFrames) {
                frames = 0;
                index++;
                if (index > maxIndex) {
                    index = 0;
                }
            }
        } else {
            frames = 0;
            index = 0;
        }
    }

    public void render(Graphics g) {
        if (dir == 0) {
            g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
        } else if (dir == 1) {
            g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
        }
    }
}
