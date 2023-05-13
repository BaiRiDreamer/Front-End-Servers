import java.sql.*;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

/**
 * 该类是用来操作数据库所需要的方法和函数等
 */
public class Database
{
    static Connection con = null;
    private ResultSet resultSet;

    private String host = "localhost";
    private String dbname = "Project2";
    private String user = "checker";
    private String pwd = "123456";
    private String port = "5432";

    public Database (String host, String dbname, String user, String pwd, String port)
    {
        this.host = host;
        this.dbname = dbname;
        this.user = user;
        this.pwd = pwd;
        this.port = port;
        openDatasource();
    }

    public Database ()
    {
        openDatasource();
    }

    public void openDatasource ()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e)
        {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try
        {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            con = DriverManager.getConnection(url, user, pwd);
        }
        catch (SQLException e)
        {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void closeDatasource ()
    {
        if (con != null)
        {
            try
            {
                con.close();
                con = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


}