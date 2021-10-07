import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
////https://www.youtube.com/watch?v=s_YsS3jhgWc&t=157s Was where I learnt much of this

public class Client {

    static final int port = 5000;
    public static void main(String[] args) {
        boolean loop = true;

        System.out.println("Enter destination IP address");
        Scanner sc = new Scanner(System.in);
        String host = sc.next();

        try(Socket socket = new Socket(host, port)){

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(loop){
                out.println();
                out.flush();
                System.out.println("Server replied " + in.readLine());
                break;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
