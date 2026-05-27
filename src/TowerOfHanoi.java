import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class TowerOfHanoi extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int MAX_DISKS = 10;
    private static final int WAIT_TIME = 10; //500 Default

    // The three pegs tracked using standard Java Stacks
    private Stack<Integer>[] towers = new Stack[3];
    private int totalMoves = 0;
    private int selectedPeg = -1;

    private GamePanel gamePanel;
    private JLabel statusLabel;
    private JLabel largestLabel;
    private JLabel testLabel;

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
        statusLabel = new JLabel("Click a peg to move manually, or press 'Run My Code'!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        largestLabel = new JLabel("Biggest stack peg: " + biggestStackPeg(), SwingConstants.CENTER);
        largestLabel.setFont(new Font("Arial", Font.BOLD, 16));
        largestLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        testLabel = new JLabel("biggest disk: " + towers[0].getLast(), SwingConstants.CENTER);
        testLabel.setFont(new Font("Arial", Font.BOLD, 16));
        testLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel controlPanel = new JPanel();
        JButton resetButton = new JButton("Reset Game");
        JButton customCodeButton = new JButton("Run My Code");

        resetButton.addActionListener(e -> resetGame());

        // This triggers your custom algorithm thread
        customCodeButton.addActionListener(e -> {
//            resetGame();
            statusLabel.setText("Running your custom algorithm...");
            new Thread(() -> {
                // Calls your custom method below
                runMyCustomAlgorithm();
                statusLabel.setText("Your code finished executing! Total moves: " + totalMoves);
            }).start();
        });

        controlPanel.add(resetButton);
        controlPanel.add(customCodeButton);

        add(statusLabel, BorderLayout.NORTH);
//        add(largestLabel, BorderLayout.EAST);
//        add(testLabel, BorderLayout.EAST);
        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // =================================================================
    // WRITE YOUR OWN CODE HERE
    // =================================================================

    public double biggestDisk = MAX_DISKS;
    public boolean isOdd(int i){

        return(i % 2 == 1);
    }
    public int goalPeg(int stackSize, int currentPeg, int goalPeg){

        if(isOdd(stackSize)){

           return goalPeg;
        }
        else{

            if(currentPeg == 0){

                if(goalPeg == 1){

                    return 2;
                }
                else{

                    return 1;
                }
            }
            else if(currentPeg == 1){

                if(goalPeg == 0){

                    return 2;
                }
                else{

                    return 0;
                }
            }
            else{

                if(goalPeg == 0){

                    return 1;
                }
                else{

                    return 0;
                }
            }
        }

    }
    public int storePeg(int currentPeg, int goalPeg){

        return goalPeg(2, currentPeg, goalPeg);
    }
    public int biggestStackPeg(){

        int currentLargest = 0;
        int candidate = -1;

        for(int i = 0; i < 3; i++){

            if(towers[i].size() > currentLargest){

                currentLargest = towers[i].size();
                candidate = i;
            }

        }

        return candidate;
    }

    public void sequence1(int startPeg, int endPeg){

        if(startPeg != endPeg){

            moveDisk(startPeg, goalPeg(1, startPeg, endPeg));
        }

    }

    public void sequence2(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence1(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence1(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence3(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence2(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence2(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence4(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence3(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence3(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence5(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence4(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence4(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence6(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence5(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence5(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence7(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence6(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence6(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence8(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence7(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence7(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence9(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence8(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence8(storePeg(startPeg, endPeg), endPeg);
        }
    }

    public void sequence10(int startPeg, int endPeg){

        if(startPeg != endPeg) {

            sequence9(startPeg, storePeg(startPeg, endPeg));

            moveDisk(startPeg, endPeg);

            sequence9(storePeg(startPeg, endPeg), endPeg);
        }
    }




    private void runMyCustomAlgorithm() {
//        largestLabel.setText("Peg with largest stack: " + biggestStackPeg());
//        testLabel.setText("Largest disk: " + towers[0].firstElement());

        sequence10(0, 2);



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
        if (selectedPeg == -1) {
            if (towers[pegIndex].isEmpty()) {
                statusLabel.setText("That peg is empty! Select another.");
                return;
            }
            selectedPeg = pegIndex;
            statusLabel.setText("Selected Peg " + (pegIndex + 1) + ". Click target peg to drop.");
        } else {
            int from = selectedPeg;
            int to = pegIndex;
            selectedPeg = -1;

            if (from == to) {
                statusLabel.setText("Cancelled move.");
                gamePanel.repaint();
                return;
            }

            if (!towers[to].isEmpty() && towers[from].peek() > towers[to].peek()) {
                statusLabel.setText("Illegal Move! Cannot place larger disk on smaller disk.");
            } else {
                towers[to].push(towers[from].pop());
                totalMoves++;
                if (towers[2].size() == MAX_DISKS) {
                    statusLabel.setText("Victory! You solved it manually in " + totalMoves + " moves!");
                } else {
                    statusLabel.setText("Moves: " + totalMoves);
                }
            }
        }
        gamePanel.repaint();
    }

    private void resetDisks() {
        for (int i = 0; i < 3; i++) towers[i].clear();
        for (int i = MAX_DISKS; i > 0; i--) towers[0].push(i);
    }

    private void resetGame() {
        resetDisks();
        totalMoves = 0;
        selectedPeg = -1;
        statusLabel.setText("Game reset. Ready.");
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

                    g2.setColor(new Color((diskSize * 45) % 255, (diskSize * 90) % 255, 200));
                    g2.fillRoundRect(diskX, diskY, diskWidth, diskHeight, 10, 10);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TowerOfHanoi().setVisible(true));
    }
}
