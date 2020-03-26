package GUI.Customer;
import EntityClasses.*;
import GUI.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;

/**
 * Title: HomePageOfCustomer.java
 * Description: this class is used to create the home page of customer.
 *               It also contain the method to change user info. and warning the overcome of the user's budgets.
 * @author Jie Ji
 * @version 1.2
 */
public class HomePageOfCustomer implements Global {
    String userID;

    // used to judge which need to change
    private static int toFormerPage = 1;
    private static int changeBox = 0;

    static String NAME[] = {"UserID","UserName","Password","Electricity budget(p)","Gas budget(p)","Gas Unit rate(p/kWh)","Electricity Unit rate (p/kWh)","Register date","Company ID","Email"};

    CompanyInfo companyInfo = new CompanyInfo();
    ArrayList<Gas> gas = new ArrayList<Gas>();
    ArrayList<Electric> ele = new ArrayList<Electric>();
    UserInfo userInfo = new UserInfo();

    Container con;
    JFrame frame;
    ArrayList<String> user = new ArrayList<String>();
    ArrayList<JTextField> texts = new ArrayList<JTextField>();
    JPanel panel4;

    /**
     * Constructor of Class.
     * <p>This constructor including
     */
    public HomePageOfCustomer(String userID) {
        this.userID = userID;
        setUserInfo();
        homePage();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * this method is used to generate the home page of customer
     */
    void homePage() {

        frame = new JFrame("SEMMS-consumer_home");
        con = frame.getContentPane();
        frame.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel4 = new JPanel();
        Box vBox = userInfoBox();

        Panel panel5 = new Panel();

        JLabel label1 = warningLabel();

        JButton button1 = generateButtons("Gas");
        JButton button2 = generateButtons("Electricity");
        JButton button3 = generateButtons("Bills");

        JButton exist = backButton(toFormerPage);

        panel2.add(label1);
        panel4.setPreferredSize(new Dimension(250,300));

        panel4.setLayout(new BorderLayout());
        panel4.add(vBox,BorderLayout.CENTER);
        panel4.add(userLabel(),BorderLayout.NORTH);

        panel1.setLayout(new BorderLayout());
        panel1.add(panel2, BorderLayout.NORTH);
        panel1.add(panel3, BorderLayout.CENTER);
        panel1.add(panel5, BorderLayout.SOUTH);

        panel5.setLayout(new FlowLayout(FlowLayout.RIGHT,100,10));
        panel5.add(exist );

        panel3.setLayout(new FlowLayout(FlowLayout.LEFT,65,75));
        panel3.add(button1);
        panel3.add(button2);
        panel3.add(button3);

        con.add(panel4, BorderLayout.WEST);
        con.add(panel1,BorderLayout.CENTER);
        frame.setLocation(100, 100);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setVisible(true);
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * get user info from .text file then put it in to the User ArrayList
     */
    void setUserInfo(){

        userInfo = new UserInfo().getInfo(userID);
        gas = new UserInfo().getGasInfoList(userID);
        ele =new UserInfo().getElecInfoList(userID);
        companyInfo = new CompanyInfo().getInfo(gas.get(0).getCompanyID());

        user.add(0,userInfo.getUserID());
        user.add(1,userInfo.getUserName());
        user.add(2,userInfo.getPassWord());
        user.add(3,String.valueOf(userInfo.getEleBudget()));
        user.add(4,String.valueOf(userInfo.getGasBudget() ) );
        user.add(5,String.valueOf(companyInfo.getCostOfGas() ));
        user.add(6,String.valueOf(companyInfo.getCostOfEle() ));
        user.add(7,userInfo.getRegisterDate());
        user.add(8,userInfo.getCompanyID());
        user.add(9,userInfo.getEmail());

    }

    /**
     *get user info from textArea
     */
    void getUserInfoFromText() {
        ArrayList<UserInfo> uInfo = new ArrayList<>();
        int i;
        for(i=0;i<4;i++) {
            String str= texts.get(i).getText();
            user.set(i+1,str);
        }
        user.set(9,texts.get(i).getText());
        uInfo = new UserInfo().fileReader();
        i=0;
        for(UserInfo uf:uInfo){
            if(uf.getUserID().contains( user.get(0) ) ){
                uInfo.get(i).setUserName(user.get(1));
                uInfo.get(i).setPassWord(user.get(2));
                uInfo.get(i).setEleBudget(Double.valueOf( user.get(3) ) );
                uInfo.get(i).setGasBudget(Double.valueOf( user.get(4) ) );
                uInfo.get(i).setEmail(user.get(9));
            }
            i++;
        }

        new UserInfo().WriteFiles(uInfo);
    }

    /**
     * this method is used to generate the warning lable
     * @return label which used to notice user their budget is overcome
     */
    JLabel warningLabel() {
        String str = "";
        int situation = judgeBudget();

        JLabel label = new JLabel();
        label.setSize(100, 0);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        if (situation == 0){
            str = "<html>Your gas budget is:"+String.valueOf(userInfo.getGasBudget())+"<br>Your electricity budget is:"+String.valueOf(userInfo.getEleBudget())+"</html>";
            label.setText(str);
            label.setForeground(new Color(0,164,164));
        }
        else if (situation == 1) {
            str="Warning! Your already overcommitted in gas!";

            label.setText(str);
            label.setForeground(new Color(238,64,0));
        }
        else if(situation == 2){
            str="Warning! Your already overcommitted in electricity!";
            label.setText(str);
            label.setForeground(new Color(238,64,0));
        }
        else if(situation == 3){
            str="<html>Warning! Your already overcommitted <br>in both gas and electricity!</html>";

            label.setText(str);
            label.setForeground(new Color(238,0,0));
        }
        else {
            str="Oops~ something wrong!";
            label.setText(str);
            label.setForeground(new Color(209,95,238));
        }

        return label;
    }

    /**
     * this method is used to judge whether the user overcome the budget
     * @return if not overcome return 0, if overcommitted in gas only return 1,
     *          if overcommitted in electricity only return 2, if overcommitted in both gas and electricity return3
     */
    int judgeBudget() {
        CountMonthlyCost cmc = new CountMonthlyCost(userID);
        int num=0;
        double eleCost = cmc.costEle();
        double gasCost = cmc.costGas();

        if(userInfo.getGasBudget()>gasCost&&userInfo.getEleBudget()<eleCost)
            num = 2;
        else if(userInfo.getGasBudget() <gasCost&&userInfo.getEleBudget()>eleCost)
            num = 1;
        else if(userInfo.getGasBudget() <gasCost&&userInfo.getEleBudget()<eleCost)
            num = 3;
        return num;
    }
    /**
     * return the label which used in the home page
     * @return If the check pass
     */
    JLabel userLabel() {

        JLabel label2 = new JLabel("Hi! "+user.get(1));

        label2.setFont(new Font("Serif", Font.PLAIN, 25));

        return label2;
    }
    /**
     * generate the box which used to lay out user info
     * @return If the check pass
     */
    Box userInfoBox() {

        Box box1 = Box.createVerticalBox();
        JLabel label = new JLabel();
        JButton button = new JButton("Change Info");

        int i;
        ArrayList<JLabel> labels = new ArrayList<JLabel>();

        Font buttonsFront = new Font("Serif", Font.PLAIN, 15);
        button.setFont(buttonsFront);
        button.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e) {
                // Get text and check input.
                panel4.removeAll();
                Box vBox = changeUserInfoBox();
                panel4.add(vBox);
                panel4.setVisible(false);
                panel4.setVisible(true);
            }
        });

