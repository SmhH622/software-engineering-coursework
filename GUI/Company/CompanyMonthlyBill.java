package GUI.Company;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import EntityClasses.*;
import ControlClasses.*;
import GUI.Global;
/**
 * Title: CompanyMonthlyBill.java
 * Description: this class is a object which check the message of the personal monthly bill.
 * @author Jingyun Wang
 * @version 1.0
 */

public class CompanyMonthlyBill extends JPanel implements ActionListener,Global{
    JFrame frame;
    JTable newtable;
    String[][] datas={} ;
    String cID ;
    DateTransform time=new DateTransform();

    ArrayList<UserInfo> userInfos;
    ArrayList<CompanyInfo> companyInfos;
    ArrayList<Gas> gases;
    ArrayList<Electric> electrics;
    ArrayList<Bills> bills;
    ArrayList<double[]> Ebill;

    String BillTime;
    String mbill1;
    Date monthbill1;
    int newpeople;

    public CompanyMonthlyBill(String ID)
    {
        frame=new JFrame("SEMMS-Monthly Bill");
        this.cID=ID;
        this.build();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocation(100, 100);
        frame.setMinimumSize(new Dimension(700,500));
        frame.setVisible(true);
    }
    /**
     * this method is used to generate the form of the user interface
     */
    public void build()
    {
        this.getInformation();
        newtable=this.getAll();
        frame.getContentPane().add(BorderLayout.CENTER,new JScrollPane(newtable));
        this.buildButton();
        frame.setVisible(true);
    }
    /**
     * this method is used to get the current data from the specific files
     */
    public void getInformation()
    {
        userInfos=new UserInfo().fileReader();
        companyInfos=new CompanyInfo().fileReader();
        gases=new Gas().fileReader();
        electrics=new Electric().fileReader();
        bills=new Bills().fileReader();
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
                newpeople=needmonthbill(i);
                if((newpeople==1)||(newpeople==2))
                {
                    E[1]=0;E[2]=0;
                    for(j=0;j<electrics.size();j++)
                    {
                        if((electrics.get(j).getCompanyID().equals(cID))&&(electrics.get(j).getUserID().equals(userID)))
                        {
                            if(timesuit(i,j,electrics.get(j).getData())==1)
                            {
                                E[2]=E[2]+electrics.get(j).getTotalUsage();
                                E[1]=E[1]+electrics.get(j).getCost(); }
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
        }
    }
    /**
     * this method is used to generate the table of the monthly bill.
     * @return the table of monthly bill, in JTable form
     */
    public JTable getAll()
    {
        System.out.println("getAll");
        int i;
        String[] titles = {"ID","name","E-cost", "E-consume","G-cost","G-consume","arrive-time","select"};
        DefaultTableModel model = new DefaultTableModel(datas, titles);
        JTable table = new JTable(model);
        String[]args=new String[8];
        Object[]object=new Object[8];
        int usernum;
        for(i = 0;i<Ebill.size();i++){

            usernum=(int)(Ebill.get(i)[0]);

            args[0]=userInfos.get(usernum).getUserID();
            args[1]=userInfos.get(usernum).getUserName();
            args[2]=String.valueOf(Ebill.get(i)[1]);
            args[3]=String.valueOf(Ebill.get(i)[2]);
            args[4]=String.valueOf(Ebill.get(i)[3]);
            args[5]=String.valueOf(Ebill.get(i)[4]);
            args[6]=mbill1.substring(0,10);
            object=args;
            object[7]=new String("false");

            model.addRow(object);

            Object a= model.getValueAt(0, 7);
        }

        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(7).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        tcm.getColumn(7).setCellRenderer(new TestTableCellRenderer());
        return table;

    }


    class TestTableCellRenderer extends JCheckBox implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row,int column)
        {
            String B;
            Boolean b;
            if(value!=null)
            {
                B=String.valueOf(value);
                if(B.equals("false"))
                {b=false;}
                else
                {b=true;}
                this.setSelected(b.booleanValue());
            }
            return this;
        }
    }
    /**
     * this method is used to determine whether the consumer need to generate the monthly bill
     * @param usernum
     * @return the determination about monthly bill generating, in int form
     */
    public int needmonthbill(int usernum)
    {
        int t=0;
        int send=0;
        int i;
        int kindofBill=0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try
        {
            String CurrenTime=time.getCurrenTime();
            String registerDate=userInfos.get(usernum).getRegisterDate();
            Date current= df.parse(time.getDate(CurrenTime)+" "+time.getTime(CurrenTime));
            Date regist= df.parse(time.getDate(registerDate)+" "+time.getTime(registerDate));
            Date bill;

            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            Date m = c.getTime();
            String mdelay = df.format(m);
            Date monthdelay=df.parse(mdelay);

            long RCdiff = current.getTime() - regist.getTime();
            long days = (RCdiff / (1000 * 60 * 60 * 24));

            String mbill;
            Date monthbill;
            if(days>=30)//>=30day current-monthbill?-monthdelay-regist
            {
                kindofBill=1;
                monthdelay=df.parse(mdelay);
            }
            else//<30day
            {
                if(time.getDay(BillTime)>=time.getDay(CurrenTime))
                {
                    mbill=mdelay.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                }
                else
                {
                    mbill=CurrenTime.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                }
                monthbill=df.parse(mbill);

                if(regist.getTime()<monthbill.getTime())//current-bill-monthbill-regist
                {
                    kindofBill=2;
                    monthdelay=monthbill;
                }
            }

            for(i=0;i<bills.size();i++)
            {
                String userID=userInfos.get(usernum).getUserID();

                if((bills.get(i).getCompanyID().equals(cID))&&(bills.get(i).getUserID().equals(userID)))
                {
                    String Mbill=bills.get(i).getDate();
                    bill= df.parse(time.getDate(Mbill)+" "+time.getTime(Mbill));
                    if(bill.getTime()>=monthdelay.getTime())
                    {
                        send=1;//bill have send
                    }
                }
            }
            if(send!=1)
            {
                t=kindofBill;//need send bill!
            }
        }
        catch(Exception e)
        {  }
        return t;
    }
    /**
     * this method is used to judge whether the daily bill should be count
     * @param usernum  user ID
     * @param  billnum bill number
     * @param billDate the date to generate the bill
     * @return whether the daily bill should be count, in int form
     */
    public int timesuit(int usernum,int billnum,String billDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        int t=0;
        try
        {
            String CurrenTime=time.getCurrenTime();

            Date bill=df.parse(time.getDate(billDate)+" "+time.getTime(billDate));

            Date monthbill2;


            String mbill2="";

            String mdelay2;
            String mdelay1;
            c1.setTime(new Date());
            c1.add(Calendar.MONTH, -1);
            Date m1 = c1.getTime();
            mdelay1 = df.format(m1);

            c2.setTime(new Date());
            c2.add(Calendar.MONTH, -2);
            Date m2 = c2.getTime();
            mdelay2 = df.format(m2);

            if(newpeople==1)
            {
                if(time.getDay(BillTime)>=time.getDay(CurrenTime))
                {
                    mbill1=mdelay1.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                    mbill2=mdelay2.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";

                }
                else
                {
                    mbill1=CurrenTime.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                    mbill2=mdelay1.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                }
            }
            else if(newpeople==2)
            {
                if(time.getDay(BillTime)>=time.getDay(CurrenTime))
                {
                    mbill1=mdelay1.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                }
                else
                {
                    mbill1=CurrenTime.substring(0, 8)+BillTime.substring(8,10)+" 00:00:00";
                }
                String registerDate=userInfos.get(usernum).getRegisterDate();
                mbill2=time.getDate(registerDate)+" "+time.getTime(registerDate);
            }

            monthbill1=df.parse(mbill1);
            monthbill2=df.parse(mbill2);

            if(bill.getTime()>=monthbill2.getTime()&&bill.getTime()<monthbill1.getTime())
            {
                t=1;
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
        JPanel mix1=new JPanel();
        JPanel mix2=new JPanel();
        JPanel mix=new JPanel();
        mix1.setLayout(new GridLayout(1,3));
        mix2.setLayout(new GridLayout(1,2));
        mix.setLayout(new GridLayout(2,1));

        JButton back= new JButton("back");
        back.addActionListener(this);
        mix2.add(back);

        JButton home= new JButton("home");
        home.addActionListener(this);
        mix2.add(home);

        JButton selectAll= new JButton("selectAll");
        selectAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for(int i=0;i<Ebill.size();i++)
                {
                    Object o=new String("true");
                    newtable.setValueAt(o, i,7);
                }
            }});
        mix1.add(selectAll);

