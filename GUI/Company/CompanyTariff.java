package GUI.Company;

import EntityClasses.*;
import GUI.Customer.init;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Title: CompanyTariff.java
 * <p>Description: Chang company's tariff imformation
 * @author Han Jiang
 * @version 1.2
 */
public class CompanyTariff {

	private JFrame frmCompanytariff;
	private JTextField textField;
	private JTextField textField_1;
	String cID;
	/**
	 * Create the application.
	 * @param num the sequence of company
	 */
	public CompanyTariff(int num) {
		initialize(num);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param num the sequence of company
	 */
	private void initialize(int num) {

		ArrayList<CompanyInfo> companyInfos= new CompanyInfo().fileReader();

		cID = companyInfos.get(num).getCompanyID();
		frmCompanytariff = new JFrame();
		frmCompanytariff.setVisible(true);
		frmCompanytariff.setTitle("SEMMS-Company Tariff");
		frmCompanytariff.setBounds(100, 100, 800, 600);
		frmCompanytariff.setMinimumSize(new Dimension(700, 500));
		frmCompanytariff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompanytariff.getContentPane().setLayout(null);
		frmCompanytariff.setLocation(100, 100);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 784, 180);
		frmCompanytariff.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblTheCurrent = new JLabel("The curent E_tariff is (p/kwh):");
		lblTheCurrent.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblTheCurrent.setBounds(32, 36, 226, 95);
		panel.add(lblTheCurrent);
		
		JLabel label = new JLabel();
		label.setText(String.valueOf(companyInfos.get(num).getCostOfEle()));
		label.setFont(new Font("Times New Roman", Font.BOLD, 16));
		label.setBounds(250, 36, 89, 95);
		panel.add(label);
		
		JLabel lblSetANew = new JLabel("Set a new E_tariff (p/kwh):");
		lblSetANew.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSetANew.setBounds(373, 36, 202, 95);
		panel.add(lblSetANew);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == KeyEvent.VK_PERIOD )){

				}else{
					e.consume();
				}
			}
		});
		textField.setBounds(564, 74, 74, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 181, 784, 180);
		frmCompanytariff.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTheCurentGtariff = new JLabel("The curent G_tariff is (p/kwh):");
		lblTheCurentGtariff.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblTheCurentGtariff.setBounds(32, 40, 226, 95);
		panel_1.add(lblTheCurentGtariff);
		
		JLabel label_2 = new JLabel();
		label_2.setText(String.valueOf(companyInfos.get(num).getCostOfGas()));
		label_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		label_2.setBounds(250, 40, 89, 95);
		panel_1.add(label_2);
		
		JLabel lblSetANew_1 = new JLabel("Set a new G_tariff (p/kwh):");
		lblSetANew_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSetANew_1.setBounds(373, 40, 202, 95);
		panel_1.add(lblSetANew_1);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == KeyEvent.VK_PERIOD )){

				}else{
					e.consume(); 
				}
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(564, 78, 74, 21);
		panel_1.add(textField_1);
		/**
		 * Save the imformation
		 */
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newE_tax = textField.getText();
				String newG_tax = textField_1.getText();
				if(!("".equals(textField.getText().trim())))
				{
					companyInfos.get(num).setCostOfEle(Double.parseDouble(newE_tax));
				}
				if(!("".equals(textField_1.getText().trim())))
				{
					companyInfos.get(num).setCostOfGas(Double.parseDouble(newG_tax));
				}
				new CompanyInfo().WriteFiles(companyInfos);
				frmCompanytariff.dispose();
				new CompanyHomePage(cID);
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(107, 440, 120, 45);
		frmCompanytariff.getContentPane().add(btnNewButton);
		/**
		 * go back to company homepage
		 */
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCompanytariff.dispose();
				new CompanyHomePage(cID);
			}
		});
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnBack.setBounds(308, 440, 120, 45);
		frmCompanytariff.getContentPane().add(btnBack);
		/**
		 * turn off the system
		 */
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCompanytariff.dispose();
				new init();
			}
		});
		btnQuit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnQuit.setBounds(505, 440, 120, 45);
		frmCompanytariff.getContentPane().add(btnQuit);
	}
}
