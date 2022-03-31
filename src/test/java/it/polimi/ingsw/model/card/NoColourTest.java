package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoColourTest {
    private Game gameTest;
    private NoColour cardTest;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new NoColour(gameTest);
        gameTest.addCharacterCard(cardTest);
        gameTest.addPlayer(new ExpertPlayer("sara", Wizard.TYPE_2, TowerColour.WHITE));
        gameTest.addPlayer(new ExpertPlayer("sara", Wizard.TYPE_3, TowerColour.GREY));
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }
    }

    @Test
    void setColour() {
        for (Colour colour1 : Colour.values()) {
            cardTest.setColour(colour1);
            assertEquals(colour1, cardTest.getColour());
        }
    }


    @Test
    void calculateInfluencePlayer() {
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.PINK);
        gameTest.getTable().getGroupIslandByIndex(0).changeInfluence(gameTest.getPlayerByIndex(0));
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.YELLOW);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addProfessor(Colour.PINK);
        gameTest.getPlayerByIndex(1).getSchoolBoard().addProfessor(Colour.RED);
        gameTest.getPlayerByIndex(1).getSchoolBoard().addProfessor(Colour.YELLOW);
        cardTest.setColour(Colour.PINK);
        cardTest.calculateInfluence(0);
        assertEquals(gameTest.getPlayerByIndex(1), gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
    }

}