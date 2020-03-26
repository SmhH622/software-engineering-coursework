package GUI.Company;

import EntityClasses.CompanyInfo;

import GUI.Customer.init;
import GUI.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: AddNewCompany.java
 * <p>Description: This class creat a frame which used to created a new company.
 * @author Jie Ji,
 * @version 1.0
 * @author Songminghuang Huang
 * @version 1.1
 */

public class AddNewCompany implements Global{
    Container con;
    JFrame frame;
    static String NAME[] = {"CompanyID","CompanyName","Password","Gas Unit rate (p/kWh)","Electricity Unit rate (p/kWh)","Time to send bill"};
    ArrayList<JTextField> texts = new ArrayList<JTextField>();
    ArrayList<CompanyInfo> companyInfo = new ArrayList<>();

    ArrayList<CompanyInfo>  infos = new ArrayList<>();
    int CHACK[]={0,0,0,0,0,0};

    public AddNewCompany(){

        infos = new CompanyInfo().fileReader();

        frame = new JFrame("SEMMS-Add New Company");
        JPanel panel1 = new JPanel();
        Container con = frame.getContentPane();

        Box box = Box.createVerticalBox();

        JButton exit = backButton();
        JButton save = saveButton();

        JLabel label = new JLabel("Welcome to use our software!(=^ - ^=)",JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 25));

        Font textFont = new Font("Serif", Font.PLAIN, 15);

        int i=0;
        for(i=0;i<NAME.length;i++) {
            JPanel panel = new JPanel();

            JLabel nameLabel = new JLabel(NAME[i]);
            nameLabel.setFont(textFont);

            JButton check = checkButton(NAME[i]);
            JTextField jf =  new JTextField();
            texts.add(i,jf );
            texts.get(i).setFont(textFont);
            if(i==3||i==4){
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
            panel.setLayout(new BorderLayout());
            panel.add(texts.get(i),BorderLayout.CENTER);
            panel.add(check,BorderLayout.EAST);
            box.add(nameLabel,Box.createVerticalStrut(4));
            box.add(panel,Box.createVerticalStrut(4));
        }


        panel1.setLayout(new FlowLayout());

        frame.setLayout(new BorderLayout());

        panel1.add(exit);
        panel1.add(save);
        con.add(label,BorderLayout.NORTH);
        con.add(box,BorderLayout.CENTER);
        con.add(panel1,BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setSize(800,600);
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(700, 500));
    }

