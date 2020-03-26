package GUI.Company;

import javax.swing.*;
import java.awt.*;
import javax.swing.JTextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import EntityClasses.*;
import GUI.Customer.init;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 * Title: CompanyBasicInfo.java
 * <p>Description: Chang company's basic imformation here
 * @author Han Jiang
 * @version 1.2
 */
public class CompanyBasicInfo {

	private JFrame frmCompanyBasicInfo;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField;
	String cID;

	/**
	 * Create the application.
	 * @param num the sequence of company
	 */
	public CompanyBasicInfo (int num) {
		initialize(num);

	}

	/**
	 * Initialize the contents of the frame.
	 * @param num the sequence of company
	 */
	private void initialize(int num) {

		ArrayList<CompanyInfo> companyInfos= new CompanyInfo().fileReader();
        cID = companyInfos.get(num).getCompanyID();
		frmCompanyBasicInfo = new JFrame();
		frmCompanyBasicInfo.setVisible(true);
		frmCompanyBasicInfo.setTitle("SEMMS-Company Info");
		frmCompanyBasicInfo.setBounds(100, 100, 800, 600);
		frmCompanyBasicInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompanyBasicInfo.getContentPane().setLayout(null);
		frmCompanyBasicInfo.setMinimumSize(new Dimension(700,500));
		JPanel panel = new JPanel();
		panel.setBounds(0, 86, 784, 104);
		frmCompanyBasicInfo.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblTheCurrent = new JLabel("The curent company name:");
		lblTheCurrent.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblTheCurrent.setBounds(30, 10, 226, 95);
		panel.add(lblTheCurrent);
		
		JLabel lblAcompany = new JLabel();
		lblAcompany.setText(companyInfos.get(num).getCompanyName());
		lblAcompany.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblAcompany.setBounds(226, 10, 89, 95);
		panel.add(lblAcompany);
		
		JLabel lblSetANew = new JLabel("Set a new company name:");
		lblSetANew.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSetANew.setBounds(350, 10, 202, 95);
		panel.add(lblSetANew);
		
		textField_1 = new JTextField();


		textField_1.setBounds(539, 48, 99, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		/**
		 * check if the company name is repeated
		 */
		JButton btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				if(!("".equals(textField_1.getText().trim()))){
					for(i = 0;i<companyInfos.size();i++){
						if((companyInfos.get(i).getCompanyName()).equals(textField_1.getText())){
							JOptionPane.showMessageDialog(null, "The company name is repeated !", "Warning",JOptionPane.WARNING_MESSAGE);
							i=-1;
							break;
						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE); 
				}
				if(i>0){
					JOptionPane.showMessageDialog(null, "The new company name is avaliable! ");
				}
			}
		});
		btnCheck.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnCheck.setBounds(667, 47, 93, 23);
		panel.add(btnCheck);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 191, 784, 210);
		frmCompanyBasicInfo.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTheCurentGtariff = new JLabel("The current data to receive budget:");
		lblTheCurentGtariff.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblTheCurentGtariff.setBounds(30, 0, 254, 95);
		panel_1.add(lblTheCurentGtariff);
		
		JLabel lblfriday = new JLabel();
		lblfriday.setText(companyInfos.get(num).getTimeforbill());
		lblfriday.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblfriday.setBounds(350, 0, 284, 95);
		panel_1.add(lblfriday);
		
		JLabel lblSetANew_1 = new JLabel("Set a new data to receive budget:");
		lblSetANew_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSetANew_1.setBounds(30, 90, 254, 95);
		panel_1.add(lblSetANew_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(350, 128, 209, 21);
		panel_1.add(textField_2);
		
		/**
		 * check if the date format is right
		 */
		JButton button = new JButton("Check");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				if(!("".equals(textField_2.getText().trim())) &&textField_2.getText().trim().length()>=10 ){

						String date = textField_2.getText().trim();
						date = date.substring(0,10);
						String el= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
						Pattern p = Pattern.compile(el);
						Matcher m = p.matcher(date);
						boolean b = m.matches();
						String time =textField_2.getText().trim();
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
					JOptionPane.showMessageDialog(null, "The new time for bill is avaliable! ");
				}
			}
		});
		button.setFont(new Font("Times New Roman", Font.BOLD, 14));
		button.setBounds(667, 127, 93, 23);
		panel_1.add(button);
		
		/**
		 * save the information
		 */
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String newC_ID = textField.getText();
					String newC_Name = textField_1.getText();
					String newC_Data = textField_2.getText();
					if(!("".equals(textField.getText().trim())))
					{
						cID = newC_ID;
						companyInfos.get(num).setCompanyID(newC_ID);
					}
					if(!("".equals(textField_1.getText().trim())))
					{
						companyInfos.get(num).setCompanyName(newC_Name);
					}
					if(!("".equals(textField_2.getText().trim())))
					{
						companyInfos.get(num).setTimeforbill(newC_Data);
					}
					new CompanyInfo().WriteFiles(companyInfos);
					frmCompanyBasicInfo.dispose();
					new CompanyHomePage(cID);
				}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(107, 440, 120, 45);
		frmCompanyBasicInfo.getContentPane().add(btnNewButton);
		
		/**
		 * go back to company homepage
		 */
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCompanyBasicInfo.dispose();
				new CompanyHomePage(cID);
			}
		});
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnBack.setBounds(308, 440, 120, 45);
		frmCompanyBasicInfo.getContentPane().add(btnBack);
		
		/**
		 * turn off the system
		 */
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCompanyBasicInfo.dispose();
				new init();
			}
		});
		btnQuit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnQuit.setBounds(505, 440, 120, 45);
		frmCompanyBasicInfo.getContentPane().add(btnQuit);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(0, 0, 784, 85);
		frmCompanyBasicInfo.getContentPane().add(panel_2);
		
		JLabel lblCompanyid = new JLabel("CompanyID:");
		lblCompanyid.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblCompanyid.setBounds(30, 0, 122, 95);
		panel_2.add(lblCompanyid);
		
		JLabel lblC = new JLabel();
		lblC.setText(companyInfos.get(num).getCompanyID());
		lblC.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblC.setBounds(142, 0, 89, 95);
		panel_2.add(lblC);
		
		JLabel lblSetANew_2 = new JLabel("Set a new CompanyID:");
		lblSetANew_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSetANew_2.setBounds(350, 0, 177, 95);
		panel_2.add(lblSetANew_2);
		
		/**
		 * check if the company id is repeated
		 */
		JButton button_1 = new JButton("Check");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				if(!("".equals(textField.getText().trim()))){
					for(i = 0;i<companyInfos.size();i++){
						if((companyInfos.get(i).getCompanyID()).equals(textField.getText())){
							JOptionPane.showMessageDialog(null, "The company ID is repeated !", "Warning",JOptionPane.WARNING_MESSAGE);
							i=-1;
							break;
						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please enter the correct value ! ", "Warning",JOptionPane.WARNING_MESSAGE); 
				}
				if(i>0){
					JOptionPane.showMessageDialog(null, "The new ID is avaliable! ");
				}
			}
		});
		button_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		button_1.setBounds(667, 37, 93, 23);
		panel_2.add(button_1);
		
		textField = new JTextField();

		textField.setColumns(10);
		textField.setBounds(536, 38, 56, 21);
		panel_2.add(textField);
	}
}
