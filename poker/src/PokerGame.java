import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class PokerGame extends JFrame {
    private JButton player1Button, player2Button;
    private JLabel player1Label, player2Label, roundLabel;
    private JPanel player1Panel, player2Panel, tablePanel;
    private JCheckBox[] player1Checks, player2Checks;
    private JLabel[] player1Cards, player2Cards, tableCards;

    private ArrayList<String> deck;
    private ArrayList<String> table;
    private int player1Points = 0, player2Points = 0;
    private int player1Wins = 0, player2Wins = 0;
    private boolean player1Turn = true;

    public PokerGame() {
        setTitle("Poker Game");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initDeck();
        dealInitialCards();

        // Top Panel for Rounds and Points
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        roundLabel = new JLabel("Round 1", SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 18));

        player1Label = new JLabel("Player 1 Points: 0", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2 Points: 0", SwingConstants.CENTER);
        topPanel.add(player1Label);
        topPanel.add(roundLabel);
        topPanel.add(player2Label);

        add(topPanel, BorderLayout.NORTH);

        // Player 1 Panel
        player1Panel = createPlayerPanel("Player 1", true);
        add(player1Panel, BorderLayout.WEST);

        // Table Panel
        tablePanel = new JPanel(new FlowLayout());
        tableCards = createCardLabels(3);
        JLabel tableLabel = new JLabel("Table Cards:");
        tablePanel.add(tableLabel);
        for (JLabel card : tableCards) tablePanel.add(card);
        add(tablePanel, BorderLayout.CENTER);

        // Player 2 Panel
        player2Panel = createPlayerPanel("Player 2", false);
        add(player2Panel, BorderLayout.EAST);

        updateTable();
    }

    private JPanel createPlayerPanel(String playerName, boolean isPlayer1) {
        JPanel panel = new JPanel(new FlowLayout());
        JLabel playerLabel = new JLabel(playerName);

        JButton changeButton = new JButton("Change");
        JLabel[] cards = createCardLabels(5);
        JCheckBox[] checks = createCheckBoxes(5);

        if (isPlayer1) {
            player1Button = changeButton;
            player1Cards = cards;
            player1Checks = checks;
            changeButton.addActionListener(e -> handleCardChange(1));
        } else {
            player2Button = changeButton;
            player2Cards = cards;
            player2Checks = checks;
            changeButton.addActionListener(e -> handleCardChange(2));
        }

        panel.add(playerLabel);
        for (JLabel card : cards) panel.add(card);
        for (JCheckBox check : checks) panel.add(check);
        panel.add(changeButton);

        return panel;
    }

    private JLabel[] createCardLabels(int count) {
        JLabel[] labels = new JLabel[count];
        for (int i = 0; i < count; i++) {
            labels[i] = new JLabel("??", SwingConstants.CENTER);
            labels[i].setPreferredSize(new Dimension(50, 70));
            labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        return labels;
    }

    private JCheckBox[] createCheckBoxes(int count) {
        JCheckBox[] boxes = new JCheckBox[count];
        for (int i = 0; i < count; i++) boxes[i] = new JCheckBox();
        return boxes;
    }

    private void initDeck() {
        String[] suits = {"♥", "♠", "♦", "♣"};
        deck = new ArrayList<>();
        for (String suit : suits) {
            for (int i = 2; i <= 14; i++) {
                deck.add((i <= 10 ? String.valueOf(i) : getFaceCard(i)) + suit);
            }
        }
        Collections.shuffle(deck);
    }

    private String getFaceCard(int value) {
        return switch (value) {
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            case 14 -> "A";
            default -> "";
        };
    }

    private void dealInitialCards() {
        table = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            table.add(deck.remove(0));
        }
        for (int i = 0; i < 5; i++) {
            player1Cards[i].setText(deck.remove(0));
            player2Cards[i].setText("??");
        }
    }

    private void handleCardChange(int player) {
        JLabel[] cards = (player == 1) ? player1Cards : player2Cards;
        JCheckBox[] checks = (player == 1) ? player1Checks : player2Checks;

        for (int i = 0; i < 5; i++) {
            if (checks[i].isSelected()) {
                cards[i].setText(deck.remove(0));
                checks[i].setSelected(false);
            }
        }

        int points = calculatePoints(cards);
        if (player == 1) {
            player1Points = points;
            player1Label.setText("Player 1 Points: " + points);
            player1Button.setEnabled(false);
            player2Button.setEnabled(true);
        } else {
            player2Points = points;
            player2Label.setText("Player 2 Points: " + points);
            player2Button.setEnabled(false);
            determineWinner();
        }
    }

    private int calculatePoints(JLabel[] cards) {
        Map<String, Integer> valueCounts = new HashMap<>();
        int sum = 0;

        for (JLabel card : cards) {
            String value = card.getText().replaceAll("[♥♠♦♣]", "");
            valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            sum += getValue(value);
        }

        for (int count : valueCounts.values()) {
            if (count == 2) sum *= 2; // Pair
            else if (count == 3) sum *= 3; // Three of a kind
            else if (count == 4) sum *= 4; // Four of a kind
        }

        return sum;
    }

    private int getValue(String value) {
        return switch (value) {
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            case "A" -> 14;
            default -> Integer.parseInt(value);
        };
    }

    private void updateTable() {
        for (int i = 0; i < 3; i++) {
            tableCards[i].setText(table.get(i));
        }
    }

    private void determineWinner() {
        updateTable();
        int player1Total = player1Points;
        int player2Total = player2Points;

        for (JLabel card : tableCards) {
            player1Total += getValue(card.getText().replaceAll("[♥♠♦♣]", ""));
            player2Total += getValue(card.getText().replaceAll("[♥♠♦♣]", ""));
        }

        if (player1Total > player2Total) {
            player1Wins++;
            JOptionPane.showMessageDialog(this, "Player 1 Wins the Round!");
        } else {
            player2Wins++;
            JOptionPane.showMessageDialog(this, "Player 2 Wins the Round!");
        }

        if (player1Wins == 3 || player2Wins == 3) {
            JOptionPane.showMessageDialog(this, (player1Wins == 3 ? "Player 1" : "Player 2") + " Wins the Game!");
            System.exit(0);
        }

        resetGame();
    }

    private void resetGame() {
        player1Turn = !player1Turn;
        player1Button.setEnabled(player1Turn);
        player2Button.setEnabled(!player1Turn);

        initDeck();
        dealInitialCards();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PokerGame().setVisible(true));
    }
}
