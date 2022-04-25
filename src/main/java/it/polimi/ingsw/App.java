package it.polimi.ingsw;
import it.polimi.ingsw.client.Client;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Client client = new Client();
        client.connect();

    }
}
