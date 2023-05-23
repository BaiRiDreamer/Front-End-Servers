import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Main
{
    public static void main (String[] args)
    {
        try
        {
            String host = "10.25.3.249";
//            String host = "localhost";
            int port = 7345;
            Socket socket = new Socket(host, port);

            // 发送查询请求
            Command command = new Command("isUserValid", new String[]{"1", "2"});
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(command);
            oos.flush();

            // 接收查询结果
            Response response = (Response) new ObjectInputStream(socket.getInputStream()).readObject();
            System.out.println(response.toString());
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }


    }
}