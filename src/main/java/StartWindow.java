import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartWindow extends JFrame {
    private boolean show = true;

    public StartWindow() {
        // הגדרות בסיסיות של החלון
        this.setResizable(false);
        this.setSize(600, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true); // הסרת מסגרת החלון
        this.setShape(new RoundRectangle2D.Double(0, 0, 600, 500, 30, 30)); // פינות מעוגלות

        // פאנל ראשי עם רקע מעוצב
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(null);
        this.add(mainPanel);

        // כותרת המשחק
        JLabel titleLabel = new JLabel("8-Puzzle Game");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(240, 240, 240));
        titleLabel.setBounds(0, 50, 600, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);

        // כפתור התחלה
        RoundedButton startButton = new RoundedButton("START GAME");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(45, 220, 40));
        startButton.setBounds(200, 180, 200, 60);
        startButton.addActionListener(e -> startGame());
        mainPanel.add(startButton);

        // כפתור כללים
        RoundedButton rulesButton = new RoundedButton("HOW TO PLAY");
        rulesButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        rulesButton.setForeground(Color.WHITE);
        rulesButton.setBackground(new Color(60, 40, 210));
        rulesButton.setBounds(200, 260, 200, 60);
        mainPanel.add(rulesButton);

        // תווית עם כללים
        JLabel rulesLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>HOW TO PLAY</h2>"
                + "<p>• Press Ctrl to shuffle the board</p>"
                + "<p>• Use ARROW KEYS to move the tiles</p>"
                + "<p>• Press ENTER to solve automatically</p>"
                + "<p>• Click X to exit the game</p></div></html>");
        rulesLabel.setForeground(new Color(240, 240, 240));
        rulesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rulesLabel.setBounds(100, 120, 400, 250);
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rulesLabel.setVisible(false);
        mainPanel.add(rulesLabel);

        // טיפול בלחיצות כפתור הכללים
        rulesButton.addActionListener(e -> {
            startButton.setVisible(!show);
            rulesButton.setVisible(!show);
            rulesLabel.setVisible(show);
            show = !show;
        });

        // כפתור יציאה קטן בפינה
        RoundedButton exitButton = new RoundedButton("X");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setBounds(550, 10, 40, 40);
        exitButton.addActionListener(e -> System.exit(0));
        mainPanel.add(exitButton);

        // הצגת החלון
        this.setVisible(true);
    }

    public void startGame() {
        new Thread(() -> {
            // אנימציית מעבר
            for (float opacity = 1.0f; opacity > 0; opacity -= 0.05f) {
                setOpacity(opacity);
                try { Thread.sleep(5); } catch (Exception e) {}
            }
            dispose(); // סגירת חלון הפתיחה
            new Window(); // פתיחת חלון המשחק
        }).start();
    }

    // מחלקה ליצירת רקע עם גרדיאנט
    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // יצירת גרדיאנט
            Color color1 = new Color(30, 30, 60);
            Color color2 = new Color(10, 10, 30);
            GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // הוספת טקסטורת רקע עדינה
            g2d.setColor(new Color(255, 255, 255, 10));
            for (int i = 0; i < getWidth(); i += 40) {
                for (int j = 0; j < getHeight(); j += 40) {
                    g2d.fillOval(i, j, 3, 3);
                }
            }

            // הוספת גבול מעוצב
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(new Color(80, 80, 120));
            g2d.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, 30, 30);
        }
    }

    // מחלקה ליצירת כפתורים מעוגלים עם אפקטים
    static class RoundedButton extends JButton {
        private Color hoverColor;
        private Color pressedColor;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // אפקטים לאירועי עכבר
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (hoverColor != null) {
                        setBackground(hoverColor);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(getBackground());
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (pressedColor != null) {
                        setBackground(pressedColor);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(getBackground());
                }
            });
        }

        @Override
        public void setBackground(Color bg) {
            super.setBackground(bg);
            hoverColor = bg.brighter();
            pressedColor = bg.darker();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // ציור הרקע
            if (getModel().isPressed()) {
                g2.setColor(pressedColor != null ? pressedColor : getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(hoverColor != null ? hoverColor : getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            // ציור הטקסט
            g2.setColor(getForeground());
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            Rectangle r = new Rectangle(getWidth(), getHeight());
            int x = (r.width - fm.stringWidth(getText())) / 2;
            int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(getText(), x, y);

            // ציור גבול עדין
            g2.setColor(new Color(200, 200, 200, 100));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);

            g2.dispose();
        }
    }
}