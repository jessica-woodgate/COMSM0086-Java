package com.company.DBCommand;

import com.company.DBExceptions.DBException;
import com.company.DBExceptions.EmptyData;
import com.company.DBExceptions.StorageType;

import java.util.ArrayList;

public class UseCMD extends Parser implements DBCommand{

    private int index;
    private final String databaseName;
    private final StorageType type;

    public UseCMD(ArrayList<String> command, int index) throws DBException {
        this.index = index;
        type = StorageType.DATABASE;
        databaseName = parseDatabaseName(command, index);
        this.index += 2;
        //increase index to be pointing to the ; after databaseName
    }

    public String getDatabaseName() throws EmptyData {
        if(databaseName!=null) {
            return databaseName;
        }
        throw new EmptyData("database name");
    }

    public int getIndex(){
        return index;
    }

    public StorageType getType(){
        return type;
    }
}
