package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TwoPointsTest {

    private TwoPoints cardTest;
    private Game gameTest;
    private Player player1;

    @BeforeEach
    void setup(){
        gameTest = new ExpertGame();
        cardTest = new TwoPoints(gameTest);
        player1 = new ExpertPlayer("Laura", Wizard.TYPE_1);

        gameTest.addPlayer(player1);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();
    }

    @Test
    void calculateInfluencePlayer(){

    }
}
