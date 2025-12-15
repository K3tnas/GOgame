package pl.pwr.student.gogame.client;

public class Client {
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Pass the server IP as the sole command line argument");
      return;
    }
    GoClient client = new GoClient(args[0]);
    client.play();
  }
}
