package GUI.Customer;

import EntityClasses.*;
import ControlClasses.*;
import GUI.Global;

import java.util.ArrayList;

/**
 * Title: CountMonthlyCost.java
 * Description: this class is used to
 * @author Jie Ji
 * @version 1.2
 */
public class CountMonthlyCost implements Global {

    ArrayList<Gas> gas = new ArrayList<Gas>();
    UserInfo userInfo = new UserInfo();
    ArrayList<Electric> ele  = new ArrayList<>();
    CompanyInfo companyInfos ;
    String userID;
    DateTransform df = new DateTransform();
    int companyDay  ;
    int currentDay ;
    int registMonth  ;
    int currentMonth ;
    int registDay  ;
    String currentDate;
    public CountMonthlyCost(String userID){
        this.userID = userID;
        userInfo = new UserInfo().getInfo(userID);
        gas = new UserInfo().getGasInfoList(userID );
        ele = new UserInfo().getElecInfoList(userID );
        companyInfos = new CompanyInfo().getInfo(ele.get(0).getCompanyID());
        currentDate = df.getCurrenTime();

        companyDay = df.getDay(companyInfos.getTimeforbill());
        currentDay = df.getDay(currentDate);
        registMonth = df.getMonth(userInfo.getRegisterDate());
        currentMonth = df.getMonth(currentDate);
        registDay = df.getDay(userInfo.getRegisterDate());
    }
    public double costEle(){
        double cost = 0;

        int lengh = ele.size()-1;
        if(registMonth == currentMonth ){
            int day;
            if(registDay<companyDay){
                 day= currentDay -companyDay+1;

            }
            else{
                day= currentDay -registDay+1;
            }
            for(int i = lengh;i>lengh-day;i--){
                cost+=ele.get(i).getCost();
            }
        }
        else{
            int day;
            if(currentDay<companyDay){
                day= currentDay;

            }
            else{
                day= currentDay - companyDay+1;
            }
            for(int i = lengh;i>lengh-day;i--){
                cost+=ele.get(i).getCost();
            }
        }
        return cost;
    }
    public double costGas(){
        double cost = 0;

        int lengh = ele.size()-1;
        if(registMonth == currentMonth ){
            int day;
            if(registDay<companyDay){
                day= currentDay -companyDay+1;

            }
            else{
                day= currentDay -registDay+1;
            }
            for(int i = lengh;i>lengh-day;i--){
                cost+=gas.get(i).getCost();
            }
        }
        else{
            int day;
            if(currentDay<companyDay){
                day= currentDay;

            }
            else{
                day= currentDay - companyDay+1;
            }
            for(int i = lengh;i>lengh-day;i--){
                cost+=gas.get(i).getCost();
            }
        }
        return cost;
    }
}
