package EntityClasses;

import java.io.*;
import java.util.ArrayList;

/**
 * Meter .java
 * this class is the farther class of the Gas meter and electric meter
 * @author
 * @version
 */
public class Meter {
    String MeterID;
    String companyID;
    String userID;
    double cost  ;
    double totalUsage;
    String data;
   public Meter(){   }

    public String getMeterID() {
        return  MeterID;
    }

    public void setMeterID(String gasMeterID) {
        this. MeterID = gasMeterID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getCost () {
        return cost ;
    }

    public void setCost(double costOfGas) {
        this.cost  = costOfGas;
    }

    public double getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(double totalUsage) {
        this.totalUsage = totalUsage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * this method is used to return new meter id for new user
     * @param fileN the file path name
     * @return new meter id
     */
    public String getNewID(String fileN){
        String id = null;
        File file = new File(fileN);
        ArrayList<String> ID = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine())!=null){
                String [] arr = tempString.split("\\s+");
                ID.add(arr[0]);
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

        id = ID.get(0);
        ID.remove(0);

        FileWriter fw=null; //
        try {

            fw=new FileWriter(file);//

            for(int i = 0;i<ID.size();i++){
                fw.write(ID.get(i)+"\r\n");
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
        return id;
    }
    @Override
    public String toString() {
        return    MeterID + " "+ companyID + " "+ userID + " " + cost +" "+ totalUsage +" "+ data ;
    }
}
