import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * @author Li Weihao
 */
public class Command implements Serializable
{
    public String command;
    public String[] args;
    public Database database;
    public byte[] file;

    public Command (String command, String[] args)
    {
        this.command = command;
        this.args = args;
    }

    public Command (String command, String[] args, byte[] file)
    {
        this.command = command;
        this.args = args;
        this.file = file;
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

    public Response execute ()
    {
        return new Response("true", "The user exists");
    }
}
