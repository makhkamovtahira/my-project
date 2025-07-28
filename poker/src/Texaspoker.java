import javax.swing.*;
import java.awt.*;

public class Texaspoker {
    private JFrame frame;
    private JPanel player1Panel, player2Panel, deskPanel, controlPanel;
    private JButton redrawButton, revealButton;
    private JLabel player1ScoreLabel, player2ScoreLabel, turnLabel;
    private JCheckBox[] player1CheckBoxes;
    private JLabel[] player1Cards, player2Cards, deskCards;

    private boolean isPlayer1Turn = true; // Определяет текущий ход

    public Texaspoker() {
        // Создание главного окна
        frame = new JFrame("2-Player Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // Панель для игрока 1
        player1Panel = new JPanel();
        player1Panel.setBorder(BorderFactory.createTitledBorder("Player 1 (Face Up)"));
        player1Panel.setLayout(new GridLayout(2, 5));
        player1Cards = new JLabel[5];
        player1CheckBoxes = new JCheckBox[5];
        for (int i = 0; i < 5; i++) {
            player1Cards[i] = new JLabel("Card " + (i + 1), SwingConstants.CENTER);
            player1Cards[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            player1Panel.add(player1Cards[i]);
            player1CheckBoxes[i] = new JCheckBox("Redraw");
            player1Panel.add(player1CheckBoxes[i]);
        }

        // Панель для игрока 2
        player2Panel = new JPanel();
        player2Panel.setBorder(BorderFactory.createTitledBorder("Player 2 (Face Down)"));
        player2Panel.setLayout(new GridLayout(1, 5));
        player2Cards = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            player2Cards[i] = new JLabel("???", SwingConstants.CENTER);
            player2Cards[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            player2Panel.add(player2Cards[i]);
        }

        // Панель для стола (desk)
        deskPanel = new JPanel();
        deskPanel.setBorder(BorderFactory.createTitledBorder("Desk Cards"));
        deskPanel.setLayout(new GridLayout(1, 3));
        deskCards = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            deskCards[i] = new JLabel("Desk " + (i + 1), SwingConstants.CENTER);
            deskCards[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            deskPanel.add(deskCards[i]);
        }

        // Панель управления (кнопки и очки)
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2));
        turnLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER);
        player1ScoreLabel = new JLabel("Player 1 Score: 0", SwingConstants.CENTER);
        player2ScoreLabel = new JLabel("Player 2 Score: 0", SwingConstants.CENTER);
        redrawButton = new JButton("Redraw");
        revealButton = new JButton("Reveal Desk Cards");

        controlPanel.add(turnLabel);
        controlPanel.add(new JLabel());
        controlPanel.add(player1ScoreLabel);
        controlPanel.add(player2ScoreLabel);
        controlPanel.add(redrawButton);
        controlPanel.add(revealButton);

        // Добавление панелей в главное окно
        frame.add(player1Panel, BorderLayout.NORTH);
        frame.add(deskPanel, BorderLayout.CENTER);
        frame.add(player2Panel, BorderLayout.SOUTH);
        frame.add(controlPanel, BorderLayout.EAST);

        // Добавление действий на кнопки
        redrawButton.addActionListener(e -> handleRedraw());
        revealButton.addActionListener(e -> handleReveal());

        frame.setVisible(true);
    }

    private void handleRedraw() {
        if (isPlayer1Turn) {
            JOptionPane.showMessageDialog(frame, "Player 1 Redrawing Cards...");
            // Логика для замены карт Player 1
        } else {
            JOptionPane.showMessageDialog(frame, "Player 2 Redrawing Cards...");
            // Логика для замены карт Player 2
        }
        switchTurn();
    }

    private void handleReveal() {
        JOptionPane.showMessageDialog(frame, "Revealing Desk Cards...");
        for (JLabel deskCard : deskCards) {
            deskCard.setText("Card"); // Здесь добавить логику для открытия карт
        }
    }

    private void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        turnLabel.setText(isPlayer1Turn ? "Player 1's Turn" : "Player 2's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Texaspoker::new);
    }
}
