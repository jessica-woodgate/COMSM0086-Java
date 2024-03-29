package com.company.DBCommand;

import com.company.DBExceptions.CommandException;
import com.company.DBExceptions.DBException;
import com.company.DBExceptions.EmptyData;
import com.company.DBExceptions.StorageType;

import java.util.ArrayList;

public class AlterCMD extends Parser implements DBCommand{

    private final ArrayList<String> command;
    private int index;
    private final StorageType type;
    private String tableName;
    private String attributeName;
    private AlterationType alterationType;

    public AlterCMD(ArrayList<String> command, int index) throws DBException {
        this.command = command;
        this.index = index;
        type = StorageType.TABLE;
        if(command != null) {
            if (!parseAlter()) {
                throw new CommandException(
                        command.get(index), index, "database or table name");
            }
        }else{
            throw new EmptyData("ALTER command");
        }
    }

    private boolean parseAlter() throws DBException{
        index++;
        String nextToken = command.get(index);
        checkNextToken(nextToken, "table", index);
        tableName = parseTableName(command, index);
        //increasing index to point to after the table name
        index+=2;
        nextToken = command.get(index);
        switch (nextToken) {
            case ("add"):
                alterationType = AlterationType.ADD;
                index++;
                attributeName = parseAttributeName(command, index);
                index++;
                break;
            case ("drop"):
                alterationType = AlterationType.DROP;
                index++;
                attributeName = parseAttributeName(command, index);
                index++;
                break;
            default:
                return false;
        }
        return true;
    }

    public AlterationType getAlterationType()throws EmptyData{
        if(alterationType!=null){
            return alterationType;
        }
        throw new EmptyData("alteration type");
    }

    public String getAttributeName() throws EmptyData{
        if(attributeName!=null){
            return attributeName;
        }
        throw new EmptyData("attribute name");
    }

    public StorageType getType(){
        return type;
    }

    public String getTableName() throws EmptyData {
        if(tableName!=null) {
            return tableName;
        }
        throw new EmptyData("table name");
    }

    public int getIndex(){
        return index;
    }
}
