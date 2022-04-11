import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BACKGROUND extends GameObject{
    
    public BACKGROUND(int x, int y, int number, ID id){
        super(x, y, number, id);
    }

    public void tick(){

    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(0, 0, 1000, 500);
        g.fillRect(0, 0, 1000, 500);
    }
}
