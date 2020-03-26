package ControlClasses;
import EntityClasses.Bills;
import GUI.Global;

import java.text.*;
import java.util.*; 
/**
 * Title: Time.java
 * Description: this class is a control class contains some method judging whether new bill has been read.
 * @author Yihan Lan
 * @version 1.0
 */
public class JudgeBill implements Global{
	private static final String DATE = "yyyy-MM-dd";
	 /**
     * this method is used to judge whether there has a new bill
     * @param id the id of the customer
     * @return boolean value
     */
	public boolean hasBill(String id) {
		ArrayList<Bills> b= new Bills().fileReader();
		String today=new SimpleDateFormat(DATE).format(new Date());
		String todayYearMonth=today.substring(0,7);
		for(int i=0;i<b.size();i++) {
			if(b.get(i).getUserID().equalsIgnoreCase(id)) {
				String date=b.get(i).getDate();
				String dateYearMonth=date.substring(0,7);
				if(todayYearMonth.equalsIgnoreCase(dateYearMonth)) {
					return true;
				}
			}
		}
		return false;
	}
	 /**
     * this method is used to judge whether the bill has been visited
     * @param id the id of the customer
     * @return boolean value
     */
	public boolean hasvisited(String id) {
		ArrayList<Bills> b= new Bills().fileReader();
		String today=new SimpleDateFormat(DATE).format(new Date());
		String todayYearMonth=today.substring(0,7);
		for(int i=0;i<b.size();i++) {
			if(b.get(i).getUserID().equalsIgnoreCase(id)) {
				String date=b.get(i).getDate();
				String dateYearMonth=date.substring(0,7);
				if(todayYearMonth.equalsIgnoreCase(dateYearMonth)&&b.get(i).getRead()==1) {
					return true;
				}
			}
		}
		return false;
	}
	 /**
     * this method is used to change the flag to true
     * @param id the id of the customer
     */
	public void setVisited(String id) {
		ArrayList<Bills> b= new Bills().fileReader();
		String today=new SimpleDateFormat(DATE).format(new Date());
		String todayYearMonth=today.substring(0,7);
		for(int i=0;i<b.size();i++) {
			if(b.get(i).getUserID().equalsIgnoreCase(id)) {
				String date=b.get(i).getDate();
				String dateYearMonth=date.substring(0,7);
				if(todayYearMonth.equalsIgnoreCase(dateYearMonth)) {
					b.get(i).setRead(1);
					new Bills().WriteFiles(b);
				}
			}
		}
	}
}
