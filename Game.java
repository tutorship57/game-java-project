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
    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 720;

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static Random rd = new Random();
    private static int bestScore;
    private static Clip soundClip;
    private static JLabel scoreBox;
    private static JFrame frame;
    private static Container container;
    private static ImageIcon backgroundImage;
    private static JLabel backgroundlabel;
    private static int score = 0;
    private static Set<Integer> pressedKeys = new HashSet<>();

    MouseAdapter wrongLabelListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            JLabel pressedLabel = (JLabel)e.getSource();
            gameUpdate(pressedLabel);
            playSound("src/sound/quack.wav");
        }
    };
    MouseAdapter labelMouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            JLabel pressedLabel = (JLabel)e.getSource();
            gameUpdate(pressedLabel);
            playSound("src/sound/pop.wav");
        }
    };
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Game();
            }
        });
    }
    public Game() {
        frame = new JFrame("My Tutor Game");
        container = frame.getContentPane();

        Components();
        scoreBox();
        startFalling(20);

        frame.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void scoreBox() {
        Color textColor = new Color(255, 0, 0); // ตั้งค่าสี (RGB)
        Font font = new Font("Arial", Font.BOLD, 20);
        scoreBox = new JLabel();
        scoreBox.setBounds(20, 20, 150, 100);
        backgroundlabel.add(scoreBox);
        scoreBox.setFont(font);
        scoreBox.setForeground(textColor);
        scoreBox.setText("Score: " + score);
    }
    private void gameUpdate(JLabel clickedLabel) {
        if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Pomelo.getImage())) {
            score += 1;
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Banana.getImage())) {
            score += 2;
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Frog.getImage())) {
            score -= 1; 
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Tomato.getImage())) {
            score += 1; 
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Peach.getImage())) {
            score += 1; 
        }
        scoreBox.setText("Score: " + score);
        clickedLabel.setVisible(false);
        clickedLabel.setLocation(clickedLabel.getX(), FRAME_HEIGHT);

    }
    private void Components() {
        backgroundImage = new ImageIcon("src/img/garden.jpg");
        backgroundlabel = new JLabel(backgroundImage);
        backgroundlabel.setLayout(null);
        container.add(backgroundlabel);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                pressedKeys.add(code);
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
    private static Object getRandomObject() {
        Object[] objects =  {   new Pomelo(), 
                                new Banana(), 
                                new Frog(),
                                new Tomato(),
                                new Peach()
                            };
        return objects[rd.nextInt(objects.length)];
    }
    private void startFalling(int amount) {
        int delay = 500;
        for(int i = 0; i < amount; i++) {
            Object obj = getRandomObject();
            fallingObject(obj, delay);
            delay += 500;
        }
    }
    private void fallingObject(Object obj, int delay){
        JLabel label = checkJLabel(obj);
        int velo = checkVelocity(obj);

        if (label.getMouseListeners().length == 0) {
            if (obj instanceof Frog)
                label.addMouseListener(wrongLabelListener);
            else
                label.addMouseListener(labelMouseListener);
        }
        final Icon icon = label.getIcon();
        int minX = 300;
        int maxX = FRAME_WIDTH - minX - 70;
        scheduler.schedule(() -> {
            int positionX = rd.nextInt(maxX - minX + 1) + minX;
            final int[] positionY = {0};
            final double[] velocity = {velo};
            final double[] acceleration = {0.1};
            label.setVisible(true);
            Timer timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backgroundlabel.add(label);
                    positionY[0] += velocity[0] += (acceleration[0]);
                    label.setLocation(positionX, positionY[0]);
                    
                    if (positionY[0] >= FRAME_HEIGHT) {
                        label.setVisible(false);
                        label.setIcon(icon);
                        backgroundlabel.remove(label);
                        ((Timer) e.getSource()).stop();
                        fallingObject(getRandomObject(), delay);;
                    }
                }
            });
            timer.start();
        }, delay, TimeUnit.MILLISECONDS);
    }
    private JLabel checkJLabel(Object obj) {
        if (obj instanceof Pomelo) 
            return Pomelo.getJLabel();
        else if (obj instanceof Banana) 
            return Banana.getJLabel();
        else if (obj instanceof Peach)
            return Peach.getJLabel();
        else if (obj instanceof Tomato)
            return Tomato.getJLabel();
        else 
            return Frog.getJLabel();
    }
    private int checkVelocity(Object obj) {
        if (obj instanceof Pomelo) 
            return Pomelo.getVelocity();
        else if (obj instanceof Banana) 
            return Banana.getVelocity();
        else if (obj instanceof Peach)
            return Peach.getVelocity();
        else if (obj instanceof Tomato)
            return Tomato.getVelocity();
        else 
            return Frog.getVelocity();
    }
}
