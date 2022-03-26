package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.Before;
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
        gameTest.addPlayer(new ExpertPlayer("sara",Wizard.TYPE_2));
        gameTest.addPlayer(new ExpertPlayer("sara",Wizard.TYPE_3));
    }

    @Test
    void setColour() {
        for(Colour colour1: Colour.values()){
            cardTest.setColour(colour1);
            assertEquals(colour1,cardTest.getColour());
        }
    }

    @Before
    protected void setUpCalculateInfluencePlayer(){
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.PINK);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.PINK);
    }

    @Test
    void calculateInfluencePlayer() {

    }

    private void setUpSchoolBoardPlayer0(){
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.PINK);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.PINK);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.PINK);
    }
}