package ControlClasses;

import EntityClasses.*;

import java.util.*;

/**
 * this class is used to refresh the recent info in gas table and electric table
 * @author Ji Jie
 * @version 1.0
 */
public class Refresh {

    public Refresh() throws Exception {
         ElecTimers();
         GasTimers();
    }

    /**
     * this method is used to refresh electric table info in static time
     * @throws Exception
     */

    public void ElecTimers() throws Exception {
        // TODO Auto-generated method stub
        int wait = 30;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ArrayList<UserInfo> userInfos = new UserInfo().fileReader();

                for(UserInfo str:userInfos){
                    new Electric().reflash(str.getUserID());
                }

            }
        }, wait*1000,wait*1000);

    }

    /**
     * this method is used to refresh gas table info in static time
     * @throws Exception
     */
    public void GasTimers() throws Exception {
        // TODO Auto-generated method stub
        int wait = 1800;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ArrayList<UserInfo> userInfos = new UserInfo().fileReader();

                for(UserInfo str:userInfos){
                    new Gas().reflash(str.getUserID());
                }

            }
        }, wait*1000,wait*1000);

    }

}
