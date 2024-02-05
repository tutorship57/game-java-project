import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    static Random rd = new Random();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ImageIcon BirdImg;
    JLabel BirdLabel;
    JFrame frame;
    Container container;
    JPanel panel;
    ImageIcon backgroundImage;
    JLabel backgroundLabel;
    int velocity = 15; // Adjusted the velocity for testing
    int frameWidth = 1280, frameHeight = 720;
    Set<Integer> pressedKeys = new HashSet<>();
    MouseAdapter labelMouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            JLabel pressedLabel = (JLabel)e.getSource();
            pressedLabel.setIcon(Image.getIcon());
        }
    };
    public static void main(String[] args) {
        new Game();
    }
    public Game() {
        frame = new JFrame("My Tutor Game");
        container = frame.getContentPane();

        Components();
        randomFalling(10);

        frame.setBounds(0, 0, frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void Components() {
        // Add backgroundLabel to the frame with null layout manager
        backgroundImage = new ImageIcon("src/background.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(null);
        container.add(backgroundLabel);
        frame.setLocationRelativeTo(null);
        
        // Bird
        BirdImg = new ImageIcon("src/bird.png");
        BirdLabel = new JLabel(BirdImg);
        BirdLabel.setBounds(50, 50, 51, 51); // Set size directly
        backgroundLabel.add(BirdLabel);
        BirdLabel.addMouseListener(labelMouseListener);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                pressedKeys.add(code);
                handleMovement();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                pressedKeys.remove(code);
                handleMovement();
            }
        });
    }
    private void handleMovement() {
        // goUp
        if (pressedKeys.contains(KeyEvent.VK_W) || pressedKeys.contains(KeyEvent.VK_UP)) {
            if (BirdLabel.getY() > 0)
                BirdLabel.setLocation(BirdLabel.getX(), BirdLabel.getY() - velocity);
            else BirdLabel.setLocation(BirdLabel.getX(), 0);
        }
        // goDown
        if (pressedKeys.contains(KeyEvent.VK_S) || pressedKeys.contains(KeyEvent.VK_DOWN)) {
            if (BirdLabel.getY() < frameHeight - BirdLabel.getHeight())
                BirdLabel.setLocation(BirdLabel.getX(), BirdLabel.getY() + velocity);
            else BirdLabel.setLocation(BirdLabel.getX(), frameHeight - BirdLabel.getHeight());

        }
        // goLeft
        if (pressedKeys.contains(KeyEvent.VK_A) || pressedKeys.contains(KeyEvent.VK_LEFT)) {
            if (BirdLabel.getX() > 0)
                BirdLabel.setLocation(BirdLabel.getX() - velocity, BirdLabel.getY());
            else BirdLabel.setLocation(0, BirdLabel.getY());
        }
        // goRight
        if (pressedKeys.contains(KeyEvent.VK_D) || pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            if (BirdLabel.getX() < frameWidth - BirdLabel.getWidth())
                BirdLabel.setLocation(BirdLabel.getX() + velocity, BirdLabel.getY());
            else BirdLabel.setLocation(frameWidth - BirdLabel.getWidth(), BirdLabel.getY());
        }
        System.out.println("X:" + BirdLabel.getX() + " Y:" + BirdLabel.getY());
    }
    private void randomObject(JLabel o, int delay) {
        int minX = 250;
        int maxX = frameWidth - minX;
        int minY = 100;
        int maxY = frameHeight - minY;
        backgroundLabel.add(o);
        scheduler.scheduleAtFixedRate(() -> {
            int positionX = rd.nextInt(maxX - minX + 1) + minX;
            int positionY = rd.nextInt(maxY - minY + 1) + minY;
            o.setLocation(positionX, positionY);
            o.setVisible(true);
            scheduler.schedule(() -> {
                o.setVisible(false);
            }, 700, TimeUnit.MILLISECONDS);
        }, 0, delay, TimeUnit.MILLISECONDS);
    }
    private void randomFalling(int amount) {
        int delay = 500;
        HashMap <Integer, Object> ObjContainer = new HashMap<>();
        ObjContainer.put(0,new Obj1());
        ObjContainer.put(1,new Obj2());
        ObjContainer.put(2,new Obj3());

        for(int i = 0; i < amount; i++) {
            Object tmp = ObjContainer.get(rd.nextInt(ObjContainer.size()));
            if(tmp instanceof Obj1) {
                fallingObject(Obj1.getJLabel(), 2, delay);
            }
            if(tmp instanceof Obj2) {
                fallingObject(Obj2.getJLabel(), 3, delay);
            }
            if(tmp instanceof Obj3) {
                fallingObject(Obj3.getJLabel(), 1, delay);
            }
            delay += 100;
        }
    }
    private void fallingObject(JLabel o, int a, int delay) {
        if (o.getMouseListeners().length == 0) {
            o.addMouseListener(labelMouseListener);
        }
        final Icon icon = o.getIcon();
        int minX = 50;
        int maxX = frameWidth - minX;
        scheduler.schedule(() -> {
            int positionX = rd.nextInt(maxX - minX + 1) + minX;
            final int[] positionY = {0};
            final int[] velocity = {5};
            final int[] acceleration = {0};
        
            Timer timer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backgroundLabel.add(o);
                    o.setVisible(true);
                    positionY[0] += velocity[0] += acceleration[0];
                    o.setLocation(positionX, positionY[0]);
                    
                    if (positionY[0] >= frameHeight) {
                        o.setVisible(false);
                        o.setIcon(icon);
                        ((Timer) e.getSource()).stop();
                        fallingObject(o, a, delay);
                    }
                }
            });
            timer.start();
        }, delay, TimeUnit.MILLISECONDS);
    }
}
