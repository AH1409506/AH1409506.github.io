
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class MathMaster {

    static int score = 0;
    static int target;
    static Integer firstNumber = null;

    static JLabel questionLabel;
    static JLabel scoreLabel;
    static JLabel messageLabel;
    static JPanel numberPanel;

    static Random random = new Random();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Math Master");
        frame.setSize(550, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 26));

        scoreLabel = new JLabel(
                "Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        messageLabel = new JLabel(
                "Find two numbers that add up to the target!",
                SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        topPanel.add(questionLabel);
        topPanel.add(scoreLabel);
        topPanel.add(messageLabel);

        numberPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        numberPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(numberPanel, BorderLayout.CENTER);

        newQuestion();

        frame.setVisible(true);
    }

    static void newQuestion() {

        firstNumber = null;

        // Choose two numbers that add up to the target.
        int a = random.nextInt(9) + 1;
        int b = random.nextInt(9) + 1;

        target = a + b;

        HashSet<Integer> numbers = new HashSet<>();
        numbers.add(a);
        numbers.add(b);
        numbers.add(target);

        // Fill the table with unique numbers.
        while (numbers.size() < 16) {
            numbers.add(random.nextInt(30) + 1);
        }

        ArrayList<Integer> tableNumbers =
                new ArrayList<>(numbers);

        Collections.shuffle(tableNumbers);

        questionLabel.setText("Find two numbers that equal " + target);
        messageLabel.setText("Click two numbers in the table.");
        messageLabel.setForeground(Color.BLACK);

        numberPanel.removeAll();

        for (int number : tableNumbers) {

            JButton button = new JButton(String.valueOf(number));
            button.setFont(new Font("Arial", Font.BOLD, 24));

            if (number == target) {
                button.setBackground(new Color(255, 220, 100));
                button.setToolTipText("This is the target number.");
            } else {
                button.setBackground(new Color(220, 235, 255));
                button.addActionListener(
                        e -> checkNumber(number, button));
            }

            numberPanel.add(button);
        }

        numberPanel.revalidate();
        numberPanel.repaint();
    }

    static void checkNumber(int number, JButton button) {

        if (firstNumber == null) {

            firstNumber = number;

            button.setBackground(new Color(150, 200, 255));
            messageLabel.setText(
                    "First number: " + number + ". Choose another!");

        } else {

            int sum = firstNumber + number;

            if (sum == target) {

                score++;
                scoreLabel.setText("Score: " + score);

                messageLabel.setText(
                        firstNumber + " + " + number + " = "
                        + target + "! Correct!");

                messageLabel.setForeground(
                        new Color(20, 140, 60));

                showNextQuestion();

            } else {

                score = Math.max(0, score - 1);
                scoreLabel.setText("Score: " + score);

                messageLabel.setText(
                        firstNumber + " + " + number
                        + " is not " + target + ". Try again!");

                messageLabel.setForeground(Color.RED);

                showNextQuestion();
            }
        }
    }

    static void showNextQuestion() {

        Timer timer = new Timer(900, e -> newQuestion());
        timer.setRepeats(false);
        timer.start();
    }
}