package EntityClasses;

import ControlClasses.DateTransform;
import GUI.Global;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Title: Gas.java
 * Description: this class is a object of Gas meter's info
 * @author Jie Ji
 * @version 1.0
 */
public class Gas extends Meter implements Global,BasicOperation, refresh {

    public Gas(){    }
    public Gas(String gasMeterID, String companyID, String userID, double costOfGas, double totalUsage, String data) {
        this. MeterID = gasMeterID;
        this.companyID = companyID;
        this.userID = userID;
        this.cost  = costOfGas;
        this.totalUsage = totalUsage;
        this.data = data;
    }

    @Override
    public  ArrayList<Gas> fileReader() {
        String fileName = gasFileName;
        File file = new File(fileName);
        ArrayList<Gas> gases = new ArrayList<>();
        BufferedReader reader = null;

        try {


            reader = new BufferedReader(new FileReader(file));
            String tempString = "";

            while ((tempString = reader.readLine())!=null){
                String [] arr = tempString.split("\\s+");

                gases.add( new Gas( arr[0],arr[1],arr[2],Double.valueOf(arr[3]),Double.valueOf(arr[4]),arr[5] ) );
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
                catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        }
        return gases;
    }

    @Override
    public void WriteFiles( Object gas){
        String fileName = gasFileName;
        ArrayList<Gas> Gas;
        Gas = (ArrayList<Gas>) gas;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file);//

            for(int i = 0;i<Gas.size();i++){
                fw.write(Gas.get(i).toString()+"\r\n");
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
    public  void Delete (String ID){
        int i =0 ;
        ArrayList<Integer>index=new ArrayList<Integer>();
        ArrayList<Gas> gases = new Gas().fileReader();

        for(Gas gas:gases){
            if(gas.getUserID().contains(ID)&&gas.getUserID().length()==ID.length())
                index.add(i);
            i++;
        }
        i=0;
        if(index!=null){
            for(Integer ind:index){
                gases.remove((int)ind-i);
                i++;
            }
        }
        new Gas().WriteFiles(gases);

}
    @Override
    public void WriteFiles_append( Object gas){
        String fileName = gasFileName;
        ArrayList<Gas> Gas = (ArrayList<Gas>) gas;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file,true);//

            int i = Gas.size();
            fw.write(Gas.get(i-1).toString()+"\r\n");
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

    public Gas getNewRecord(String ID){
        double usage =0;
        double cost =0;
        Gas newGas = new Gas();
        usage = new Random().nextDouble()%0.02;
        usage = usage*180;
        ArrayList<Gas> gas = new UserInfo().getGasInfoList(ID);
        DateTransform df=new DateTransform();
        String time = df.getCurrenTime();
        int hour = df.getHour(time);
        int min = df.getMin(time);
        int sec = df.getSec(time);
        double unitCost = new CompanyInfo().getInfo(gas.get(0).getCompanyID() ).getCostOfGas();

        if(hour ==0){
            usage=0;
            cost =0;
        }
        else{
            usage += gas.get(gas.size()-1).getTotalUsage();
            cost = usage *unitCost;
        }
        newGas.setData(time);
        newGas.setCost(cost);
        newGas.setTotalUsage(usage);
        newGas.setCompanyID(gas.get(gas.size()-1).getCompanyID());
        newGas.setUserID(gas.get(gas.size()-1).getUserID());
        newGas.setMeterID(gas.get(gas.size()-1).getMeterID());

        return newGas;
    }

    public int reflash(String id){
        int flag = 0;
        int i=0;
        Gas g = getNewRecord(id);

        ArrayList<Gas> gases = new Gas().fileReader();
        DateTransform df=new DateTransform();
        if(g.getCost()!=0&&g.getTotalUsage()!=0){
            for(Gas gas : gases){
                if(gas.getMeterID().contains(g.getMeterID())&&df.getYear(gas.getData())==df.getYear(g.getData())&&df.getMonth(gas.getData())==df.getMonth(g.getData())&&df.getDay(gas.getData())==df.getDay(g.getData()))
                    gases.set(i,g);
                i++;
            }
        }
        else
            gases.add(g);
        new Gas().WriteFiles(gases);
        return  flag;
    }

    public String toString() {
        return    MeterID + " "+ companyID + " "+ userID + " " + cost +" "+ totalUsage +" "+ data ;
    }
}
