package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoColourTest {
    private Game gameTest;
    private NoColour cardTest;
    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new NoColour(gameTest);
        gameTest.addCharacterCard(cardTest);
    }

    @Test
    void setColour() {
        for(Colour colour1: Colour.values()){
            cardTest.setColour(colour1);
            assertEquals(colour1,cardTest.getColour());
        }
    }

    @Test
    void calculateInfluencePlayer() {

    }

}