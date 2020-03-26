package GUI.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import EntityClasses.*;

/**
 * Title: bills.java
 * Description: this class is used to display bills' information for user.
 * @author  Songminghuang Huang
 * @version 1.2
 */
public class bills extends JFrame {

    public bills(String userid){
        String companyID = null;
        String userID= null;
        String GasUsge= null;
        String GasCosts= null;
        String ELeUsge= null;
        String EleCosts= null;
        String date= null;


        ArrayList<Bills> bills = new Bills().fileReader();
        JFrame jf = new JFrame("SEMMS-Bills");
        Container c = jf.getContentPane();
        c.setBackground(Color.white);
        jf.setLayout(new BorderLayout());
        final Object[] columnNames = {"Company","ID","Gas Usage","Cost of gas","Electricity Usage","Cost of electricity","Date"};

        String user = userid;
        int index = bills.size();
        double cost1 = 0.0;
        double cost2 = 0.0;
        Object[][] data = new Object[index][7];
        int k = 0;

        for(int i=0; i<bills.size();i++){
            if( bills.get(i).getUserID().contains(user) ){
                companyID = String.valueOf(bills.get(i).getCompanyID());
                userID = String.valueOf(bills.get(i).getUserID());
                GasUsge = String.valueOf(bills.get(i).getMonthlyUsgeOfGas());
                GasCosts = String.valueOf(bills.get(i).getMonthlyCostOfGas());
                ELeUsge = String.valueOf(bills.get(i).getMonthlyUsgeOfEle());
                EleCosts = String.valueOf(bills.get(i).getMonthlyCostOfEle());
                date = String.valueOf(bills.get(i).getDate());
                data[k][0] = companyID;
                data[k][1] = userID;
                data[k][2] = GasUsge;
                data[k][3] = GasCosts;
                data[k][4] = ELeUsge;
                data[k][5] = EleCosts;
                data[k][6] = date;
                k++;
            }
        }
        //get total cost
        for(int j=0; j<index;j++){
            if ( bills.get(j).getUserID().contains(user) ){
                cost1 = bills.get(j).getMonthlyCostOfGas() + cost1;
                cost2 = bills.get(j).getMonthlyCostOfEle() + cost2;
            }
        }

        JLabel hint = new JLabel("total cost: "+ cost1 + "   total usage: "+ cost2);

        JTable friends = new JTable(data, columnNames);
       // friends.getc
        friends.setRowHeight (40);
        friends.setPreferredScrollableViewportSize(new Dimension(700, 100));

        friends.setRowMargin (1);
        friends.setRowSelectionAllowed (true);
        friends.setSelectionBackground (new Color(223, 255, 248));
        friends.setGridColor (Color.black);
        friends.selectAll ();
        friends.clearSelection ();
        friends.setDragEnabled (true);
        friends.setShowGrid (false);
        friends.setShowHorizontalLines (true);
        friends.setShowVerticalLines (true);

        JScrollPane pane = new JScrollPane (friends);
        JButton b2 = new JButton("back");
        JPanel P1 = new JPanel();
        JPanel P2 = new JPanel();
        P2.setLayout(new BorderLayout());

        b2.addActionListener(new ActionListener() {
            /**
             * this method provides the way to back.
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                Object[] options ={ "Yes", "No" };
                //yes = 0 no = 1
                int m = JOptionPane.showOptionDialog(null, "Go Back?", "tips",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (m == 0)
                {
                    jf.dispose();
                    new HomePageOfCustomer(userid);
                }
            }

        });
        P1.setLayout(new FlowLayout());
        P1.add(b2);
        P2.add(pane,BorderLayout.CENTER);
        P2.add(hint,BorderLayout.SOUTH);
        c.add(P2,BorderLayout.CENTER);
        c.add(P1,BorderLayout.SOUTH);

        jf.setVisible(true);
        jf.setBounds(200,100,800,600);
        jf.setMinimumSize(new Dimension(700, 500));
        jf.setLocation(100, 100);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
