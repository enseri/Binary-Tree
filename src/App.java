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
        int WIDTH = 1000;
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
        handler.addObject(new NUMBER(500, 0, 5, ID.NUMBER));
        handler.addObject(new NUMBER(510, 0, 0, ID.NUMBER));
        handler.addObject(new NUMBER(520, 0, 0, ID.NUMBER));
        int numbersDisplayed = 0;
        int[] array = createRandomIntegerArray(1000, 1000);
        array[0] = 500;
        String[] displayedNumsData = new String[array.length];
        // Format
        // 0 left 9
        String[] displayedNumbers = new String[array.length];
        displayedNumbers[0] = "500";
        displayedNumsData[0] = "500,0,30,10";
        for (int i = 0; i != array.length; i++) {
            boolean numDisplayed = false;
            for (int a = 0; a != displayedNumbers.length; a++) {
                if (displayedNumbers[a] != null && array[i] == findNum(1, displayedNumbers[a]))
                    numDisplayed = true;
            }
            if (!numDisplayed) {
                String info = findNextSpot(array[i], 500, displayedNumbers);
                for (int b = 0; b != displayedNumbers.length; b++) {
                    if (displayedNumbers[b] != null && findNum(1, displayedNumbers[b]) == findNum(2, info)) {
                        if (findDir(info).equals("right")) {
                            System.out.println(info);
                            for (int c = 0; c < (findNum(1, info) + "").length(); c++) {
                                handler.addObject(new NUMBER(findData(0, displayedNumsData[b]) + findData(2, displayedNumsData[b]) + ((c + 1) * 10), findData(1, displayedNumsData[b]) + findData(3, displayedNumsData[b]), strToNum((findNum(1, info) + "").substring(c, c + 1)), ID.NUMBER));
                            }
                            numbersDisplayed++;
                            displayedNumbers[numbersDisplayed] = info;
                            displayedNumsData[numbersDisplayed] = (findData(0, displayedNumsData[b]) + findData(2, displayedNumsData[b]) + 10) + "," + (findData(1, displayedNumsData[b]) + findData(3, displayedNumsData[b])) + "," + ((findNum(1, info) + "").length() * 10) + "," + 10;
                            handler.addObject(new LINE(findData(0, displayedNumsData[b]) + findData(2, displayedNumsData[b]), findData(1, displayedNumsData[b]) + findData(3, displayedNumsData[b]) / 2, findData(0, displayedNumsData[numbersDisplayed]), findData(1, displayedNumsData[numbersDisplayed]), ID.LINE));
                        } else if (findDir(info).equals("left")) {
                            System.out.println(info);
                            int d = 0;
                            for (int c = (findNum(1, info) + "").length() - 1; c > -1; c--) {
                                handler.addObject(new NUMBER(findData(0, displayedNumsData[b]) - ((c + 2) * 10), findData(1, displayedNumsData[b]) + findData(3, displayedNumsData[b]), strToNum((findNum(1, info) + "").substring(d, d + 1)), ID.NUMBER));
                                d++;
                            }
                            numbersDisplayed++;
                            displayedNumbers[numbersDisplayed] = info;
                            displayedNumsData[numbersDisplayed] = (findData(0, displayedNumsData[b]) - (((findNum(1, info) + "").length() + 1) * 10)) + "," + (findData(1, displayedNumsData[b]) + findData(3, displayedNumsData[b])) + "," + ((findNum(1, info) + "").length() * 10) + "," + 10;
                            handler.addObject(new LINE(findData(0, displayedNumsData[b]), findData(1, displayedNumsData[b]) + findData(3, displayedNumsData[b]) / 2, findData(0, displayedNumsData[numbersDisplayed]) + findData(2, displayedNumsData[numbersDisplayed]), findData(1, displayedNumsData[numbersDisplayed]), ID.LINE));
                        }
                    }
                }
            }
        }
    }

    public int findNumIndex(int num, String[] arr) {
        for (int i = 0; i != arr.length; i++) {
            for (int a = 0; a + (num + "").length() <= arr[i].length(); a++) {
                if ((num + "").equals(arr[i].substring(a, a + (num + "").length())))
                    return i;
            }
        }
        return -1;
    }

    public int[] createRandomIntegerArray(int length, int upperLimit) {
        Random rand = new Random();
        int[] array = new int[length];
        for (int i = 0; i != length; i++) {
            array[i] = rand.nextInt(upperLimit);
        }
        return array;
    }

    public int findData(int spot, String str) {
        int commasPassed = 0;
        for (int i = 0; i != str.length(); i++) {
            if (commasPassed == spot) {
                int endIndex = 0;
                for (int a = i; a < str.length() && !str.substring(a, a + 1).equals(","); a++) {
                    endIndex = a;
                }
                endIndex++;
                return strToNum(str.substring(i, endIndex));
            } else if (str.substring(i, i + 1).equals(",")) {
                commasPassed++;
            }
        }
        return -1;
    }

    public String findNextSpot(int num, int currentNum, String[] arr) {
        for (int i = 0; i != arr.length; i++) {
            if (arr[i] != null && currentNum == findNum(1, arr[i])) {
                if (num > currentNum) {
                    String info = num + " right ";
                    boolean moved = false;
                    for (int a = 0; a != arr.length; a++) {
                        if (arr[a] != null && findDir(arr[a]).equals("right") && findNum(2, arr[a]) == currentNum) {
                            moved = true;
                            return findNextSpot(num, findNum(1, arr[a]), arr);
                        }
                    }
                    if (!moved) {
                        info += currentNum;
                        return info;
                    }
                } else if (num < currentNum) {
                    String info = num + " left ";
                    boolean moved = false;
                    for (int a = 0; a != arr.length; a++) {
                        if (arr[a] != null && findDir(arr[a]).equals("left") && findNum(2, arr[a]) == currentNum) {
                            moved = true;
                            return findNextSpot(num, findNum(1, arr[a]), arr);
                        }
                    }
                    if (!moved) {
                        info += currentNum;
                        return info;
                    }
                }
            }
        }
        return null;
    }

    public int strToNum(String str) {
        for (int i = 0; i != 100000; i++) {
            if ((i + "").equals(str))
                return i;
        }
        return -1;
    }

    public String findDir(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).equals("l"))
                return "left";
            else if (str.substring(i, i + 1).equals("r"))
                return "right";
        }
        return "none";
    }

    public int findNum(int firstOrSecond, String str) {
        String num = "-1";
        if (firstOrSecond == 1) {
            num = "";
            int endIndex = 0;
            int i = 0;
            for (i = 0; i <= str.length(); i++) {
                if (i < str.length() && str.substring(i, i + 1).equals(" ")) {
                    endIndex++;
                    break;
                }
                endIndex = i;
            }
            num += strToNum(str.substring(0, endIndex));
        } else if (firstOrSecond == 2) {
            int i = 0;
            for (i = 0; i < str.length() && !str.substring(i, i + 1).equals("t"); i++) {
            }
            i += 2;
            num = "";
            num += strToNum(str.substring(i, str.length()));
        }
        return strToNum(num);
    }
}