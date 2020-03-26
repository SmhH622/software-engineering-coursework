package GUI.Customer;

import EntityClasses.*;
import ControlClasses.*;
import GUI.Global;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
/**
 * Title: HistoricalGasUsage.java
 * Description: this class is a GUI which shows historical usage in a table. 
 * @author Yihan Lan
 * @version 1.0
 */

public class HistoricalGasUsage extends HistoricalUsage implements UsageHasAction, Global{
	static String[] Names = { "Electricity Meter ID", "Company ID", "User ID", "Cost Of Electricity", "Total Usage","Start Date","End Date" };

	public HistoricalGasUsage(String id) {
			super(id);
			frame.setTitle("SEMMS-HISTORICAL GAS USAGE");
			JLabel label1=new JLabel("HISTORICAL GAS USAGE");
			label1.setFont(new Font("Serif", Font.PLAIN, 25));

			jrb1.addActionListener(new JRBListener());

		    jrb2.addActionListener(new JRBListener());

		    jrb3.addActionListener(new JRBListener());
			JButton  button1=new JButton("Back");
			JButton  button2=new JButton("Back Home");

		    button1.addActionListener(new ButtonListener());

		    button2.addActionListener(new ButtonListener());

		    panel1_1.add(label1);

		    panel3.add(button1);
		    panel3.add(button2);

		}
	 /**
     * A inner class used to generate a usage table in daily, weekly and monthly.
     */
		public class JRBListener implements ActionListener {
			public void actionPerformed(ActionEvent event){
				String jrbName = event.getActionCommand();
				ArrayList<Gas> g = new UserInfo().getGasInfoList(id);
				String[][] obj ;
				if (jrbName.equals("DAILY")) {
					DateTransform dt=new DateTransform();
					String[] Names = { "Gas Meter ID", "Company ID", "User ID", "Cost Of Gas", "Total Usage","Date" };
					obj =new String[1000][6];
					for(int i=0,j=0;i<g.size();i++) {
						String a=g.get(i).getMeterID();
						String b=g.get(i).getCompanyID();
						String c=g.get(i).getUserID();
						String d=String.valueOf(g.get(i).getCost());
						String e=String.valueOf(g.get(i).getTotalUsage());
						String f=g.get(i).getData();
						f=dt.getDate(f);
						obj[j][0]=a;
						obj[j][1]=b;
						obj[j][2]=c;
						obj[j][3]=d;
						obj[j][4]=e;
						obj[j][5]=f;
						j++;
					}
					if(g.size()>0) {
						JTable table = new JTable(obj, Names);
						JScrollPane scrollPane = new JScrollPane(table);
						table.setPreferredScrollableViewportSize(new Dimension(600, 400));
						panel2.removeAll();
						panel2.add(scrollPane);
						frame.repaint();
						frame.setVisible(true);
					}
					else {
						panel2.removeAll();
						frame.repaint();
						frame.setVisible(true);
					}

				}
				else if (jrbName.equals("WEEKLY")) {
					DateTransform dt=new DateTransform();
					String[] Names = { "Gas Meter ID", "Company ID", "User ID", "Cost Of Gas", "Total Usage","Start Date","End Date" };
					obj =new String[1000][7];
					Double cost=0.0, usage=0.0;
					String a,b,c,sd,se,f;
					Double d,e;
					for(int i=0,j=0,start=0;i<g.size();i++) {
						d=g.get(i).getCost();
						e=g.get(i).getTotalUsage();
						f=dt.getDate(g.get(i).getData());
						try {
							if(dt.isSameWeek(dt.getDate(g.get(start).getData()),f)==1) {
								cost=cost+d;
								usage=usage+e;
							}
							if(i==g.size()-1||dt.isSameWeek(dt.getDate(g.get(start).getData()),dt.getDate(g.get(i+1).getData()))==0) {
								a=g.get(i).getMeterID();
								b=g.get(i).getCompanyID();
								c=g.get(i).getUserID();
								sd=String.valueOf(cost);
								se=String.valueOf(usage);
								obj[j][0]=a;
								obj[j][1]=b;
								obj[j][2]=c;
								obj[j][3]=sd;
								obj[j][4]=se;
								obj[j][5]=dt.getDate(g.get(start).getData());
								obj[j][6]=dt.getDate(g.get(i).getData());
								j++;
								start=i+1;
								cost=0.0;
								usage=0.0;
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(g.size()>0) {
						JTable table = new JTable(obj, Names);
						JScrollPane scrollPane = new JScrollPane(table);
						table.setPreferredScrollableViewportSize(new Dimension(600, 400));
						panel2.removeAll();
						panel2.add(scrollPane);
						frame.repaint();
						frame.setVisible(true);
					}
					else {
						panel2.removeAll();
						frame.repaint();
						frame.setVisible(true);
					}

				}
				else if (jrbName.equals("MONTHLY")) {
					DateTransform dt=new DateTransform();
					String[] Names = { "Gas Meter ID", "Company ID", "User ID", "Cost Of Gas", "Total Usage","Start Date","End Date" };
					obj =new String[1000][7];
					Double cost=0.0, usage=0.0;
					String a,b,c,sd,se,f;
					Double d,e;
					for(int i=0,j=0,start=0;i<g.size();i++) {
						d=g.get(i).getCost();
						e=g.get(i).getTotalUsage();
						f=dt.getDate(g.get(i).getData());
						try {
							if(dt.isSameMonth(dt.getDate(g.get(start).getData()),f)==1) {
								cost=cost+d;
								usage=usage+e;
							}
							if(i==g.size()-1||dt.isSameMonth(dt.getDate(g.get(start).getData()),dt.getDate(g.get(i+1).getData()))==0) {
								a=g.get(i).getMeterID();
								b=g.get(i).getCompanyID();
								c=g.get(i).getUserID();
								sd=String.valueOf(cost);
								se=String.valueOf(usage);
								obj[j][0]=a;
								obj[j][1]=b;
								obj[j][2]=c;
								obj[j][3]=sd;
								obj[j][4]=se;
								obj[j][5]=dt.getDate(g.get(start).getData());
								obj[j][6]=dt.getDate(g.get(i).getData());
								j++;
								start=i+1;
								cost=0.0;
								usage=0.0;
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(g.size()>0) {
						JTable table = new JTable(obj, Names);
						JScrollPane scrollPane = new JScrollPane(table);
						table.setPreferredScrollableViewportSize(new Dimension(600, 400));
						panel2.removeAll();
						panel2.add(scrollPane);
						frame.repaint();
						frame.setVisible(true);
					}
					else {
						panel2.removeAll();
						frame.repaint();
						frame.setVisible(true);
					}
				}
			}
		}
		 /**
	     * A inner class used to perform button action that back to GasUsage GUI or HomePageOfCustomer GUI
	     *
	     */
		public class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				String buttonName = event.getActionCommand();
				if (buttonName.equals("Back")) {
					frame.dispose();
					new GasUsage(id);
				}
				else {
					frame.dispose();
					new HomePageOfCustomer(id);
				}
			}
		}

}
