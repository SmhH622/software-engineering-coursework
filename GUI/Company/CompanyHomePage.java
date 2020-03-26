package GUI.Company;

import EntityClasses.*;
import GUI.Customer.init;
import GUI.Global;

import java.util.ArrayList;
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;



/**
 * Title: CompanyHomePage.java
 * <p>Description: The main functions are listed here, you can choose one function by clicking the corresponding button
 * @author Han Jiang
 * @version 1.2
 */
public class CompanyHomePage implements Global {

	ArrayList<CompanyInfo> companyInfos= new ArrayList<>();
	private JFrame Company_HomePage;
    String cID;
	/**
	 * Create the application.
	 * @param cID company id
	 */
	public CompanyHomePage(String cID) {
		initialize(cID);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param cID company id
	 */
	private void initialize(String cID) {

		ArrayList<CompanyInfo> companyInfos= new CompanyInfo().fileReader();

        int i;

        for(i = 0;i<companyInfos.size();i++){
           if( companyInfos.get(i).getCompanyID().contains(cID) ){
               break;
           }
        }
		int num = i;
		Company_HomePage = new JFrame();
		Company_HomePage.setVisible(true);
		Company_HomePage.setTitle("SEMMS-Company_HomePage");
		Company_HomePage.setBounds(100, 100, 800, 600);
		Company_HomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Company_HomePage.getContentPane().setLayout(null);
		Company_HomePage.setMinimumSize(new Dimension(700,500));

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 241, 561);
		Company_HomePage.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCompanyname = new JLabel("CompanyName:");
		lblCompanyname.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCompanyname.setBounds(10, 95, 118, 45);
		panel.add(lblCompanyname);
		
		JLabel lblC = new JLabel();
		lblC.setText(companyInfos.get(num).getCompanyName());
		lblC.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblC.setBounds(133, 95, 78, 45);
		panel.add(lblC);
		
		JLabel lblCurrentEtax = new JLabel("Current E_tax(kw/h):");
		lblCurrentEtax.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCurrentEtax.setBounds(10, 150, 140, 45);
		panel.add(lblCurrentEtax);
		
		JLabel label = new JLabel();
		label.setText(String.valueOf(companyInfos.get(num).getCostOfEle()));
		label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label.setBounds(158, 150, 53, 45);
		panel.add(label);
		
		JLabel lblCurrentGtaxkwh = new JLabel("Current G_tax(kw/h):");
		lblCurrentGtaxkwh.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCurrentGtaxkwh.setBounds(10, 205, 157, 45);
		panel.add(lblCurrentGtaxkwh);
		
		JLabel label_2 = new JLabel();
		label_2.setText(String.valueOf(companyInfos.get(num).getCostOfGas()));
		label_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label_2.setBounds(158, 205, 53, 45);
		panel.add(label_2);
		
		JLabel lblDataToReceive = new JLabel("Data to receive budget:");
		lblDataToReceive.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDataToReceive.setBounds(10, 260, 185, 45);
		panel.add(lblDataToReceive);
		
		JLabel lblfriday = new JLabel("2018/04/27-08:04:26-Friday");
		lblfriday.setText(companyInfos.get(num).getTimeforbill());
		lblfriday.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblfriday.setBounds(20, 298, 211, 34);
		panel.add(lblfriday);
		
		JLabel lblCompanyid = new JLabel("CompanyID:");
		lblCompanyid.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCompanyid.setBounds(10, 40, 97, 45);
		panel.add(lblCompanyid);
		
		JLabel label_5 = new JLabel();
		label_5.setText(companyInfos.get(num).getCompanyID());
		label_5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label_5.setBounds(104, 40, 78, 45);
		panel.add(label_5);
		
		JButton btnNewButton = new JButton("Set Basic Infomation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Company_HomePage.dispose();  
	             new CompanyBasicInfo(num);
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(20, 413, 164, 45);
		panel.add(btnNewButton);
		
		JButton btnSetNewTarrify = new JButton("Set New Tariff");
		btnSetNewTarrify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Company_HomePage.dispose();  
	             new CompanyTariff(num);
			}
		});
		btnSetNewTarrify.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSetNewTarrify.setBounds(20, 468, 164, 45);
		panel.add(btnSetNewTarrify);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(240, 0, 544, 561);
		Company_HomePage.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 534, 120);
		panel_1.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblWelcomeToUse = new JLabel("Welcome to use the company management system!");
		lblWelcomeToUse.setForeground(Color.BLACK);
		panel_2.add(lblWelcomeToUse, BorderLayout.CENTER);
		lblWelcomeToUse.setFont(new Font("Times New Roman", Font.BOLD, 22));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 121, 544, 440);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Customer Management");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Company_HomePage.dispose();  
	        	new CustomerOperation(cID);
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnNewButton_1.setBounds(56, 50, 405, 84);
		panel_3.add(btnNewButton_1);
		
		JButton btnMetersManagement = new JButton("Meters Management");
		btnMetersManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Company_HomePage.dispose();  
	        	new CompanyPerson(cID);
			}
		});
		btnMetersManagement.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnMetersManagement.setBounds(56, 188, 405, 84);
		panel_3.add(btnMetersManagement);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Company_HomePage.dispose();  
	        	new init();
			}
		});
		btnQuit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnQuit.setBounds(56, 314, 405, 84);
		panel_3.add(btnQuit);
	}
}
