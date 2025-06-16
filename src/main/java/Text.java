import java.awt.*;

public class Text {
    private int x;
    private int y;

    private String text="";
    public Text(int x, int y,int number) {
        this.x = x;
        this.y = y;
        if(number!=0){
            text= String.valueOf(number);
        }
    }

    public void paint(Graphics graphics){
        graphics.setColor(Color.white);
        graphics.setFont(new Font(Font.SERIF, Font.BOLD, 130));
        graphics.drawString(text,x+65,y+150);
    }
    public void setText(int text){
        if(text!=0){
            this.text= String.valueOf(text);
        }else {
            this.text= "";
        }
    }
}
