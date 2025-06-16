import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener {
    private final Board BOARD =new Board();
    private final int LEFT=37;
    private final int UP=38;
    private final int RIGHT=39;
    private final int DOWN=40;
    private final int SHUFFLE=17;
    private final int SOLVE=10;
    Window(){
        this.setBounds(0,0,614,637);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(BOARD);
        this.addKeyListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case RIGHT -> BOARD.left(true);
            case DOWN -> BOARD.up(true);
            case LEFT -> BOARD.right(true);
            case UP -> BOARD.down(true);
            case SHUFFLE->BOARD.shuffle(50);
            case SOLVE-> {BOARD.solve();}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}