    JButton checkButton(String name){
        JButton button = new JButton("Check");
        Font buttonsFront = new Font("Serif", Font.PLAIN, 17);

        button.setFont(buttonsFront);
        button.setPreferredSize(new Dimension(280,30));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (name){
                    case "CompanyID": {
                        String first = texts.get(0).getText();

                        int k = 0;
                        boolean flag = true;
                        if(first.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE);

                            flag = false;
                        }
                        else{
                            while (k < infos.size()) {
                                if (infos.get(k).getCompanyID().contains(first)&&infos.get(k).getCompanyID().length() == first.length()) {
                                    JOptionPane.showMessageDialog(null, "The company ID is already in the list.Try another! ", "Warning",JOptionPane.WARNING_MESSAGE);
                                    flag = false;
                                    break;
                                } else {
                                    k++;
                                }
                            }
                        }

                        if(flag){
                            JOptionPane.showMessageDialog(null, "The value of Company ID is available! ");

                            CHACK[0] = 1;
                        }

                    }
                    break;
                    case "CompanyName":{
                        String second = texts.get(1).getText();

                        int k=0;
                        boolean flag = true;
                        if(second.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE);

                            flag = false;
                        }
                        else{
                            while(k<infos.size()){
                                if(second.equals(infos.get(k).getCompanyName())){
                                    JOptionPane.showMessageDialog(null, "The company name is already in the list.Try another! ", "Warning",JOptionPane.WARNING_MESSAGE);

                                    flag = false;
                                    break;
                                }
                                else{
                                    k++;
                                }
                            }
                        }

                        if(flag){
                            JOptionPane.showMessageDialog(null, "The value of Company name is available! ");

                            CHACK[1] = 1;
                        }
                    }
                    break;
                    case "Password":{
                        String third = texts.get(2).getText();

                        if(third.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE);

                        }
                        else{

                            if(third.length()<6){
                                JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE);

                                break;
                          }
                            else{
                                JOptionPane.showMessageDialog(null, "The value of Gas Unit rate is available! ");
                                CHACK[2] = 1;
                            }
                        }
                    }
                    break;
                    case "Gas Unit rate (p/kWh)":
                        String forth = texts.get(3).getText();

                        if(forth.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE);

                        }
                        else{
                            JOptionPane.showMessageDialog(null, "The value of Gas Unit rate is available! ");
                            CHACK[3] =1;
                        }
                        break;

                    case "Electricity Unit rate (p/kWh)":
                        String fivth = texts.get(4).getText();

                        if(fivth.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE);

                        }
                        else{
                            JOptionPane.showMessageDialog(null, "The value of Electricity Unit rate is available! ");
                            CHACK[4] =1;
                        }

                        break;
                    case "Time to send bill":
                        String sixth = texts.get(5).getText();
                        int i = 0;
                        if(!("".equals(sixth.trim()))&&sixth.trim().length()>=10){

                            String date = sixth.trim();
                            date = date.substring(0,10);
                            String el= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
                            Pattern p = Pattern.compile(el);
                            Matcher m = p.matcher(date);
                            boolean b = m.matches();
                            String time =sixth.trim();
                            //StringUtils.substringBefore(time, "-");
                            time = time.substring(11);
                            String el1= "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
                            Pattern p1 = Pattern.compile(el1);
                            Matcher m1 = p1.matcher(time);
                            boolean b1 = m1.matches();
                            if((!b)||(!b1)){
                                JOptionPane.showMessageDialog(null, "The format is wrong","Warning",JOptionPane.WARNING_MESSAGE);
                                i=-1;
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE);
                            i=-1;
                        }
                        if(i>=0){
                            CHACK[5] =1;
                            JOptionPane.showMessageDialog(null, "The new time for bill is available! ");
                        }

                        break;
                }

            }
        });



        return button;
    }
    /**
     * generate the exist button.
     * @return JButton button :the exit button
     */

    JButton backButton() {
        JButton button = new JButton("Back");
        Font buttonsFront = new Font("Serif", Font.PLAIN, 17);

        button.setFont(buttonsFront);
        button.setPreferredSize(new Dimension(80,30));

        button.setToolTipText("return formal page");

        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // Get text and check input.
                Object[] options ={ "Yes", "No" };
                //yes = 0 no = 1
                int m = JOptionPane.showOptionDialog(null, "Go Back?", "tips",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (m == 0)
                {
                    frame.dispose();
                    new init();
                }
            }
        });
        return button;
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
                int flag = 0;
                for(int i =0;i<CHACK.length;i++){
                    if(CHACK[i] == 0)
                        flag = 1;
                }
                if(flag == 0){
                    // TODO Auto-generated method stub
                    Object[] options ={ "Yes", "No" };
                    //yes = 0 no = 1
                    int m = JOptionPane.showOptionDialog(null, "Save?", "tips",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    System.out.println(m);
                    if (m==0) {
                        getCompanyInfoFromText();   //write in
                        //go back to Init Page
                        frame.dispose();
                        new init();

                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Place make sure every input is already checked! ", "Warning",JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        return button;
    }

    /**
     * This method write the new company information into file.
     */
    void getCompanyInfoFromText() {

        String first = texts.get(0).getText();
        String cID = first;
        String second = texts.get(1).getText();
        String cNAME = second;
        String third = texts.get(2).getText();
        String password = third;
        String forth = texts.get(3).getText();
        String GUR = forth;
        double d = Double.parseDouble(GUR);
        String fifth = texts.get(4).getText();
        String EUR = fifth;
        double dd = Double.parseDouble(EUR);
        String sixth = texts.get(5).getText();
        String TSB = sixth ;
        String TT = "2018-04-/08:04:26";
        StringBuffer sb = new StringBuffer();
        sb.append(TT).insert(8,TSB);
        String ts = sb.toString();

        companyInfo.add(new CompanyInfo(cID,cNAME,password,d,dd,ts));

        new CompanyInfo().WriteFiles_append(companyInfo);
    }
}
