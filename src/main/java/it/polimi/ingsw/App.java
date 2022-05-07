package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Client2;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Client client = new Client();
        client.connect();
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {

        }
        Client2 client2 = new Client2();
        client2.connect();

    }
}
