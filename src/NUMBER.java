import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class NUMBER extends GameObject{
    
    public NUMBER(int x, int y, int number, ID id){
        super(x, y, number, id);
    }

    public void tick(){

    }

    public void render(Graphics g) {
        try{
            BufferedImage img = ImageIO.read(new File("Images/" + super.getNumber() + ".png"));
            g.drawImage(img, 0, 0, 10, 10, Color.white, null);
        } catch (IOException E) {
            System.out.println(E.getMessage());
        }
    }
}
