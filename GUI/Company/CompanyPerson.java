package GUI.Company;

import EntityClasses.*;
import ControlClasses.*;
import GUI.Global;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Title: CompanyPerson .java
 * Description: this class is a object which generate the current bill of the consumer.
 * @author Jingyun Wang
 * @version 1.0
 */
public class CompanyPerson implements ActionListener, Global{
    JFrame frame;
    String[][] datas={} ;
    String cID ;

    DateTransform time=new DateTransform();
    Date day = new Date();
    ArrayList<UserInfo> userInfos;
    ArrayList<CompanyInfo> companyInfos;
    ArrayList<Gas> gases;
    ArrayList<Electric> electrics;
    ArrayList<double[]> Ebill;
    String BillTime;

    public CompanyPerson (String ID)
    {
        frame=new JFrame("SEMMS-Generate  Current Bill");
        this.cID=ID;
        this.build();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocation(100, 100);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setVisible(true);
    }
    /**
     * this method is used to generate the form of the user interface
     */
    public void build()
    {
        System.out.print("[cID]"+cID+"\n");
        this.getInformation();
        this.getAll();
        this.buildButton();
    }
    /**
     * this method is used to get the current data from the specific files
     */
    public void getInformation()
    {
        try {
            userInfos=new UserInfo().fileReader();
            companyInfos=new CompanyInfo().fileReader();
            gases=new Gas().fileReader();
            electrics=new Electric().fileReader();

            Ebill =new ArrayList<double[]>();
            int i,j;
            for(i = 0;i<companyInfos.size();i++){
                if(companyInfos.get(i).getCompanyID().equals(cID))
                {
                    BillTime=companyInfos.get(i).getTimeforbill();
                }
            }
            for( i = 0;i<userInfos.size();i++)
            {
                if(userInfos.get(i).getCompanyID().equals(cID))
                {
                    double[] E=new double[5];
                    String userID=userInfos.get(i).getUserID();
                    E[1]=0;E[2]=0;
                    for(j=0;j<electrics.size();j++)
                    {

                        if((electrics.get(j).getCompanyID().equals(cID))&&(electrics.get(j).getUserID().equals(userID)))
                        {

                            if(timesuit(i,j,electrics.get(j).getData())==1)
                            {
                                E[2]=E[2]+electrics.get(j).getTotalUsage();
                                E[1]=E[1]+electrics.get(j).getCost();
                            }
                        }
                    }
                    E[0]=i;


                    E[3]=0;E[4]=0;
                    for(j=0;j<gases.size();j++)
                    {
                        if(gases.get(j).getCompanyID().equals(cID)&&gases.get(j).getUserID().equals(userID))
                        {
                            if(timesuit(i,j,gases.get(j).getData())==1)
                            {
                                E[3]=E[3]+gases.get(j).getCost();
                                E[4]=E[4]+gases.get(j).getTotalUsage();
                            }
                        }
                    }
                    Ebill.add(E);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * this method is used to generate the current data in specific form
     * @return current time, in String form
     */
    public void getAll()
    {
        int i;
        String[] titles = {"ID","name","E-cost", "E-consume","G-cost","G-consume"};
        DefaultTableModel model = new DefaultTableModel(datas, titles);
        JTable table = new JTable(model);
        String[]args=new String[6];
        int usernum;

        for(i = 0;i<Ebill.size();i++){

            usernum=(int)(Ebill.get(i)[0]);

            args[0]=userInfos.get(usernum).getUserID();
            args[1]=userInfos.get(usernum).getUserName();
            args[2]=String.valueOf(Ebill.get(i)[1]);
            args[3]=String.valueOf(Ebill.get(i)[2]);
            args[4]=String.valueOf(Ebill.get(i)[3]);
            args[5]=String.valueOf(Ebill.get(i)[4]);

            model.addRow(args);
        }

        frame.getContentPane().add(BorderLayout.CENTER,new JScrollPane(table));

    }
    /**
     * this method is used to judge whether the daily bill should be counted
     * @param usernum , billnum , billDate
     * @return whether the daily bill should be count, in int form
     */
    public int timesuit(int usernum,int billnum,String billDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        int t=0;
        try
        {
            String CurrenTime=time.getCurrenTime();
            String registerDate=userInfos.get(usernum).getRegisterDate();
            String registD=time.getDate(CurrenTime)+" "+time.getTime(CurrenTime);
            Date current= df.parse(registD);
            Date regist= df.parse(time.getDate(registerDate)+" "+time.getTime(registerDate));
            Date bill=df.parse(time.getDate(billDate)+" "+time.getTime(billDate));
            Date monthbill;

            String mbill;
            if(time.getDay(BillTime)>=time.getDay(CurrenTime))
            {
                c.setTime(new Date());
                c.add(Calendar.MONTH, -1);
                Date m = c.getTime();
                String mdelay = df.format(m);

                mbill=mdelay.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                monthbill=df.parse(mbill);
            }
            else
            {
                mbill=CurrenTime.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                monthbill=df.parse(mbill);
            }


            long RCdiff = current.getTime() - regist.getTime();
            long days = (RCdiff / (1000 * 60 * 60 * 24));

            if(days<30)
            {
                if(bill.getTime()>=regist.getTime())
                {
                    if(regist.getTime()>=monthbill.getTime())
                    {
                        t=1;//bill-regist-monthbill
                    }
                    else
                    {
                        if(bill.getTime()>=monthbill.getTime())
                        {
                            t=1;//bill-monthbill-regist
                        }
                    }

                }

            }
            else
            {

                if(bill.getTime()>=monthbill.getTime())
                {
                    t=1;
                }
            }
        }
        catch(Exception e) {
        }
        return t;
    }
    /**
     * this method is used to generate the button which should be shown on the user interface
     */
    public void buildButton()
    {
        JPanel mix=new JPanel();
        mix.setLayout(new GridLayout(1,3));

        JButton back= new JButton("back");
        back.addActionListener(this);
        mix.add(back);

        JButton bill= new JButton("bill");
        bill.addActionListener(this);
        mix.add(bill);

        frame.getContentPane().add(BorderLayout.SOUTH,mix);
    }
    /**
     * this method is used to monitor the action of user and generate the corresponding response. 
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand()=="back")
        {
            frame.dispose();
            new CompanyHomePage(cID);
            System.out.print("back");

        }
        else if(e.getActionCommand()=="bill")
        {
            new CompanyMonthlyBill(cID);
            System.out.print("bill");
            frame.dispose();
        }
    }

}
