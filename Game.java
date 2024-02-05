import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Game {
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    static Random rd = new Random();
    private Clip soundClip;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ImageIcon BirdImg;
    JLabel BirdLabel;
    JFrame frame;
    Container container;
    JPanel panel;
    ImageIcon backgroundImage;
    JLabel backgroundLabel;
    int velocity = 15; // Adjusted the velocity for testing
    int frameWidth = 1080, frameHeight = 720;
    Set<Integer> pressedKeys = new HashSet<>();
    MouseAdapter labelMouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            JLabel pressedLabel = (JLabel)e.getSource();
            pressedLabel.setIcon(Image.getIcon());
            pressedLabel.setVisible(false);
            playSound("src/pop.wav");
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
        backgroundImage = new ImageIcon("src/background.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(null);
        container.add(backgroundLabel);
        frame.setLocationRelativeTo(null);
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
            }
        });
    }
    private void playSound(String soundFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundFilePath));
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
    
            soundClip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                    }
                }
            });
    
            soundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                fallingObject(new Obj1(), delay);
            }
            if(tmp instanceof Obj2) {
                fallingObject(new Obj2(), delay);
            }
            if(tmp instanceof Obj3) {
                fallingObject(new Obj3(), delay);
            }
            delay += 300;
        }
    }
    private void fallingObject(Object obj, int delay){
        JLabel o;
        int velo;
        if (obj instanceof Obj1) {
            o = Obj1.getJLabel();
            velo = Obj1.getVelocity();
        }
        else if (obj instanceof Obj2) {
            o = Obj2.getJLabel();
            velo = Obj2.getVelocity();
        }
        else {
            o = Obj3.getJLabel();
            velo = Obj2.getVelocity();
        } 

        if (o.getMouseListeners().length == 0) {
            o.addMouseListener(labelMouseListener);
        }
        final Icon icon = o.getIcon();
        int minX = 300;
        int maxX = frameWidth - minX - 70;
        scheduler.schedule(() -> {
            int positionX = rd.nextInt(maxX - minX + 1) + minX;
            final int[] positionY = {0};
            final int[] velocity = {velo + 5};
            final int[] acceleration = {0};
            o.setVisible(true);
            Timer timer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backgroundLabel.add(o);
                    positionY[0] += velocity[0] += acceleration[0];
                    o.setLocation(positionX, positionY[0]);
                    
                    if (positionY[0] >= frameHeight) {
                        o.setVisible(false);
                        o.setIcon(icon);
                        backgroundLabel.remove(o);
                        ((Timer) e.getSource()).stop();
                        fallingObject(obj, delay);
                    }
                }
            });
            timer.start();
        }, delay, TimeUnit.MILLISECONDS);
    }
}
