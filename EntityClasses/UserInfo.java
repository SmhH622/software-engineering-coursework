package EntityClasses;

import GUI.Global;

import java.io.*;
import java.util.ArrayList;

/**
 * Title: UserInfo.java
 * Description: this class is a object of UserInfo
 * @author Jie Ji
 * @version 1.0
 */
public class UserInfo implements Global,BasicOperation,HigherLevelOp{
    String userID;
    String userName;
    String passWord;
    Double gasBudget;
    Double eleBudget;
    String registerDate;
    String companyID;
    String Email;

    String fileName = userFileName;
    public UserInfo(){  }

    public UserInfo(String userID, String userName, String passWord, Double gasBudget, Double eleBudget, String registerDate, String companyID) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.gasBudget = gasBudget;
        this.eleBudget = eleBudget;
        this.registerDate = registerDate;
        this.companyID = companyID;
    }
    public UserInfo(String userID, String userName, String passWord, Double gasBudget, Double eleBudget, String registerDate, String companyID,String Email) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.gasBudget = gasBudget;
        this.eleBudget = eleBudget;
        this.registerDate = registerDate;
        this.companyID = companyID;
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Double getGasBudget() {
        return gasBudget;
    }

    public void setGasBudget(Double gasBudget) {
        this.gasBudget = gasBudget;
    }

    public Double getEleBudget() {
        return eleBudget;
    }

    public void setEleBudget(Double eleBudget) {
        this.eleBudget = eleBudget;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    @Override
    public  ArrayList<UserInfo> fileReader(){
        File file = new File(fileName);
        ArrayList<UserInfo> userInfos = new ArrayList<>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine())!=null){
                String [] arr = tempString.split("\\s+");

                userInfos.add( new UserInfo( arr[0],arr[1],arr[2],Double.valueOf(arr[3]),Double.valueOf(arr[4]),arr[5],arr[6],arr[7] ) );
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e1)
                {    }
            }
        }

        return userInfos;
    }

    @Override
    public String getPassWord(String ID) {
        String password="";
        if (ID.charAt(0)=='H'){
            ArrayList<UserInfo> info = new UserInfo().fileReader();
            for (UserInfo item:info){
                String id = item.getUserID();
                if(id.contains(ID)&&id.length()==ID.length())
                    password = item.getPassWord();
            }
        }
        else if (ID.charAt(0)=='C'){
            ArrayList <CompanyInfo> info = new CompanyInfo().fileReader();
            for (CompanyInfo item:info){
                String id = item.getCompanyID();
                if (id.contains(ID)&&id.length()==ID.length())
                    password = item.getPassword();
            }
        }
        return password;
    }

    @Override
    public UserInfo getInfo(String ID){
        UserInfo userInfo = new UserInfo();
        ArrayList<UserInfo> user = new UserInfo().fileReader();
        for (UserInfo item:user){
            String id = item.getUserID();
            if(id.contains(ID)&&id.length()==ID.length() ){
                userInfo = item;
            }

        }

        return userInfo;
    }

    @Override
    public    ArrayList<Gas> getGasInfoList(String userID){
        ArrayList<Gas> gas = new  ArrayList<Gas>();
        ArrayList<Gas> gasInfo = new Gas().fileReader();
        for (Gas item : gasInfo) {
            String id = item.getUserID();
            if (id.contains(userID)&&id.length()==userID.length()) {
                gas.add(item);

            }
        }
        return gas;
    }

    @Override
    public   ArrayList<Electric> getElecInfoList(String userID){
        ArrayList<Electric> ele = new  ArrayList<Electric>();
        ArrayList<Electric> eleInfo = new Electric().fileReader();
        for (Electric item : eleInfo) {
            String id = item.getUserID();
            if (id.indexOf(userID) != -1) {
                ele.add(item);
            }
        }
        return ele;
    }

    @Override
    public String toString() {
        return  userID +" " + userName + " "+ passWord + " " + gasBudget +" " + eleBudget +" "+  registerDate + " " + companyID+" "+Email ;
    }

    @Override
    public void WriteFiles( Object userInfo){
        ArrayList<UserInfo> UserInfo = (ArrayList<UserInfo>) userInfo;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file);//

            for(int i = 0;i<UserInfo.size();i++){
                fw.write(UserInfo.get(i).toString()+"\r\n");
                fw.flush();//
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{

            try {
                if(fw!=null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public  void Delete (String  ID){
        int i =0 ,index=-1;
        ArrayList<UserInfo> userInfos = new UserInfo().fileReader();

        for(UserInfo userInfo:userInfos){
            if(userInfo.getUserID().contains(ID))
                index = i;
            i++;
        }
        if(index!=-1)
            userInfos.remove(index);

        new UserInfo().WriteFiles(userInfos);

    }
    @Override
    public void WriteFiles_append( Object userInfo){
        ArrayList<UserInfo> UserInfo = (ArrayList<UserInfo>) userInfo;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file,true);//

            int i = UserInfo.size();
            fw.write(UserInfo.get(i-1).toString()+"\r\n");
            fw.flush();//

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //
            try {
                if(fw!=null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
