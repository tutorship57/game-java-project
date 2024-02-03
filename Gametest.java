import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Gametest {

    Gametest() {
        JFrame frame = new JFrame("Mouse Interaction Example");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(null); // Set layout manager to null for absolute positioning
        panel.setBounds(0, 0, 500, 500); // Set the size of the panel
        panel.setBackground(Color.BLUE);
        frame.add(panel);

        // Create a custom component that responds to mouse events
        CustomComponent customComponent = new CustomComponent(panel);
        frame.add(customComponent);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gametest());
    }
}

class CustomComponent extends JComponent {
    public CustomComponent(JPanel p) {
        Map<Integer,JLabel> spawn = new HashMap<Integer,JLabel>();
        spawn.put(0,new JLabel(new ImageIcon("src/bird.png")));
        spawn.put(1,new JLabel(new ImageIcon("src/icon.jpg")));
        spawn.put(2,new JLabel(new ImageIcon("src/obj1.png")));
        spawn.put(4,new JLabel(new ImageIcon("src/obj2.png")));
        JLabel object1 = new JLabel(new ImageIcon("src/bird.png"));
        object1.setBounds(20, 20, 50, 50);
        Point objectLocation = object1.getLocation();
        System.out.println(objectLocation.x+object1.getWidth());
        p.add(object1);
        spawn.forEach((key,value)->setObject(p,spawn,key));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Handle mouse click event
                if(checkPosition(object1,e)){
                    object1.setIcon(new ImageIcon("src/icon.jpg"));
                }
                System.out.println("Mouse Clicked at (" + e.getX() + ", " + e.getY() + ")");
            }
        });   
    }
    public boolean checkPosition(JLabel obj,MouseEvent e){
        boolean checkX = e.getX() <= obj.getX()+obj.getWidth() && obj.getX()<=e.getX() ;
        boolean checkY = e.getY() <= obj.getY()+obj.getHeight() && obj.getY()<=e.getX() ;
        return checkX && checkY ;
    }
    public void setObject(JPanel j,Map<Integer, JLabel> spawn,int key){
        JLabel temp  = spawn.get(key);
        temp.setBounds(20, 20, 50, 50);
        j.add(temp);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Handle mouse click event
                if(checkPosition(temp,e)){
                    temp.setIcon(new ImageIcon("src/icon.jpg"));
                }
                System.out.println("Mouse Clicked at (" + e.getX() + ", " + e.getY() + ")");
            }
        });
    }
}