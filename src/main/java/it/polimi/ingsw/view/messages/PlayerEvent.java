package it.polimi.ingsw.view.messages;

public abstract class PlayerEvent {
    private static final long serialVersionUID = -695696550449639585L;
    private int player;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player){
        this.player = player;
    }
}
