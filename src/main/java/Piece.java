import java.awt.*;

public class Piece {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private Text text;
    public Piece(int x, int y, int width, int height, Color color,int number) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.text=new Text(x,y,number);
    }

    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fill3DRect(x, y, width, height, true);
        text.paint(graphics);
    }
    public void setText(int text){
        this.text.setText(text);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", text=" + text +
                '}';
    }
}
