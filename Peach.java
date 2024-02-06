import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

public class Peach {
    static ImageIcon img = new ImageIcon("src/img/peach.png");
    public static int getVelocity() {
        return 4;
    }
    public static JLabel getJLabel() {
        int width = 65;
        int height = 65;
        JLabel label = new JLabel(img);
        label.setBounds(0, 0, width, height);
        return label;
    }
    public static Image getImage() {
        return img.getImage();
    }
}
