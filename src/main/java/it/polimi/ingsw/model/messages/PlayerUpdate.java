package it.polimi.ingsw.model.messages;

public abstract class PlayerUpdate extends ModelUpdate{
    private static final long serialVersionUID = 7600345658820901831L;
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }
}
