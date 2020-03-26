package GUI.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import EntityClasses.*;
import java.lang.String;
import GUI.Company.AddNewCompany;
import GUI.Company.CompanyHomePage;
import GUI.Global;

/**
 *Title: init.java
 * @author Songminghuang Huang
 * @version 1.2
 */

public class init implements Global {
    public init() {

        JFrame jf = new JFrame("SEMMS");
        Container c = jf.getContentPane();
        JButton log = new JButton("log in");
        JButton new_company = new JButton("new company");

        jf.setLayout(null);
        jf.setMinimumSize(new Dimension(700, 500));
        jf.setLocation(100, 100);
        c.setBackground(new Color(240,248,255));
        log.setBounds(100, 187, 125, 45);
        new_company.setBounds(100, 337, 125, 45);
        c.add(log);
        c.add(new_company);

        new_company.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new AddNewCompany();
            }
        });

        log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton jrb1 = new JRadioButton("Customer");
                JRadioButton jrb2 = new JRadioButton("Company");
                ButtonGroup bg = new ButtonGroup();
                JPanel P1 = new JPanel();
                JPanel P2 = new JPanel();
                JPanel P3 = new JPanel();
                JLabel L1 = new JLabel("ID");
                JLabel L2 = new JLabel("Password");
                JButton B1 = new JButton("Sign");
                JButton B2 = new JButton("Back");
                JTextField T1 = new JTextField();
                JPasswordField jp = new JPasswordField();
                P1.setLayout(new BorderLayout());
                P2.setLayout(new GridLayout(3, 2, 15, 15));

                jp.setEchoChar('*');
                bg.add(jrb1);
                bg.add(jrb2);
                P2.add(jrb1);
                P2.add(jrb2);
                P2.add(L1);
                P2.add(T1);
                P2.add(L2);
                P2.add(jp);
                P3.add(B1);
                P3.add(B2);

                B2.addActionListener(new ActionListener() {
                    /**
                     * This method provide the way to back.
                     * @param e
                     */
                    public void actionPerformed(ActionEvent e) {
                        Object[] options = {"Yes", "No"};
                        //yes = 0 no = 1
                        int m = JOptionPane.showOptionDialog(null, "Go Back?", "tips", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (m == 0) {
                            jf.dispose();
                            new init();
                        }
                    }
                });

                jrb1.addActionListener(new ActionListener() {
                    /**
                     * This method provide the select of customer.
                     * @param e
                     */
                    public void actionPerformed(ActionEvent e) {
                        B1.addActionListener(new ActionListener() {
                            /**
                             * This method verifies the correctness of userID and password
                             * @param e
                             */
                            public void actionPerformed(ActionEvent e) {
                                ArrayList<UserInfo> userInfos = new UserInfo().fileReader();
                                  boolean flag = false;
                                  String input1 = String.valueOf(T1.getText());
                                  String input2 = String.valueOf(jp.getPassword());
                                  if( input1!= null ) {
                                       for (int i = 0; i < userInfos.size(); i++) {
                                           if( userInfos.get(i).getUserID().contains(input1)&&userInfos.get(i).getUserID().length()== input1.length() ){
                                              if ( userInfos.get(i).getPassWord().contains(input2) && userInfos.get(i).getPassWord().length()== input2.length()){
                                                  flag = true;
                                                  break;
                                              }
                                          }
                                      }
                                  }
                                  if( flag ){
                                      jf.dispose();
                                      new HomePageOfCustomer(input1);
                                  }
                                  else{
                                      new md();
                                  }


                                }
                            });
                    }
                });

                jrb2.addActionListener(new ActionListener() {
                    /**
                     * This method provide the select of company.
                     * @param e
                     */

                    public void actionPerformed(ActionEvent e) {
                        B1.addActionListener(new ActionListener() {
                            /**
                             * This method verifies the correctness of companyID and password
                             * @param e
                             */
                            public void actionPerformed(ActionEvent e) {
                                boolean flag = false;
                                ArrayList<CompanyInfo> companyInfos= new CompanyInfo().fileReader();
                                String input1 = String.valueOf(T1.getText());
                                String input2 = String.valueOf(jp.getPassword());
                                if( input1!= null ) {
                                    for (int i = 0; i < companyInfos.size(); i++) {
                                        if( companyInfos.get(i).getCompanyID().contains(input1) && companyInfos.get(i).getCompanyID().length()== input1.length() ){
                                            if ( companyInfos.get(i).getPassword().contains(input2) && companyInfos.get(i).getPassword().length()== input2.length()){
                                                flag = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if( flag ){
                                    jf.dispose();
                                    new CompanyHomePage(input1);
                                }
                                else{
                                    new md();
                                }
                             }
                        });
                    }
                });

                P1.add(P2);
                P1.add(P3);
                jrb1.setBackground(new Color(240,248,255));
                jrb2.setBackground(new Color(240,248,255));
                P1.setBackground(new Color(240,248,255));
                P3.setBackground(new Color(240,248,255));
                P2.setBackground(new Color(240,248,255));
                P1.add(P2, BorderLayout.NORTH);
                P1.add(P3, BorderLayout.SOUTH);
                jf.add(P1);

                P1.setBounds(400, 187, 200, 150);
                new_company.setVisible(false);
                P1.updateUI();
            }
        });

        jf.setBounds(200, 100, 800, 600);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Title: md.java
     * @author Songminghuang Huang
     * @version 1.0
     */
    class md extends JFrame{
        public md(){

            Container c = getContentPane();
            this.setLayout(new BorderLayout());
            JLabel L1 = new JLabel("    Wrong!   ");
            c.add(L1,BorderLayout.CENTER);
            this.setVisible(true);
            this.setBounds(550, 287, 125, 150);
            this.setDefaultCloseOperation(2);
        }
    }

}


