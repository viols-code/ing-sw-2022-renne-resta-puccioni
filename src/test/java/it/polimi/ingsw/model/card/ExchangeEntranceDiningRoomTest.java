package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeEntranceDiningRoomTest {
    private Game gameTest;
    private NoColour cardTest;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new NoColour(gameTest);
        gameTest.addCharacterCard(cardTest);
        gameTest.addPlayer(new ExpertPlayer("sara", Wizard.TYPE_2));
        gameTest.addPlayer(new ExpertPlayer("sara",Wizard.TYPE_3));
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }
    }

    @Test
    void effect() {
    }

    @Test
    void setColourDiningRoomEntrance() {
    }
}