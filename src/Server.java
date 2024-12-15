import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private final Map<Integer, Philosopher> philosophers = new HashMap<>();
    private final Map<Integer, Fork> forks = new HashMap<>();
    private int philosopherCount = 0;

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {
        initializeForks(5); // Default to 5 forks for the example

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new PhilosopherHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeForks(int numberOfForks) {
        for (int i = 1; i <= numberOfForks; i++) {
            forks.put(i, new Fork());
        }
    }

    private class PhilosopherHandler implements Runnable {
        private final Socket socket;
        private int philosopherId;

        public PhilosopherHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String message;
                while ((message = in.readLine()) != null) {
                    handleMessage(message, out);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (philosopherId != 0) {
                    philosophers.remove(philosopherId);
                }
                System.out.println("Philosopher " + philosopherId + " disconnected.");
            }
        }

        private void handleMessage(String message, PrintWriter out) {
            if (message.startsWith(ProtocolConstants.HELLO)) {
                philosopherId = ++philosopherCount;
                philosophers.put(philosopherId, new Philosopher(philosopherId));
                out.println(ProtocolConstants.HI + " " + philosopherId);
                System.out.println("Philosopher " + philosopherId + " connected.");
            } else if (message.startsWith(ProtocolConstants.REQUEST_FORKS)) {
                handleForkRequest(out);
            } else if (message.startsWith(ProtocolConstants.RELEASE_FORKS)) {
                releaseForks(out);
            }
        }

        private void handleForkRequest(PrintWriter out) {
            int leftFork = philosopherId;
            int rightFork = (philosopherId % forks.size()) + 1;

            synchronized (forks) {
                if (forks.get(leftFork).acquire() && forks.get(rightFork).acquire()) {
                    out.println(ProtocolConstants.FORKS_GRANTED);
                    System.out.println("Philosopher " + philosopherId + " acquired forks " + leftFork + " and " + rightFork);
                } else {
                    if (forks.get(leftFork).acquire()) {
                        forks.get(leftFork).release();
                    }
                    out.println(ProtocolConstants.WAITING);
                    System.out.println("Philosopher " + philosopherId + " is waiting for forks.");
                }
            }
        }

        private void releaseForks(PrintWriter out) {
            int leftFork = philosopherId;
            int rightFork = (philosopherId % forks.size()) + 1;

            synchronized (forks) {
                forks.get(leftFork).release();
                forks.get(rightFork).release();
            }
            out.println(ProtocolConstants.FORKS_RELEASED);
            System.out.println("Philosopher " + philosopherId + " released forks " + leftFork + " and " + rightFork);
        }
    }
}
