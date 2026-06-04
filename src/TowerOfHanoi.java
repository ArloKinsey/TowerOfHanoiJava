import javax.swing.*;
import java.awt.*;
import java.util.Stack;


public class TowerOfHanoi extends JFrame {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 600;
    private static  int MAX_DISKS = 5;
    private static int WAIT_TIME = 500;

    // The three pegs tracked using standard Java Stacks
    private final Stack<Integer>[] towers = new Stack[3];
    private int totalMoves = 0;
    private int selectedPeg = -1;

    private final GamePanel gamePanel;
    private final JLabel statusLabel;
    private boolean lockControls = false;
    private int colorInt = 1;

    public TowerOfHanoi() {
        setTitle("Tower of Hanoi - My Algorithm Lab");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize towers and disks
        for (int i = 0; i < 3; i++) {
            towers[i] = new Stack<>();
        }
        resetDisks();

        // Create UI components
        gamePanel = new GamePanel();
        statusLabel = new JLabel("Click a peg to move manually, or press 'Run Code'!          ", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        Dimension statusLabelSize = statusLabel.getPreferredSize();
        statusLabel.setPreferredSize(statusLabelSize);

        JPanel controlPanel = new JPanel();
        JButton resetButton = new JButton("Reset Game");
        JButton customCodeButton = new JButton("Run Code");

        JSlider diskAmountSlider = new JSlider(1, 10);
        diskAmountSlider.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel disksLabel = new JLabel("Disks: 10", SwingConstants.CENTER);
        disksLabel.setFont(new Font("Arial", Font.BOLD, 16));
        disksLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Dimension disksLabelSize = disksLabel.getPreferredSize();
        disksLabel.setPreferredSize(disksLabelSize);
        disksLabel.setText("Disks: " + diskAmountSlider.getValue());

        JSlider speedSlider = new JSlider(0, 1000);

        JLabel speedLabel = new JLabel("Speed (ms): 1000", SwingConstants.RIGHT);
        speedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        speedLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Dimension speedLabelSize = speedLabel.getPreferredSize();
        speedLabel.setPreferredSize(speedLabelSize);
        speedLabel.setText("Speed (ms): " + WAIT_TIME);

        JPanel colorPanel = new JPanel();

        JButton redButton = new JButton();
        redButton.setBackground(new Color(200, 50, 50));
        redButton.setPreferredSize(new Dimension(50, 30));
        redButton.addActionListener(e -> {

           colorInt = 1;
           gamePanel.repaint();

        });

        JButton greenButton = new JButton();
        greenButton.setBackground(new Color(50, 200, 50));
        greenButton.setPreferredSize(new Dimension(50, 30));
        greenButton.addActionListener(e -> {

            colorInt = 2;
            gamePanel.repaint();

        });

        JButton blueButton = new JButton();
        blueButton.setBackground(new Color(50, 50, 200));
        blueButton.setPreferredSize(new Dimension(50, 30));
        blueButton.addActionListener(e -> {

            colorInt = 3;
            gamePanel.repaint();

        });


        resetButton.addActionListener(e -> {

            resetGame();
            speedSlider.setValue(500);

        });

        // This triggers your custom algorithm thread
        customCodeButton.addActionListener(e -> {

            lockControls = true;

            speedSlider.setValue(500);

            //resetGame();
            controlPanel.add(speedSlider);
            controlPanel.add(speedLabel);
            controlPanel.remove(resetButton);
            controlPanel.remove(customCodeButton);
            controlPanel.remove(diskAmountSlider);
            controlPanel.remove(disksLabel);

            statusLabel.setText("Running custom algorithm...");

            new Thread(() -> {
                // Calls your custom method below
                runMyCustomAlgorithm();
                statusLabel.setText("Code finished executing! Total moves: " + totalMoves);

                controlPanel.remove(speedSlider);
                controlPanel.remove(speedLabel);
                controlPanel.add(resetButton);
                controlPanel.add(customCodeButton);
                controlPanel.add(diskAmountSlider);
                controlPanel.add(disksLabel);
                lockControls = false;

            }).start();
        });

        diskAmountSlider.addChangeListener(e -> {

            resetGame();
            MAX_DISKS = diskAmountSlider.getValue();
            disksLabel.setText("Disks: " + MAX_DISKS);

        });

        speedSlider.addChangeListener(e -> {

            WAIT_TIME = 1000 - speedSlider.getValue();
            speedLabel.setText("Speed (ms): " + WAIT_TIME);

        });

        controlPanel.add(resetButton);
        controlPanel.add(customCodeButton);
        controlPanel.add(diskAmountSlider);
        controlPanel.add(disksLabel);

        colorPanel.add(redButton);
        colorPanel.add(greenButton);
        colorPanel.add(blueButton);
        colorPanel.add(statusLabel);

        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(colorPanel, BorderLayout.NORTH);
    }

    // =================================================================
    // WRITE YOUR OWN CODE HERE
    // =================================================================

    public int storePeg(int currentPeg, int goalPeg){

        if(currentPeg != goalPeg) {

            for (int i = 0; i < 2; i++) {

                if (i != currentPeg && i != goalPeg) {

                    return i;
                }
            }
            return 2;
        }
        return -1;

    }

    public Point findThisDisk(int diskSize){

        for(int i = 0; i < 3; i++){

            if(!towers[i].isEmpty() && towers[i].contains(diskSize)){

               return new Point(i, towers[i].indexOf(diskSize));
            }
        }

        return new Point(-1, -1);
    }

    public boolean isMovePossible(int size, int start, int end){

        if(start == end){

            return true;
        }
        else if(towers[start].lastElement() == size) {

            if (!towers[end].isEmpty()) {

                if (!towers[start].isEmpty()) {

                    return towers[start].lastElement() < towers[end].lastElement();

                } else {

                    return false;
                }
            } else {

                return true;
            }
        }
        else{
            return false;
        }
    }

    public void betterSolve(int size, int start, int end) {

        if (size > 1) {

            if (isMovePossible(size, start, end)) {

                moveDisk(start, end);
                betterSolve(size - 1, findThisDisk(size - 1).x, end);
            } else {

                betterSolve(size - 1, findThisDisk(size - 1).x, storePeg(start, end));
                betterSolve(size, start, end);
            }
        }
        else{

            moveDisk(start, end);
        }

    }

    private void runMyCustomAlgorithm() {

        betterSolve(MAX_DISKS, findThisDisk(MAX_DISKS).x, 2);

    }
    // =================================================================

    /**
     * Moves a disk from one peg to another safely and handles UI rendering.
     * Use this method inside your custom algorithm!
     * @param from The source peg index (0, 1, or 2)
     * @param to The destination peg index (0, 1, or 2)
     */
    public void moveDisk(int from, int to) {
        if (from < 0 || from > 2 || to < 0 || to > 2) return;
        if (towers[from].isEmpty()) return;
        if (from == to) return;

        // Rules check to prevent game state breaking
        if (!towers[to].isEmpty() && towers[from].peek() > towers[to].peek()) {
            System.out.println("Warning: Your algorithm attempted an illegal move (larger on smaller)!");
            return;
        }

        towers[to].push(towers[from].pop());
        totalMoves++;
        gamePanel.repaint();

        // Pause for half a second between moves so you can watch your code execute visually
        try { Thread.sleep(WAIT_TIME); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    private void handlePegClick(int pegIndex) {

        if(!lockControls) {
            if (selectedPeg == -1) {
                if (towers[pegIndex].isEmpty()) {
                    statusLabel.setText("That peg is empty! Select another.");
                    return;
                }
                selectedPeg = pegIndex;
                statusLabel.setText("Selected Peg " + (pegIndex + 1) + ". Click target peg to drop.");
            } else {
                int from = selectedPeg;
                selectedPeg = -1;

                if (from == pegIndex) {
                    statusLabel.setText("Cancelled move.");
                    gamePanel.repaint();
                    return;
                }

                if (!towers[pegIndex].isEmpty() && towers[from].peek() > towers[pegIndex].peek()) {
                    statusLabel.setText("Illegal Move! Cannot place larger disk on smaller disk.");
                } else {
                    towers[pegIndex].push(towers[from].pop());
                    totalMoves++;
                    System.out.println(storePeg(1, 0));
                    if (towers[2].size() == MAX_DISKS) {
                        statusLabel.setText("Victory! You solved it manually in " + totalMoves + " moves!");
                    } else {
                        statusLabel.setText("Moves: " + totalMoves);
                    }
                }
            }
            gamePanel.repaint();
        }
    }

    private void resetDisks() {
        for (int i = 0; i < 3; i++) towers[i].clear();
        for (int i = MAX_DISKS; i > 0; i--) towers[0].push(i);
    }

    private void resetGame() {
        resetDisks();
        totalMoves = 0;
        selectedPeg = -1;
        statusLabel.setText("Game reset.");
        gamePanel.repaint();
    }

    private class GamePanel extends JPanel {
        public GamePanel() {
            setBackground(Color.WHITE);
            setLayout(new GridLayout(1, 3));
            for (int i = 0; i < 3; i++) {
                final int index = i;
                JButton pegButton = new JButton();
                pegButton.setOpaque(false);
                pegButton.setContentAreaFilled(false);
                pegButton.setBorderPainted(false);
                pegButton.addActionListener(e -> handlePegClick(index));
                add(pegButton);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int sectionWidth = getWidth() / 3;
            g2.setColor(new Color(139, 69, 19));
            g2.fillRect(10, getHeight() - 40, getWidth() - 20, 20);

            for (int i = 0; i < 3; i++) {
                int pegX = (i * sectionWidth) + (sectionWidth / 2) - 5;
                g2.setColor(i == selectedPeg ? Color.RED : Color.DARK_GRAY);
                g2.fillRect(pegX, 100, 10, getHeight() - 140);

                Object[] disks = towers[i].toArray();
                for (int j = 0; j < disks.length; j++) {
                    int diskSize = (Integer) disks[j];
                    int diskWidth = diskSize * 30 + 20;
                    int diskHeight = 20;
                    int diskX = (i * sectionWidth) + (sectionWidth / 2) - (diskWidth / 2);
                    int diskY = getHeight() - 40 - ((j + 1) * diskHeight);

                    if(colorInt == 1){

                        g2.setColor(new Color(100 + ((diskSize * 60) % 180), 0, 0));
                    }
                    else if(colorInt == 2){

                        g2.setColor(new Color(0, 100 + ((diskSize * 60) % 180), 0));
                    }
                    else{

                        g2.setColor(new Color(0, 50, 100 + ((diskSize * 60) % 180)));
                    }

                    g2.fillRoundRect(diskX, diskY, diskWidth, diskHeight, 10, 10);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TowerOfHanoi().setVisible(true));
    }
}
