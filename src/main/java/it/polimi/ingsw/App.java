package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Client client = new Client(true);
        client.connect();
        client.run();
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {

        }

    }
}
