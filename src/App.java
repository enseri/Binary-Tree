import java.util.Scanner;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class App extends Canvas implements Runnable {
    private Random rand = new Random();
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private Scanner input = new Scanner(System.in);

    public App() {
        int WIDTH = 500;
        int HEIGHT = 500;
        new Window(WIDTH, HEIGHT, "Binary Tree", this);

        handler = new Handler();

        begin();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int prevFPS = 0;
        int fps = 0;
        int x = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                fps++;
                tick();
                delta--;
            }
            if (running)
                render();
        }
        stop();
    }

    public void tick() {
        handler.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.green);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) throws Exception {
        new App();
    }

    public void begin() {
        int[] numbers = new int[10];
        for(int i = 0; i != 10 ; i++) {
            numbers[i] = rand.nextInt(50) + 1;
        }
        handler.addObject(new NUMBER(0, 0, ID.NUMBER));
    }
}