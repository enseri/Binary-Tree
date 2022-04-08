import java.util.Scanner;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.ArrayList;

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
        handler.addObject(new BACKGROUND(0, 0, 0, ID.BACKGROUND));
        handler.addObject(new NUMBER(250, 0, 1, ID.NUMBER));
        handler.addObject(new NUMBER(260, 0, 0, ID.NUMBER));
        int[] array = createRandomIntegerArray(10, 50);
        // Format
        // 0 left 9
        String[] displayedNumbers = new String[array.length];
        displayedNumbers[0] = "10";
        array[0] = 10;
        for(int i = 0; i != array.length; i++) {
            boolean numDisplayed = false;
            for(int a = 0; a != displayedNumbers.length; a++) {
                if(array[i] == strToNum(displayedNumbers[a]))
                    numDisplayed = true;
            }
            if(!numDisplayed) {
                System.out.println(findNextSpot(array[i], array[0], displayedNumbers));
            }
        }
    }

    public int[] createRandomIntegerArray(int length, int upperLimit) {
        Random rand = new Random();
        int[] array = new int[length];
        for(int i = 0; i != length; i++) {
            array[i] = rand.nextInt(upperLimit);
        }
        return array;
    }

    public String findNextSpot(int num, int currentNum, String[] arr){
        for(int i = 0; i != arr.length; i++) {
            if(arr[i] != null && currentNum == findNum(1, arr[i])) {
                if(num > currentNum) {
                    String info = num + " right ";
                    boolean moved = false;
                    for(int a = 0; a != arr.length; a++) {
                        if(arr[a] != null && findDir(arr[a]).equals("right") && findNum(2, arr[a]) == currentNum) {
                            moved = true;
                            return findNextSpot(num, currentNum, arr);
                        }
                    }
                    if(!moved) {
                        info += currentNum;
                        return info;
                    }
                }
                if(num < currentNum) {
                    String info = num + " left ";
                    boolean moved = false;
                    for(int a = 0; a != arr.length; a++) {
                        if(arr[a] != null && findDir(arr[a]).equals("left") && findNum(2, arr[a]) == currentNum) {
                            moved = true;
                            return findNextSpot(num, currentNum, arr);
                        }
                    }
                    if(!moved) {
                        info += currentNum;
                        return info;
                    }
                }
            }
        }
        return null;
    }

    public int strToNum(String str) {
        for(int i = 0 ; i != 100000; i++) {
            if((i + "").equals(str))
                return i;
        }
        return -1;
    }

    public String findDir(String str) {
        for(int i = 0; i < str.length(); i++) {
            if(str.substring(i).equals("l"))
                return "left";
            else if(str.substring(i).equals("r"))
                return "right";
        }
        return "none";
    }

    public int findNum(int firstOrSecond, String str) {
        String num = "-1";
        if(firstOrSecond == 1) {
            for(int i = 0; i < str.length() && str.substring(i).compareTo(" ") != 0; i++) {
                num = "";
                num += strToNum(str.substring(i));
            }
        } else if (firstOrSecond == 2) {
            int i = 0;
            for(i = 0; i < str.length() && !str.substring(i).equals("t"); i++) {

            }
            i++;
            for(i = i; i < str.length(); i++) {
                num = "";
                num += strToNum(str.substring(i));
            }
        }
        return strToNum(num);
    }
}