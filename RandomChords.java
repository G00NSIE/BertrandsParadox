
import javax.swing.*;
import java.awt.*;

public class RandomChords extends JPanel {
    private JTextField trialsField = new JTextField("1000", 5);
    private JLabel trialsLabel = new JLabel("Trials: 0");
    private JLabel successesLabel = new JLabel("Successes: 0");
    private JLabel frequencyLabel = new JLabel("Success frequency: 0");
    private JButton startButton = new JButton("Start");
    private JRadioButton endpointsButton = new JRadioButton("Random Endpoints", true);
    private JRadioButton midpointButton = new JRadioButton("Random Midpoint");
    private JRadioButton radialButton = new JRadioButton("Random Radial Point");
    private int n_trials = 0;
    private int success = 0;
    private Timer timer;

    public RandomChords() {
        setPreferredSize(new Dimension(600, 600));
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Number of trials:"));
        controlPanel.add(trialsField);
        controlPanel.add(startButton);
        controlPanel.add(trialsLabel);
        controlPanel.add(successesLabel);
        controlPanel.add(frequencyLabel);

        ButtonGroup group = new ButtonGroup();
        group.add(endpointsButton);
        group.add(midpointButton);
        group.add(radialButton);
        controlPanel.add(endpointsButton);
        controlPanel.add(midpointButton);
        controlPanel.add(radialButton);

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);

        startButton.addActionListener(e -> {
            n_trials = 0;
            success = 0;
            trialsLabel.setText("Trials: " + n_trials);
            successesLabel.setText("Successes: " + success);
            frequencyLabel.setText("Success frequency: " + (double) success / n_trials);
            if (timer != null) {
                timer.stop();
            }
            timer = new Timer(10, evt -> {
                if (n_trials < Integer.parseInt(trialsField.getText())) {
                    double x1, y1, x2, y2;
                    if (endpointsButton.isSelected()) {
                        double angle1 = Math.random() * 2 * Math.PI;
                        double angle2 = Math.random() * 2 * Math.PI;
                        x1 = Math.cos(angle1);
                        y1 = Math.sin(angle1);
                        x2 = Math.cos(angle2);
                        y2 = Math.sin(angle2);
                    } else if (midpointButton.isSelected()) {
                        x1 = Math.random() - 0.5;
                        y1 = Math.random() - 0.5;
                        double distance = Math.sqrt(x1 * x1 + y1 * y1);
                        if (distance > 0.5) {
                            success++;
                        }
                        x2 = -x1;
                        y2 = -y1;
                    } else {
                        double angle = Math.random() * 2 * Math.PI;
                        double radius = Math.random();
                        x1 = radius * Math.cos(angle);
                        y1 = radius * Math.sin(angle);
                        x2 = -y1;
                        y2 = x1;
                        if (radius < 0.5) {
                            success++;
                        }
                    }
                    Graphics g = getGraphics();
                    g.drawLine(300 + (int) (x1 * 250), 300 + (int) (y1 * 250), 300 + (int) (x2 * 250), 300 + (int) (y2 * 250));
                    if (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) > Math.sqrt(3)) {
                        success++;
                    }
                    n_trials++;
                    trialsLabel.setText("Trials: " + n_trials);
                    successesLabel.setText("Successes: " + success);
                    frequencyLabel.setText("Success frequency: " + (double) success / n_trials);
                } else {
                    ((Timer) evt.getSource()).stop();
                }
            });
            timer.start();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(50, 50, 500, 500);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Chords");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new RandomChords());
        frame.pack();
        frame.setVisible(true);
    }
}