package GUI.Company;

import GUI.Global;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Title: CustomerOperation.java
 * Description: this class is used to do some operation on customer
 * @author1 Xueying Yang
 * @author2 Jie ji
 * @version 1.0
 */
public class CustomerOperation extends JFrame implements ActionListener, Global {
    private JButton btAdd, btRemove,btCh;
    private JPanel main;
    String cID;
/**
 *
 * @param CID Company ID
 */
    public CustomerOperation(String CID){
        this.cID = CID;
        setTitle("SEMMS-Operating Customer");
        setBounds(100,100,800,600);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(700, 500));
        setLocation(100, 100);

        btAdd=new JButton("add");
        btRemove=new JButton("remove");
        btCh=new JButton("Company Home");


        btAdd.addActionListener(this);
        btRemove.addActionListener(this);
        btCh.addActionListener(this);

        main=new JPanel();
        main.setLayout(new FlowLayout());

        main.add(btAdd);
        main.add(btRemove);
        main.add(btCh);
        add(main,BorderLayout.NORTH);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    @Override
/**
 * this method is used to linsten to an event
 * @param e:action a event
 */
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btAdd){
            dispose();
            AddNewCustomer add=new AddNewCustomer(cID);
        }

        else if(e.getSource()==btRemove){
            dispose();
            RemoveUsers remove=new RemoveUsers(cID);
        }

        else if(e.getSource()==btCh){
            dispose();
            CompanyHomePage ch=new CompanyHomePage(cID);
        }
    }

}
