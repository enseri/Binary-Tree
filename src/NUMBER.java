import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class NUMBER extends GameObject{
    
    public NUMBER(int x, int y, ID id){
        super(x, y, id);
    }

    public void tick(){

    }

    public void render(Graphics g) {
        try{
            BufferedImage img = ImageIO.read(new File("Images/0.png"));
            g.drawImage(img, 0, 0, 10, 10, Color.white, null);
        } catch (IOException E) {
            System.out.println(E.getMessage());
        }
    }
}
