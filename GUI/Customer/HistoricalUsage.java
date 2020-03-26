package GUI.Customer;

import javax.swing.*;
import java.awt.*;

/**
 * Title: HistoricalUsage.java
 * Description: this class is a GUI which shows historical usage in a table. 
 * @author Yihan Lan
 * @version 1.0
 */
public class HistoricalUsage {
        JFrame frame;
        JRadioButton jrb1;
        JRadioButton jrb2;
        JRadioButton jrb3;
        JPanel panel1;
        JPanel panel1_1;
        JPanel panel1_2;
        JPanel panel2;
        JPanel panel3;
        String id;
        ButtonGroup bg = new ButtonGroup();

    public HistoricalUsage(String id) {
            this.id=id;
            frame=new JFrame();
            panel1=new JPanel();
            panel1_1=new JPanel();
            panel1_2=new JPanel();
            panel2=new JPanel();
            panel3=new JPanel();

            jrb1=new JRadioButton("DAILY");

            jrb2=new JRadioButton("WEEKLY");

            jrb3=new JRadioButton("MONTHLY");

            bg.add(jrb1);
            bg.add(jrb2);
            bg.add(jrb3);
            panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
            panel1.add(panel1_1);
            panel1.add(panel1_2);

            panel1_2.add(jrb1);
            panel1_2.add(jrb2);
            panel1_2.add(jrb3);

            frame.getContentPane().add(BorderLayout.NORTH,panel1);
            frame.getContentPane().add(BorderLayout.CENTER,panel2);
            frame.getContentPane().add(BorderLayout.SOUTH,panel3);
            frame.setSize(800,600);
            frame.setVisible(true);
            frame.setMinimumSize(new Dimension(700, 500));
            frame.setLocation(100, 100);
        }

}
