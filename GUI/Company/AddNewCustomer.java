package GUI.Company;

import EntityClasses.*;
import ControlClasses.DateTransform;
import GUI.Global;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * AddNewCustomer.java
 * Description: this method is used to add new user into the company, and allocation gas and electricity meter for the
 * consumer automatic
 * @author1 Xueying Yang
 * @version 1.0
 * @author2 Jie ji
 * @version 1.1
 */
public class AddNewCustomer extends JFrame implements ActionListener, Global {
	private JButton bt1,bt0,bt2;
	private JPanel input,back;
	private JLabel label,label_id,label_name,label_password,label_e_budget,label_g_budget,label_email;
	private JTextField text_id,text_name,text_password,text_e_budget,text_g_budget,text_email;
	String cID;

	/**this is the constructor of the class
	 * @param cID means a company ID
	 */
	public AddNewCustomer(String cID){
		this.cID = cID;
		setTitle("SEMMS-Add new Customer");
		setBounds(100,100,800,600);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(700, 500));
		setLocation(100, 100);
		label=new JLabel("please enter your information");

		input=new JPanel();
		input.setBorder(BorderFactory.createEmptyBorder(0,300,50,300));
		GridLayout a=new GridLayout(9,2,10,10);
		//a.layout_
		input.setLayout(a);

		label_id=new JLabel("id:");
		text_id=new JTextField(10);
		input.add(label_id);
		input.add(text_id);

		label_name=new JLabel("name:");
		text_name=new JTextField(10);
		input.add(label_name);
		input.add(text_name);

		label_password=new JLabel("password:");
		text_password=new JTextField(10);
		input.add(label_password);
		input.add(text_password);

		label_e_budget=new JLabel("E_budget:");
		text_e_budget=new JTextField(10);
		text_e_budget.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == KeyEvent.VK_PERIOD )){

				}else{
					e.consume();
				}
			}
		});
		input.add(label_e_budget);
		input.add(text_e_budget);

		label_g_budget=new JLabel("G_budget:");
		text_g_budget=new JTextField(10);
		text_g_budget.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == KeyEvent.VK_PERIOD )){

				}else{
					e.consume();
				}
			}
		});
		input.add(label_g_budget);
		input.add(text_g_budget);

		label_email=new JLabel("e-mail:");
		text_email=new JTextField(10);
		input.add(label_email);
		input.add(text_email);

		bt1=new JButton("add");
		bt0=new JButton("back");
		bt2=new JButton("Company Home");
		bt1.addActionListener(this);
		bt0.addActionListener(this);
		bt2.addActionListener(this);
		back=new JPanel();
		back.setLayout(new FlowLayout());
		back.add(bt1);
		back.add(bt0);
		back.add(bt2);

		add(label,BorderLayout.NORTH);
		add(input,BorderLayout.CENTER);
		add(back,BorderLayout.SOUTH);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * this method is used to judge the input string is legal email form.
	 * @param string
	 * @return
	 */
	int isEmail(String string) {
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
	@Override

 /**
  * this method is used to linsten to an event
  * @param e event of the action
  */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bt0){
			dispose();
			CustomerOperation software=new CustomerOperation(cID);

		}
		if(e.getSource()==bt2){
			dispose();
			CompanyHomePage ch=new CompanyHomePage(cID);
		}
		if(e.getSource()==bt1) {
			int check = 0;
			if(text_password.getText().isEmpty()||text_id.getText().isEmpty()||text_name.getText().isEmpty()||text_e_budget.getText().isEmpty()||text_g_budget.getText().isEmpty()){
				check = -1;
				JOptionPane.showMessageDialog(null, "This filed can't be null! Please enter the a value ! ", "Warning",JOptionPane.WARNING_MESSAGE);
			}
			if(isEmail(text_email.getText())==0){
				check = -1;
				JOptionPane.showMessageDialog(null, "Please enter the a value !Like: kn32@gmail.com!", "Warning",JOptionPane.WARNING_MESSAGE);

			}

			if(check == 0){
				ArrayList<UserInfo> userInfos = new UserInfo().fileReader();

				boolean flag = false;

				for(int i=0;i<userInfos.size();i++){
					if(userInfos.get(i).getUserID().contains(text_id.getText()) && userInfos.get(i).getUserID().length() == text_id.getText().length()){
						flag=true;
						break;
					}
				}

				if(!flag){
					UserInfo userInfo = new UserInfo();
					userInfo.setRegisterDate(new DateTransform().getCurrenTime());
					userInfo.setEleBudget(Double.valueOf(text_e_budget.getText()));
					userInfo.setCompanyID(cID);
					userInfo.setGasBudget(Double.valueOf(text_g_budget.getText()));
					userInfo.setPassWord(text_password.getText());
					userInfo.setUserID(text_id.getText() );
					userInfo.setUserName(text_name.getText());
					userInfo.setEmail(text_email.getText());
					addCustomer(userInfo,userInfos);
				}
				else{
					JPanel jPanel = new JPanel();
					JOptionPane.showMessageDialog(jPanel, "There is already "+text_id.getText()+" in the table!", "warnning!",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	public void addCustomer(UserInfo userInfo,ArrayList<UserInfo> userInfos){

		Gas gas = new Gas();
		Electric electric = new Electric();
		ArrayList<Gas> gases = new Gas().fileReader();
		ArrayList<Electric> electrics = new Electric().fileReader();
		String currentTime = new DateTransform().getCurrenTime();

		gas.setTotalUsage(0);
		gas.setCost(0);
		gas.setUserID(userInfo.getUserID());
		gas.setMeterID(new Gas().getNewID(newGasFileName));
		gas.setCompanyID(cID);
		gas.setData(currentTime);

		electric.setTotalUsage(0);
		electric.setCost(0);
		electric.setUserID(userInfo.getUserID());
		electric.setMeterID(new Electric().getNewID(newEleFileName));
		electric.setCompanyID(cID);
		electric.setData(currentTime);

		gases.add(gas);
		electrics.add(electric);
		userInfos.add(userInfo);

		new Gas().WriteFiles_append(gases);
		new Electric().WriteFiles_append(electrics);
		new UserInfo().WriteFiles_append(userInfos);

	}
}
