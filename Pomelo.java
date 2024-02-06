import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

public class Pomelo {
    static ImageIcon img = new ImageIcon("src/img/pomelo.png");
    public static int getVelocity() {
        return 5;
    }
    public static JLabel getJLabel() {
        int width = 60;
        int height = 60;
        JLabel label = new JLabel(img);
        label.setBounds(0, 0, width, height);
        return label;
    }
    public static Image getImage() {
        return img.getImage();
    }
}