import java.awt.Graphics;

public abstract class GameObject {

    protected int x1, y1, x2, y2, number;
    protected ID id;

    public GameObject(int x1, int y1, int number, ID id) {
        this.x1 = x1;
        this.y1 = y1;
        this.number = number;
        this.id = id;
    }
    public GameObject(int x1, int y1, int x2, int y2, ID id) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.id = id;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public void setX1(int x) {
        this.x1 = x;
    }

    public void setY1(int y) {
        this.y1 = y;
    }
    public void setX2(int x) {
        this.x2 = x;
    }

    public void setY2(int y) {
        this.y2 = y;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }
    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

}
