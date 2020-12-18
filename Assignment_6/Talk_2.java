import java.net.SocketException;
import java.util.Scanner;

public class Talk_2 {

  public static final String SYNTAX = "syntax : java Talk_2 myPort destinationHost:destinationPort";

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println(SYNTAX);
      return;
    }
    int localPort = -1;
    try {
      localPort = Integer.parseInt(args[0]);
    } catch (@SuppressWarnings("unused") NumberFormatException e) {
      System.err.println(SYNTAX);
    }
    GroundLayer.RELIABILITY = 0.9;
    Handler ground = null;
    try {
      ground = new GroundHandler(localPort);
    } catch (SocketException e) {
      System.err.println(e.getMessage());
      return;
    }
    Handler connected = new ConnectedHandler(ground,ConnectedHandler.getUniqueID(), args[1]);
        /*321321, args[1]);*/
    Handler myTalk = new ConnectedTerminal(connected);
    Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine()) {
      String msg = sc.nextLine();
      System.out.println("Send message " + msg);
      myTalk.send(msg);
    }
    sc.close();
    System.out.println("closing");
    ground.close();
  }
}
