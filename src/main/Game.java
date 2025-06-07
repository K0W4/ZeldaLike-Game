package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;

import entities.*;
import graphics.*;

public class Game extends Canvas implements Runnable{
	public static JFrame frame;
	private final int width = 240, height = 160, scale = 3;
	private boolean isRunning = true;
	private Thread thread;
	private BufferedImage image;
	private Graphics g;

	public ArrayList<Entity> entities;
	public Spritesheet spritesheet;
	
	public Game() {
		this.setPreferredSize(new Dimension(width * scale, height * scale));
		initFrame();

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<>();
		spritesheet = new Spritesheet("/res/spritesheet.png");

		entities.add(new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16)));
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
    
    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void initFrame() {
		frame = new JFrame("ZeldaLike");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void tick() {
		for (Entity entity : entities) {
			entity.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, width * scale, height * scale);
		
		for (Entity entity : entities) {
			entity.render(g);
		}

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale,null);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while (isRunning == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
                requestFocus();
				tick();
                render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
	}
}
