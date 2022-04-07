import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LINE extends GameObject{
    
    public LINE(int x1, int y1, int x2, int y2, ID id){
        super(x1, y1, x2, y2, id);
    }

    public void tick(){

    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(super.getX1(), super.getY1(), super.getX2(), super.getY2());
    }
}
