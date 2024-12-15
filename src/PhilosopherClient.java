import java.io.*;
import java.net.*;
import java.util.Random;

public class PhilosopherClient {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private int id;

    public static void main(String[] args) {
        new PhilosopherClient().start();
    }

    public void start() {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(ProtocolConstants.HELLO);
            String response = in.readLine();
            if (response.startsWith(ProtocolConstants.HI)) {
                id = Integer.parseInt(response.split(" ")[1]);
                System.out.println("Connected as Philosopher " + id);

                Random random = new Random();
                while (true) {
                    think();
                    out.println(ProtocolConstants.REQUEST_FORKS);
                    String forkResponse = in.readLine();
                    if (forkResponse.equals(ProtocolConstants.FORKS_GRANTED)) {
                        eat();
                        out.println(ProtocolConstants.RELEASE_FORKS);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void think() {
        try {
            int thinkTime = Math.max((int) (new Random().nextGaussian() * 2 + 5), 0) * 1000;
            System.out.println("Philosopher " + id + " is thinking for " + thinkTime + " ms.");
            Thread.sleep(thinkTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        try {
            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
