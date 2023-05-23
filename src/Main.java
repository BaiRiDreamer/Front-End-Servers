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
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream  ois = new ObjectInputStream(socket.getInputStream());

            // 发送查询请求
            oos.writeObject(new Command("isUserValid", new String[]{"liweihao", "liweihao"}));
            oos.flush();


            // 接收查询结果
            Response response = (Response) ois.readObject();
            System.out.println(response.toString());



            // 发送查询请求
            oos.writeObject(new Command("registerNewUser", new String[]{"liweihao", "666666", "17513173664", "liwiehao"}));
            oos.flush();

            // 接收查询结果
            Response response2 = (Response) ois.readObject();
            System.out.println(response2.toString());
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }


    }
}