import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.sql.ResultSet;


public class Command implements Serializable
{
    public String command;
    public String[] args;
    public Database database;

    public Command (String command, String[] args)
    {
        this.command = command;
        this.args = args;
    }

    @Override
    public String toString ()
    {
        String result = "Command: ";
        result += command;
        result += "\nArgs:";
        for(String arg : args)
        {
            result += " " + arg;
        }
        return result;
    }
}

