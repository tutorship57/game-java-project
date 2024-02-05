import javax.swing.ImageIcon;

public class Image {
    static ImageIcon icon = new ImageIcon("src/icon.jpg");
    static ImageIcon scoreBox = new ImageIcon("src/scoreBox.png");
    static ImageIcon Obj1 = new ImageIcon("src/Obj1.png");
    static ImageIcon Obj2 = new ImageIcon("src/Obj2.png");
    static ImageIcon Obj3 = new ImageIcon("src/bird.png");
    public static ImageIcon getIcon() {
        return icon;
    }
    public static ImageIcon getScoreBox() {
        return scoreBox;
    }
    public static ImageIcon getObj1ImageIcon() {
        return Obj1;
    }
    public static ImageIcon getObj2ImageIcon() {
        return Obj2;
    }
    public static ImageIcon getObj3ImageIcon() {
        return Obj3;
    }
}
