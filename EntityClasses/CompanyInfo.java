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
public class CompanyInfo implements Global,BasicOperation,HigherLevelOp
{
    String companyID;
    String companyName;
    String password;
    double costOfEle;
    double costOfGas;
    String timeforbill;
    String fileName;
    public CompanyInfo(){ fileName = comFileName;   }

    public CompanyInfo(String companyID, String companyName, String password, double costOfEle, double costOfGas, String timeforbill) {
        this.companyID = companyID;
        this.companyName = companyName;
        this.password = password;
        this.costOfEle = costOfEle;
        this.costOfGas = costOfGas;
        this.timeforbill = timeforbill;
        fileName = comFileName;

    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCostOfEle() {
        return costOfEle;
    }

    public void setCostOfEle(double costOfEle) {
        this.costOfEle = costOfEle;
    }

    public double getCostOfGas() {
        return costOfGas;
    }

    public void setCostOfGas(double costOfGas) {
        this.costOfGas = costOfGas;
    }

    public String getTimeforbill() {
        return timeforbill;
    }

    public void setTimeforbill(String timeforbill) {
        this.timeforbill = timeforbill;
    }
    /**
     * method: getPassWord
     * funcation: get password of specific input id
     * @param ID: the id of the company ;
     *
     *@return password if password==null the ID is not exist
     **/
    public   String getPassWord(String ID){

        String password="";
        if (ID.charAt(0)=='H'){
            ArrayList<UserInfo> info = new UserInfo().fileReader();
            for (UserInfo item:info){
                String id = item.getUserID();
                if(id.contains(ID)&&id.length()==ID.length() )
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
    public CompanyInfo getInfo(String ID) {
        CompanyInfo company = new CompanyInfo();

        ArrayList<CompanyInfo> com = new CompanyInfo().fileReader();
        for (CompanyInfo item : com) {
            String id = item.getCompanyID();
            if (id.contains(ID)&&id.length()==ID.length()) {
                company = item;
            }
        }
        return company;
    }

    @Override
    public  ArrayList<CompanyInfo> fileReader(){
        File file = new File(fileName);
        ArrayList<CompanyInfo> companyInfos= new ArrayList<>();

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine())!=null){
                String [] arr = tempString.split("\\s+");
                companyInfos.add( new CompanyInfo( arr[0],arr[1],arr[2],Double.valueOf(arr[3]),Double.valueOf(arr[4]),arr[5]) );
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

        return companyInfos;
    }

    @Override

    public   ArrayList<Gas> getGasInfoList(String companyID){
        ArrayList<Gas> gas = new  ArrayList<Gas>();
        ArrayList<Gas> gasInfo = new Gas().fileReader();
        for (Gas item : gasInfo) {
            String id = item.getCompanyID();
            if (id.contains(companyID)&&id.length()==companyID.length()) {
                gas.add(item);

            }
        }
        return gas;
    }

    @Override
    public  void Delete(String companyID){

    }

    @Override
    public    ArrayList<Electric> getElecInfoList(String companyID){
        ArrayList<Electric> ele = new  ArrayList<Electric>();
        ArrayList<Electric> eleInfo = new Electric().fileReader();
        for (Electric item : eleInfo) {
            String id = item.getCompanyID();
            if (id.indexOf(companyID) != -1) {
                ele.add(item);
            }
        }
        return ele;
    }

    @Override
    public String toString() {
        return  companyID + " "+ companyName + " "+ password + " " + costOfEle +" "+ costOfGas +" "+ timeforbill ;
    }

    @Override
    public void WriteFiles(Object CompanyInfos){
        ArrayList<CompanyInfo> companyInfos = (ArrayList<CompanyInfo>) CompanyInfos;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file);//

            for(int i = 0;i<companyInfos.size();i++){
                fw.write(companyInfos.get(i).toString()+"\r\n");
                fw.flush();//
            }

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

    @Override
    public void WriteFiles_append( Object CompanyInfos){
        ArrayList<CompanyInfo> companyInfos = (ArrayList<CompanyInfo>) CompanyInfos;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file,true);//
            int i = companyInfos.size();
            fw.write(companyInfos.get(i-1).toString()+"\r\n");
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
