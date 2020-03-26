package EntityClasses;

import ControlClasses.*;
import GUI.Global;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Title: Electric.java
 * Description: this class is a object of Electric meter's info
 * @author Jie Ji
 * @version 1.0
 */
public class Electric extends Meter implements Global,BasicOperation,refresh {
    String fileName = eleFileName;
public Electric(){}
    public Electric(String eleMeterID, String companyID, String userID, double costOfEle, double totalUsage, String data) {
        this.MeterID = eleMeterID;
        this.companyID = companyID;
        this.userID = userID;
        this.cost = costOfEle;
        this.totalUsage = totalUsage;
        this.data = data;
    }

    @Override
    public ArrayList<Electric> fileReader() {
        File file = new File(fileName);
        ArrayList<Electric> electrics = new ArrayList<>();

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine())!=null){
                String [] arr = tempString.split("\\s+");

                electrics.add( new Electric( arr[0],arr[1],arr[2],Double.valueOf(arr[3]),Double.valueOf(arr[4]),arr[5] ) );
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
    return  electrics;
    }

    @Override
    public void WriteFiles( Object electric){
        ArrayList<Electric> Electric = (ArrayList<Electric>) electric;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file);//

            for(int i = 0;i<Electric.size();i++){
                fw.write(Electric.get(i).toString()+"\r\n");
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
    public  void Delete (String ID){
        int i =0 ;
        ArrayList<Integer>index=new ArrayList<>();
        ArrayList<Electric> electrics = new Electric().fileReader();

        for(Electric electric:electrics){
            if(electric.getUserID().contains(ID)&&electric.getUserID().length()==ID.length())
                index.add(i);
            i++;
        }
        i=0;
        if(index!=null){
            for(Integer ind:index){
                electrics.remove((int)ind-i);
                i++;
            }
        }
        new Electric().WriteFiles(electrics);

    }
    @Override
    public void WriteFiles_append(Object electric){
        ArrayList<Electric> Electric = (ArrayList<Electric>) electric;
        FileWriter fw=null; //
        try {
            File file=new File(fileName);
            fw=new FileWriter(file,true);//

            int i = Electric.size();
            fw.write(Electric.get(i-1).toString()+"\r\n");
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
    public Electric getNewRecord(String ID){
        double usage =0;
        double cost =0;
        Electric newEle = new Electric();
        usage = new Random().nextDouble()%0.06;
        ArrayList<Electric> electrics = new UserInfo().getElecInfoList(ID);
        DateTransform df=new DateTransform();
        String time = df.getCurrenTime();
        int hour = df.getHour(time);
        int min = df.getMin(time);
        int sec = df.getSec(time);
        double unitCost = new CompanyInfo().getInfo(electrics.get(0).getCompanyID() ).getCostOfEle();

        if(hour ==0){
            usage=0;
            cost =0;
        }
        else{
            usage += electrics.get(electrics.size()-1).getTotalUsage();
            cost = usage *unitCost;
        }
        newEle.setData(time);
        newEle.setCost(cost);
        newEle.setTotalUsage(usage);
        newEle.setCompanyID(electrics.get(electrics.size()-1).getCompanyID());
        newEle.setUserID(electrics.get(electrics.size()-1).getUserID());
        newEle.setMeterID(electrics.get(electrics.size()-1).getMeterID());

        return newEle;
    }

    public int reflash(String id){
        int flag = 0;
        int i=0;
        Electric e = getNewRecord(id);

        ArrayList<Electric> electrics = new Electric().fileReader();
        DateTransform df=new DateTransform();
        for(Electric electric : electrics){
            if(electric.getMeterID().contains(e.getMeterID())&&df.getYear(electric.getData())==df.getYear(e.getData())&&df.getMonth(electric.getData())==df.getMonth(e.getData())&&df.getDay(electric.getData())==df.getDay(e.getData()))
                electrics.set(i,e);
            i++;
        }
        new Electric().WriteFiles(electrics);
        return  flag;
    }

    public String toString() {
        return    MeterID + " "+ companyID + " "+ userID + " " + cost +" "+ totalUsage +" "+ data ;
    }
}
