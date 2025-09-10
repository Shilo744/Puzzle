import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartWindow extends JFrame {

    private final JPanel mainPanel;
    private final CardLayout cardLayout = new CardLayout();

    public StartWindow() {
        // --- הגדרות בסיסיות של החלון ---
        setTitle("8-Puzzle Solver");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // ממקם במרכז המסך
        setResizable(false);

        // CardLayout הוא מנהל הפריסה האידיאלי להחלפה בין "מסכים"
        mainPanel = new JPanel(cardLayout);

        // --- יצירת המסכים (הפאנלים) ---
        JPanel menuPanel = createMenuPanel();
        JPanel rulesPanel = createRulesPanel();

        // הוספת הפאנלים ל-CardLayout עם שמות מזהים
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(rulesPanel, "RULES");

        // הוספת הפאנל הראשי לחלון
        this.add(mainPanel);

        // הצגת מסך התפריט בהתחלה
        cardLayout.show(mainPanel, "MENU");

        this.setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40)); // רקע כהה

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // ריווח בין רכיבים
        gbc.gridwidth = 2; // הרכיבים יתפסו 2 טורים
        gbc.fill = GridBagConstraints.HORIZONTAL; // הרכיבים ימלאו את הרוחב

        // --- כותרת המשחק ---
        JLabel titleLabel = new JLabel("8-Puzzle Solver", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // --- כפתור התחלה ---
        JButton startButton = createStyledButton("Start Game", new Color(76, 175, 80));
        startButton.addActionListener(e -> startGame());
        gbc.gridy = 1;
        gbc.insets = new Insets(40, 10, 10, 10); // ריווח גדול יותר מלמעלה
        panel.add(startButton, gbc);

        // --- כפתור חוקים ---
        JButton rulesButton = createStyledButton("How to Play", new Color(33, 150, 243));
        rulesButton.addActionListener(e -> cardLayout.show(mainPanel, "RULES"));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10); // חזרה לריווח רגיל
        panel.add(rulesButton, gbc);

        return panel;
    }

    private JPanel createRulesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // ריווח פנימי

        // --- כותרת החוקים ---
        JLabel rulesTitle = new JLabel("How to Play", SwingConstants.CENTER);
        rulesTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        rulesTitle.setForeground(Color.WHITE);
        panel.add(rulesTitle, BorderLayout.NORTH);

        // --- טקסט החוקים המעודכן ---
        JTextArea rulesText = new JTextArea();
        rulesText.setText(
                "• Press 'Ctrl' to shuffle the puzzle into a random solvable state.\n\n" +
                        "• Use the Arrow Keys (↑, ↓, ←, →) to move the tiles.\n\n" +
                        "• Press 'Enter' to activate the algorithm and watch the puzzle solve itself!"
        );
        rulesText.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rulesText.setForeground(Color.WHITE);
        rulesText.setBackground(new Color(40, 40, 40));
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        panel.add(rulesText, BorderLayout.CENTER);

        // --- כפתור חזרה ---
        JButton backButton = createStyledButton("Back to Menu", new Color(244, 67, 54));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    // פונקציית עזר ליצירת כפתורים מעוצבים
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // אפקט Hover
        Color hoverColor = backgroundColor.brighter();
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    public void startGame() {
        Window gameWindow = new Window(); // ודא ששם חלון המשחק שלך הוא "Window"
        this.dispose(); // סוגר את חלון הפתיחה ומשחרר משאבים
    }
}