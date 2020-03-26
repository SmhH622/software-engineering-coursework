package EntityClasses;

import java.util.ArrayList;
/**
 * HigherLevelOp.java
 * Description: this class a further of the basic read, write and delete operation of files.
 * @author Jie Ji
 * @version 1.1
 */
public interface HigherLevelOp {
    /**
     * method: getPassWord
     * funcation: get password of specific input id
     * @param ID: the id of the company ;
     *
     *@return password if password==null the ID is not exist
     **/
    public   String getPassWord(String ID);

    /**
     * this method is used get info from file
     * @param ID: the id of the company ;
     * @return the info from the file
     */
    public   Object getInfo(String ID);


    /**
     * get Gas informations of specific input id
     * @param companyID:the id of the company
     * @return info the information if info==null the ID is not exist
     */
    public ArrayList<Gas> getGasInfoList(String companyID);

    /**
     * get Electric informations of specific input id
     * @param companyID：the id of the company
     * @return：info the information if info==null the ID is not exist
     */
    public    ArrayList<Electric> getElecInfoList(String companyID);

}
