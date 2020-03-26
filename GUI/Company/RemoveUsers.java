package GUI.Company;

import EntityClasses.*;
import GUI.Global;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
/**
 * Title: RemoveUsers.java
 * Description: this class is used to remove a customer from the company
 * @author1 Xueying Yang
 * @author2 Jie ji
 * @version 1.0
 */
public class RemoveUsers extends JFrame implements ActionListener, Global {
    private JButton bt1,bt0,bt2;
    private JPanel input,back;
    private Label label;
    private TextArea text;

    int user,row;
    String cID;
    public RemoveUsers(String cID){
        this.cID = cID;

        setTitle("SEMMS-Remove User");
        setBounds(100,100,800,600);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(700, 500));
        setLocation(100, 100);
        label=new Label("please enter your id:");
        text=new TextArea("");
        bt1=new JButton("remove");
        bt0=new JButton("back");
        bt2=new JButton("Company Home");
        bt0.addActionListener(this);
        bt2.addActionListener(this);
        bt1.addActionListener(this);
        back=new JPanel();
        back.setLayout(new FlowLayout());
        back.add(bt0);
        back.add(bt2);
        input=new JPanel();
        input.setLayout(new FlowLayout());
        input.add(label);
        input.add(text);
        input.add(bt1);

        add(input,BorderLayout.NORTH);
        add(back,BorderLayout.CENTER);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
   /**
     * this method is used to linsten to an event
     * @param e action a event
     */
    public void actionPerformed(ActionEvent e) {
        String userID = null;
        if(e.getSource()==bt0){
            dispose();

            CustomerOperation software=new CustomerOperation(cID);

        }
        if(e.getSource()==bt2){
            dispose();
            CompanyHomePage ch=new CompanyHomePage(cID);

        }
        else if(e.getSource()==bt1){
            if(text.getText().length()==0){
                JPanel jPanel = new JPanel();
                JOptionPane.showMessageDialog(jPanel, "can't be empty!", "warnning!", JOptionPane.WARNING_MESSAGE);
            }
            else{
                userID = text.getText();
                int flag = isCustomer(userID);

                if(flag == 1){

                    new UserInfo().Delete(userID);
                    new Bills().Delete(userID);
                    new Electric().Delete(userID);
                    new Gas().Delete(userID);
                }
                else{
                    JPanel jPanel = new JPanel();
                    JOptionPane.showMessageDialog(jPanel, "There is no "+text.getText()+" in the table!", "warnning!",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
   /**
    * this method is used to judge whether the input ID is in the system already
     * @param ID the customer id
     * @return int use to judge if a customer. if the system has the user ID return 1, else 0
     */
    public int isCustomer(String ID){
        int flag=0;

        ArrayList<UserInfo> userInfos = new UserInfo().fileReader();

        for(UserInfo user:userInfos){
            if(user.getUserID().contains(ID)&&user.getCompanyID().contains(cID)&&user.getUserID().length()==ID.length())
                flag = 1;
        }
        return flag;
    }


}
