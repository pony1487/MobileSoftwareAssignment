package com.example.ronan.assignment;

import android.app.Activity;
import android.database.SQLException;

/**
 * Created by Ronan on 07/11/2017.
 * Accesses Database
 *
 * NOT USED. Could not get database to open here. Come back to this if there is time
 */

public class HandRangeDAO extends Activity
{
    private DatabaseAccess dbAccess;

    public HandRangeDAO()
    {
        dbAccess = new DatabaseAccess(this);
        //open
        try {
            dbAccess.open();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        dbAccess.close();
    }

    public String getHandRange(int stackSize,String position)
    {
        return "";
    }


}
