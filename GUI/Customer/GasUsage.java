package GUI.Customer;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import EntityClasses.*;
import ControlClasses.*;
import GUI.Global;
/**
 * Title: GasUsage.java
 * Description: this class is a GUI which shows the Usage and Cost of gas today. 
 * @author Yihan Lan
 * @version 1.0
 */
public class GasUsage extends JFrame implements Global {

	JFrame frame=new JFrame("SEMMS-GAS USAGE INFORMATION");
	String id;
	JudgeBill t=new JudgeBill();
	DateTransform dt = new DateTransform();

	public GasUsage(String id) {
		this.id=id;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		JPanel panel4=new JPanel();
		JPanel panel4_1=new JPanel();
		JPanel panel4_2=new JPanel();
		JPanel topPanel1 = new JPanel();
		JLabel label2;
		JLabel label3;

		JLabel label1=new JLabel("GAS USAGE INFORMATION");
		label1.setFont(new Font("Serif", Font.PLAIN, 25));
		ArrayList<Gas> g = new UserInfo().getGasInfoList( id );
		int i=0;
		while(i<g.size()&&dt.isToday(g.get(i).getData())==false) {
			i++;
			if(i==g.size()) {
				break;
			}
		}
		if(i==g.size()) {
			label2=new JLabel("The usage is :  null");
			label3=new JLabel("The cost is :  null");
		}
		else {
			label2=new JLabel("The usage is :  "+g.get(i).getTotalUsage());
			label3=new JLabel("The cost is :  "+g.get(i).getCost());
			new Thread(new RefreshGasTime(label2,label3,id)).start();

		}

		label2.setFont(new Font("Serif", Font.PLAIN, 25));
		label3.setFont(new Font("Serif", Font.PLAIN, 25));


		if(t.hasBill(id)&& t.hasvisited(id)==false) {
			JLabel label4=new JLabel("You have a bill ");
			label1.setFont(new Font("Serif", Font.PLAIN, 20));
			JButton button0=new JButton("View");
			button0.addActionListener(new Button0Listener());
			panel4_2.add(label4);
			panel4_2.add(button0);

		}
		JButton  button1=new JButton("Back");
		button1.addActionListener(new Button1Listener());
		JButton button2=new JButton("Historic cost");
		button2.addActionListener(new Button2Listener());

		panel1.add(label1);
		panel2.setLayout(new GridLayout(3,0));
		panel2.add(label2);
		panel2.add(label3);
		panel3.add(button1);

		BoxLayout box=new BoxLayout(panel4,BoxLayout.Y_AXIS);
		topPanel1.add(Box.createVerticalStrut(5));
		panel4.setLayout(box);
		panel4.add(topPanel1);
		panel4.add(panel4_1);
		panel4.add(panel4_2);
		panel4_1.add(button2);


		frame.getContentPane().add(BorderLayout.NORTH,panel1);
		frame.getContentPane().add(BorderLayout.CENTER,panel2);
		frame.getContentPane().add(BorderLayout.SOUTH,panel3);
		frame.getContentPane().add(BorderLayout.EAST,panel4);
		frame.setSize(800,600);
		frame.setMinimumSize(new Dimension(700, 500));
		frame.setLocation(100, 100);
		frame.setVisible(true);
	}
	 /**
     * A inner class used to perform button action that generate Bill GUI
     */
	public class Button0Listener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			frame.dispose();
			t.setVisited(id);
			new bills(id);
		}

	}
	 /**
     * A inner class used to perform button action that generate HomePageOfCustomer GUI
     */
	public class Button1Listener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			frame.dispose();
			new HomePageOfCustomer(id);
		}

	}
	 /**
     * A inner class used to perform button action that generate HistoricalGasUsage GUI
     */
	public class Button2Listener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			frame.dispose();
			new HistoricalGasUsage(id);
		}
	}
	 /**
     * A inner class used to refresh the JLabel periodically
     */
	public class RefreshGasTime implements Runnable, Global{
		JLabel LUsage;
		JLabel LCost;
		String id;
		public RefreshGasTime(JLabel L1,JLabel L2,String id){
			this.LUsage=L1;
			this.LCost=L2;
			this.id=id;
		}

		public void run(){
			while(true){
				try
				{
					Thread.sleep(1800*1000);
				}
				catch(Exception e)
				{}
				ArrayList<Gas> g = new UserInfo().getGasInfoList(id);
				int i=0;
				while(i<g.size() && ( g.get(i).getUserID().equalsIgnoreCase(id)==false ||new DateTransform().isToday( g.get(i).getData() )==false) ) {
					i++;
				}
				LUsage.setText("The usage is :  "+g.get(i).getTotalUsage());
				LCost.setText("The cost is :  "+g.get(i).getCost());
			}
		}
	}
}
