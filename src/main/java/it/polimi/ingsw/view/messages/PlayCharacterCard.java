package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

public class PlayCharacterCard extends PlayerAction {
    @Serial
    private static final long serialVersionUID = -695694550449639585L;
    private String player;
    private final int characterCard;

    public PlayCharacterCard(String player, int characterCard) {
        this.player = player;
        this.characterCard = characterCard;
    }

    @Override
    public void process(GameController controller) {
        controller.playCharacterCard(player, characterCard);
    }
}
