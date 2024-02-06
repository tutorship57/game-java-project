import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

public class Banana {
    static ImageIcon img = new ImageIcon("src/img/banana.png");
    public static int getVelocity() {
        return 6;
    }
    public static JLabel getJLabel() {
        int width = 60;
        int height = 60;
        JLabel label = new JLabel(img);
        label.setBounds(50,50, width, height);
        return label;
    }
    public static Image getImage() {
        return img.getImage();
    }
}