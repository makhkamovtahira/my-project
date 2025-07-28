import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Poker extends JFrame {
    private JButton player1Button, player2Button;
    private JLabel player1Label, player2Label;
    private JPanel player1Panel, player2Panel, tablePanel;
    private JCheckBox[] player1Checks, player2Checks;
    private JLabel[] player1Cards, player2Cards, tableCards;

    private ArrayList<String> deck;
    private ArrayList<String> table;
    private int player1Points = 0, player2Points = 0;
    private boolean player1Turn = true;

    public Poker() {
        setTitle("Card Game");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        initDeck();
        dealInitialCards();

        // Player 1 Panel
        player1Panel = new JPanel(new FlowLayout());
        player1Label = new JLabel("Player 1 Points: " + player1Points);
        player1Button = new JButton("Change");
        player1Button.addActionListener(e -> changeCards(1));
        player1Cards = createCardLabels(5);
        player1Checks = createCheckBoxes(5);
        addToPanel(player1Panel, player1Label, player1Cards, player1Checks, player1Button);

        // Table Panel
        tablePanel = new JPanel(new FlowLayout());
        tableCards = createCardLabels(3);
        JLabel tableLabel = new JLabel("Table Cards:");
        addToPanel(tablePanel, tableLabel, tableCards, null, null);

        // Player 2 Panel
        player2Panel = new JPanel(new FlowLayout());
        player2Label = new JLabel("Player 2 Points: " + player2Points);
        player2Button = new JButton("Change");
        player2Button.addActionListener(e -> changeCards(2));
        player2Cards = createCardLabels(5);
        player2Checks = createCheckBoxes(5);
        addToPanel(player2Panel, player2Label, player2Cards, player2Checks, player2Button);

        add(player1Panel);
        add(tablePanel);
        add(player2Panel);

        updateTable();
    }

    private void addToPanel(JPanel panel, JLabel label, JLabel[] cards, JCheckBox[] checks, JButton button) {
        panel.add(label);
        for (JLabel card : cards) panel.add(card);
        if (checks != null) for (JCheckBox check : checks) panel.add(check);
        if (button != null) panel.add(button);
    }

    private JLabel[] createCardLabels(int count) {
        JLabel[] labels = new JLabel[count];
        for (int i = 0; i < count; i++) {
            labels[i] = new JLabel("??");
            labels[i].setPreferredSize(new Dimension(50, 70));
            labels[i].setBorder(BorderFactory.createLineBorder(Color.RED));
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
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
        for (String suit : suits)
            for (int i = 2; i <= 14; i++)
                deck.add((i <= 10 ? String.valueOf(i) : getFaceCard(i)) + suit);
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
        for (int i = 0; i < 3; i++) table.add(deck.remove(0));
    }

    private void changeCards(int player) {
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
        } else {
            player2Points = points;
            player2Label.setText("Player 2 Points: " + points);
        }
        if (!player1Turn) updateTable();
        player1Turn = !player1Turn;
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
        for (int i = 0; i < 3; i++) tableCards[i].setText(table.get(i));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Poker().setVisible(true));
    }
}
