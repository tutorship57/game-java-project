import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

public class Frog {
    static ImageIcon img = new ImageIcon("src/img/frog.png");
    public static int getVelocity() {
        return 4;
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
