import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LINE extends GameObject{
    
    public LINE(int x, int y, ID id){
        super(x, y, id);
    }

    public void tick(){

    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(super.getX1(), super.getY1(), super.getX2(), super.getY2());
    }
}