        for(i=0;i<user.size();i++) {
            if(NAME[i]=="Password")
                labels.add(new JLabel(NAME[i]+": ********"));
            else
                labels.add(new JLabel(NAME[i]+":"+ user.get(i)));
            labels.get(i).setFont(new Font("Serif", Font.PLAIN, 15));

            box1.add(labels.get(i),Box.createVerticalStrut(2));
        }
        box1.add(label,Box.createVerticalStrut(2));
        box1.add(button,Box.createVerticalStrut(4));
        return box1;
    }
    /**
     * generate the box which used to change user info
     * @return Box box1: the box used to change user info
     * */
    Box changeUserInfoBox() {

        Box box1 = Box.createVerticalBox();
        Font textFont = new Font("Serif", Font.PLAIN, 15);

        Font tipFont = new Font("Serif", Font.PLAIN, 10);

        int i=0;
        JLabel label1 = new JLabel(NAME[0]+":"+user.get(0));
        label1.setFont(textFont);
        box1.add(label1,Box.createVerticalStrut(4));
        for(i=1;i<user.size()-5;i++) {
            JLabel nameLabel = new JLabel(NAME[i]);
            JTextField jf = new JTextField(user.get(i));
            if(i==3||i==2){
               jf.addKeyListener(new KeyAdapter(){
                   public void keyTyped(KeyEvent e) {
                       int keyChar = e.getKeyChar();
                       if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == KeyEvent.VK_PERIOD )){

                       }else{
                           e.consume();
                       }
                   }
               });
            }
            nameLabel.setFont(textFont);
            texts.add(i-1,jf);
            texts.get(i-1).setFont(textFont);

            box1.add(nameLabel,Box.createVerticalStrut(4));
            box1.add(texts.get(i-1),Box.createVerticalStrut(4));
        }
            JLabel nameLabel = new JLabel(NAME[9]);
            JTextField jf = new JTextField(user.get(9));
            nameLabel.setFont(textFont);
            texts.add(i-1,jf);
            texts.get(i-1).setFont(textFont);

            box1.add(nameLabel,Box.createVerticalStrut(4));
            box1.add(texts.get(i-1),Box.createVerticalStrut(4));
            JPanel panel = new JPanel();

            JLabel tipLabel = new JLabel("<html>" +
                    "Sorry! You don't have right to change infomation of:<br>" +
                    "Gas Unit rate, Electricity Unit rate Register date <br>"+
                    "and Company ID<br>"+
                    "</html>");

            tipLabel.setFont(tipFont);
            panel.add(tipLabel);
            box1.add(panel,Box.createVerticalStrut(4));

        JButton exit = backButton(changeBox);

        JButton save = saveButton();
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(0,5,5));
        p.add(exit);
        p.add(save);

        box1.add(p);

        return box1;
    }

    /**
     * generate the exist button.
     * @param  flag: is used to jugde which page is need to refresh
     * @return JButton button :the exit button
     */

    JButton backButton(int flag) {
        JButton button = new JButton("Back");
        Font buttonsFront = new Font("Serif", Font.PLAIN, 17);

        button.setFont(buttonsFront);
        button.setPreferredSize(new Dimension(80,30));

        button.setToolTipText("return formal page");

        button.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e) {
                // Get text and check input.
                Object[] options ={ "Yes", "No" };
                //yes = 0 no = 1
                int m = JOptionPane.showOptionDialog(null, "Go Back?", "tips",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (m == 0)
                {
                    if(flag==toFormerPage) {
                        frame.dispose();
                        new init();
                    }
                    else if (flag == changeBox) {
                        panel4.removeAll();
                        Box vBox = userInfoBox();

                        panel4.setLayout(new BorderLayout());
                        panel4.add(vBox,BorderLayout.CENTER);
                        panel4.add(userLabel(),BorderLayout.NORTH);

                        panel4.setVisible(false);
                        panel4.setVisible(true);
                    }
                }
            }
        });
        return button;
    }

    /**
     * this method is used to judge the input string is legal email form.
     * @param string
     * @return
     */
    public static int isEmail(String string) {
        if (string == null)
            return 0;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return 1;
        else
            return 0;
    }
    /**
     * generate the save button.
     * @return JButton button :the save button
     */
    JButton saveButton() {
        JButton button = new JButton("Save");
        Font buttonsFront = new Font("Serif", Font.PLAIN, 17);
        button.setFont(buttonsFront);
        button.setPreferredSize(new Dimension(80,30));

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String email = texts.get(4).getText();
                int flag = isEmail(email);

                if(flag ==1){
                    // TODO Auto-generated method stub
                    Object[] options ={ "Yes", "No" };
                    //yes = 0 no = 1
                    int m = JOptionPane.showOptionDialog(null, "Save?", "tips",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (m==0) {
                        //get
                        getUserInfoFromText();
                        //go back
                        frame.dispose();
                        new HomePageOfCustomer(userID);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "The format of the email is illegal! ", "Warning",JOptionPane.WARNING_MESSAGE);
                }


            }
        });
        return button;
    }
    /**
     * generate the button which used to connection to other frame or container
     * @param  name: the name of button.
     * @return button :the button which been generated
     */
    JButton generateButtons(String name) {

        int bwidth,bheight;
        bheight=80;
        bwidth=150;

        JButton button = new JButton(name);
        Font buttonsFront = new Font("Serif", Font.PLAIN, 28);

        button.setFont(buttonsFront);
        button.setPreferredSize(new Dimension(bwidth,bheight));
        button.setToolTipText("Get the details~");

        button.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e) {
                // Get text and check input.
                frame.dispose();

                if(e.getActionCommand() == "Gas")
                    new GasUsage(userID);

                else if	(e.getActionCommand() =="Electricity")
                    new ElectricityUsage(userID);

                else if (e.getActionCommand() == "Bills")
                	new bills(userID);

            }
        });
        return button;
    }
}