        JButton cancelAll= new JButton("cancelAll");
        cancelAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for(int i=0;i<Ebill.size();i++)
                {
                    Object o=new String("false");
                    newtable.setValueAt(o, i,7);
                }
            }});
        mix1.add(cancelAll);

        JButton send= new JButton("send");
        send.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String senduser="";
                int send=0;


                for(int i=0;i<Ebill.size();i++)
                {
                    String B=String.valueOf(newtable.getValueAt(i, 7));
                    if(B.equals("true"))
                    {
                        Bills writebill=new Bills();
                        System.out.println(newtable.getValueAt(i, 0));
                        System.out.println(i);
                        writebill.setCompanyID(cID);

                        writebill.setUserID(String.valueOf(newtable.getValueAt(i, 0)));
                        writebill.setMonthlyUsgeOfEle((Double.parseDouble(String.valueOf(newtable.getValueAt(i, 3)))));
                        writebill.setMonthlyCostOfEle((Double.parseDouble(String.valueOf(newtable.getValueAt(i, 2)))));
                        writebill.setMonthlyUsgeOfGas((Double.parseDouble(String.valueOf(newtable.getValueAt(i, 5)))));
                        writebill.setMonthlyCostOfGas((Double.parseDouble(String.valueOf(newtable.getValueAt(i, 4)))));
                        writebill.setMonthlyCostOfGas((Double.parseDouble(String.valueOf(newtable.getValueAt(i, 4)))));

                        String time= mbill1.substring(0, 10)+"/"+mbill1.substring(11, 19)+"/"+getWeekOfDate(monthbill1);
                        writebill.setDate(time);

                        senduser=senduser+String.valueOf(newtable.getValueAt(i, 0))+" | ";
                        send=1;
                        bills.add(writebill);
                    }
                }
                if(send==1)
                {
                    new Bills().WriteFiles(bills);
                    frame.dispose();
                    new CompanyMonthlyBill(cID);

                    JOptionPane.showMessageDialog(null, "send e-mail to | " +senduser);

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "no select! please try again!" );
                }
            }});
        send.addActionListener(this);
        mix1.add(send);

        mix.add(mix1);
        mix.add(mix2);
        frame.getContentPane().add(BorderLayout.NORTH,mix1);
        frame.getContentPane().add(BorderLayout.SOUTH,mix2);
    }
    /**
     * this method is used to generate the corresponding week of the date according to the date which we want to transform
     * @param dt date
     * @return current weekday, in String form
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * this method is used to monitor the action of user and generate the corresponding response. 
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand()=="home")
        {
            frame.dispose();
            new CompanyHomePage(cID);


        }
        else if(e.getActionCommand()=="back")
        {
            frame.dispose();
            new CompanyPerson(cID);


        }
        else if(e.getActionCommand()=="send")
        {

        }
    }

}
