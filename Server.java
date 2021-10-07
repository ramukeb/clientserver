import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
//https://www.youtube.com/watch?v=s_YsS3jhgWc&t=157s Was where I learnt much of this

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        try{
            server = new ServerSocket(5000);
            server.setReuseAddress(true);

            while(true){
                Socket client = server.accept();
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(server != null){
                try{
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getRandQuote(){
        List<String> quotes = new ArrayList<>();
        quotes.add("Positive Energy flows to me and flows from me");
        quotes.add("I breathe in the light of God.");
        quotes.add("I emanate peace and love.");
        quotes.add("All my thoughts, words and actions are divinely guided.");
        quotes.add("I am a spiritual being having a human experience.");
        Random rand = new Random();
        int upper = 5;
        int random = rand.nextInt(upper);

        return quotes.get(random);

    }

    private static class ClientHandler implements Runnable{

        private final Socket clientSocket;

        public ClientHandler(Socket client) {
            this.clientSocket = client;
        }

        @Override
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                while((line = in.readLine()) != null){
                    out.println(getRandQuote());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    if(out != null){
                        out.close();
                        clientSocket.close();
                    }
                    if(in != null){
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
