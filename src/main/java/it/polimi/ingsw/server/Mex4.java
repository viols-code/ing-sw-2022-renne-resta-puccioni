package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.Socket;

public class Mex4 extends ClientMessage2{
    @Serial
    private static final long serialVersionUID = -8678594154824428984L;
    private static final String message="qual è il tuo cognome?";

    public void process(Socket connection, ObjectOutputStream out) throws IOException {

        out.reset();
        out.writeObject(this);
        out.flush();
    }
    @Override
    public String getMessage() {
        return message;
    }
}
