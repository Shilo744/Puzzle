import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.util.Stack;

public class Board extends JPanel {
    private final int BOARD_SIZE=3;
    private Tiny[] numbers = new Tiny[9];
    private Piece[] pieces = new Piece[numbers.length];
    private Color[] colors = new Color[numbers.length];
    private byte location = 8;
    private Stack <Byte>moves=new Stack<>();
    private final String MOVE_SOUND = "/moveSound.wav";
    private final String SHUFFLE_SOUND = "/shuffle.wav";
    public Tiny[] getNumbers() {
        return numbers;
    }

    //[1][2][3]
//[4][5][6]
//[7][8][0]
    Board() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i]=new Tiny(i+1);
        }
        numbers[numbers.length-1]=new Tiny(0);
        this.setBounds(0, 0, 2000, 2000);
        this.setLayout(null);
        this.setBackground(null);
        int widthAndHeight = 200;
        colors[0] = new Color(2, 181, 240);
        colors[1] = new Color(205, 92, 92);
        colors[2] = new Color(255, 165, 2);
        colors[3] = new Color(0, 195, 200);
        colors[4] = new Color(219, 112, 147);
        colors[5] = new Color(220, 195, 0);
        colors[6] = new Color(65, 105, 224);
        colors[7] = new Color(185, 85, 211);
        colors[8] = Color.white;
        for (int i = 0; i < pieces.length; i++) {
            Color color;
            if (colors[i]!=null){
                color=colors[i];
            }else {
                color=randomColor();
            }
            int number=numbers[i].getNumber();
            pieces[i] = new Piece(widthAndHeight * (i % BOARD_SIZE), widthAndHeight * (i / BOARD_SIZE), widthAndHeight, widthAndHeight, color,number );
        }
    }
    public void moves(Stack<Byte> moves){
        switch (moves.pop()){
            case 1->left(true);
            case 2->up(true);
            case 3->right(true);
            case 4->down(true);
        }
        repaint();
    }
    public static Color randomColor(){
        Random random=new Random();
        int r=random.nextInt(255);
        int g=random.nextInt(255);
        int b=random.nextInt(255);
        return new Color(r,g,b);
    }
    public void solve(){
        Solver solver = new Solver();
        Node node = new Node(null, numbers, this.location, (byte) 0);
        solver.solveIt(node);
        moves=solver.moves;
        System.out.println("\nsolved in "+moves.size()+" moves");
        orderBoard();
    }
    public void orderBoard(){new Thread(()->{
        while (!moves.isEmpty()){
            Stack move=moves;
            while (!move.isEmpty()){
                moves(move);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }}
        }
    }).start();
    }
    public void left(boolean sound) {
        if (location % 3 != 0) {
            numbers[location].setNumber(numbers[location - 1].getNumber());
            numbers[location - 1].setNumber(0);

            pieces[location].setText(numbers[location].getNumber());
            pieces[location - 1].setText(0);

            pieces[location].setColor(pieces[location - 1].getColor());
            pieces[location - 1].setColor(Color.white);

            location -= 1;
            if (sound)
                playMoveSound();
            repaint();
        }
    }
    public void up(boolean sound) {
        if (location >= 3) {
            numbers[location].setNumber(numbers[location - 3].getNumber());
            numbers[location - 3].setNumber(0);
            pieces[location].setText(numbers[location].getNumber());
            pieces[location - 3].setText(0);

            pieces[location].setColor(pieces[location - 3].getColor());
            pieces[location - 3].setColor(Color.white);

            location -= 3;
            if (sound)
                playMoveSound();
            repaint();
        }
    }
    public void right(boolean sound) {
        if (location % 3 != 2) {
            numbers[location].setNumber(numbers[location + 1].getNumber());
            numbers[location + 1].setNumber(0);

            pieces[location].setText(numbers[location].getNumber());
            pieces[location + 1].setText(0);

            pieces[location].setColor(pieces[location + 1].getColor());
            pieces[location + 1].setColor(Color.white);

            location += 1;
            if (sound)
                playMoveSound();
            repaint();
        }
    }
    public void down(boolean sound) {
        if (location < 6) {
            numbers[location].setNumber(numbers[location + 3].getNumber());
            numbers[location + 3].setNumber(0);

            pieces[location].setText(numbers[location].getNumber());
            pieces[location + 3].setText(0);

            pieces[location].setColor(pieces[location + 3].getColor());
            pieces[location + 3].setColor(Color.white);

            location += 3;
            if (sound)
                playMoveSound();
            repaint();
        }
    }
    public void shuffle(int moves) {
        Random random = new Random();
        int a;
        playSound(SHUFFLE_SOUND);
        for (int i = 0; i < moves; i++) {
            a=random.nextInt(4);
            switch (a) {
                case 0 -> left(false);
                case 1 -> up(false);
                case 2 -> right(false);
                case 3 -> down(false);
            }
        }
        repaint();
    }

    public void playMoveSound() {
        playSound(MOVE_SOUND);
    }

    public static void playSound(String resourcePath) {
        try {
            InputStream audioSrc = Board.class.getResourceAsStream(resourcePath);

            if (audioSrc == null) {
                System.err.println("Couldn't find sound file: " + resourcePath);
                return;
            }

            // 3. עטיפה ועבודה עם ה-InputStream במקום עם קובץ.
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            // ...
        } catch (Exception e) {
            // ...
        }
    }    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (Piece piece : pieces) {
            piece.paint(graphics);
        }

    }
}
