package EntityClasses;

import GUI.Global;
import java.io.*;
import java.util.ArrayList;

/**
 * Title: Bills.java
 * Description: this class is a object of bill
 * @author Jie Ji
 * @version 1.0
 */
public class Bills implements Global, BasicOperation{

    String companyID;

    String userID;

    Double monthlyUsgeOfGas;
    Double monthlyCostOfGas;
    Double monthlyUsgeOfEle;
    Double monthlyCostOfEle;
    String date;
    int read = 0;// already read = 1
    String fileName = billFileName;
    public Bills(){   }

    public Bills(String companyID, String userID, Double monthlyUsgeOfGas, Double monthlyCostOfGas, Double monthlyUsgeOfEle, Double monthlyCostOfEle, String date) {
        this.companyID = companyID;
        this.userID = userID;
        this.monthlyUsgeOfGas = monthlyUsgeOfGas;
        this.monthlyCostOfGas = monthlyCostOfGas;
        this.monthlyUsgeOfEle = monthlyUsgeOfEle;
        this.monthlyCostOfEle = monthlyCostOfEle;
        this.date = date;
    }

    public Bills(String companyID, String userID, Double monthlyUsgeOfGas, Double monthlyCostOfGas, Double monthlyUsgeOfEle, Double monthlyCostOfEle, String date, int read) {
        this.companyID = companyID;
        this.userID = userID;
        this.monthlyUsgeOfGas = monthlyUsgeOfGas;
        this.monthlyCostOfGas = monthlyCostOfGas;
        this.monthlyUsgeOfEle = monthlyUsgeOfEle;
        this.monthlyCostOfEle = monthlyCostOfEle;
        this.date = date;
        this.read = read;
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

    public Double getMonthlyUsgeOfGas() {
        return monthlyUsgeOfGas;
    }

    public void setMonthlyUsgeOfGas(Double monthlyUsgeOfGas) {
        this.monthlyUsgeOfGas = monthlyUsgeOfGas;
    }

    public Double getMonthlyCostOfGas() {
        return monthlyCostOfGas;
    }

    public void setMonthlyCostOfGas(Double monthlyCostOfGas) {
        this.monthlyCostOfGas = monthlyCostOfGas;
    }

    public Double getMonthlyUsgeOfEle() {
        return monthlyUsgeOfEle;
    }

    public void setMonthlyUsgeOfEle(Double monthlyUsgeOfEle) {
        this.monthlyUsgeOfEle = monthlyUsgeOfEle;
    }

    public Double getMonthlyCostOfEle() {
        return monthlyCostOfEle;
    }

    public void setMonthlyCostOfEle(Double monthlyCostOfEle) {
        this.monthlyCostOfEle = monthlyCostOfEle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRead(){return  read;}
    public void setRead(int read){this.read = read;}


    @Override
    public String toString() {
        return  companyID + " " + userID + " " + monthlyUsgeOfGas +" " + monthlyCostOfGas +" "+ monthlyUsgeOfEle +" " + monthlyCostOfEle +" "+ date+" "+read;
    }

    @Override
    public ArrayList<Bills>  fileReader() {
        File file = new File(fileName);
        ArrayList<Bills> bills = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                String[] arr = tempString.split("\\s+");
                bills.add(new Bills(arr[0], arr[1], Double.valueOf(arr[2]), Double.valueOf(arr[3]), Double.valueOf(arr[4]), Double.valueOf(arr[5]), arr[6], Integer.valueOf(arr[7])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return bills;
    }

        @Override
        public  void Delete (String ID){
            int i =0 ;
            ArrayList<Integer>index=new ArrayList<>();
            ArrayList<Bills> bills = new Bills().fileReader();

            for(Bills bill:bills){
                if(bill.getUserID().contains(ID))
                    index.add(i);
                i++;
            }
            i=0;
            if(index!=null){
                for(Integer ind:index){
                    bills.remove((int)ind-i);
                    i++;
                }
            }
            new Bills().WriteFiles(bills);

        }
        @Override
        public void WriteFiles( Object bills){
            ArrayList<Bills> Bills = (ArrayList<Bills>) bills;
            FileWriter fw=null; //
            try {
                File file=new File(fileName);
                fw=new FileWriter(file);//

                for(int i = 0;i<Bills.size();i++){
                    fw.write(Bills.get(i).toString()+"\r\n");
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
        public void WriteFiles_append( Object bills){
            ArrayList<Bills> Bills = (ArrayList<Bills>) bills;
            FileWriter fw=null; //
            try {
                File file=new File(fileName);
                fw=new FileWriter(file,true);//
                int i = Bills.size();
                fw.write(Bills.get(i-1).toString()+"\r\n");
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

