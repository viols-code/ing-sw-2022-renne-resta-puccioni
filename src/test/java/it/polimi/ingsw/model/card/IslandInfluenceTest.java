package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandInfluenceTest {
    private IslandInfluence cardTest;
    private Game gameTest;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp(){
        gameTest = new ExpertGame();
        cardTest = new IslandInfluence(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();

        player1 = new BasicPlayer("Viola", Wizard.TYPE_4);
        player2 = new BasicPlayer("Laura", Wizard.TYPE_1);

        gameTest.addPlayer(player1);
        gameTest.addPlayer(player2);

        player1.getSchoolBoard().addTower(8);
        player2.getSchoolBoard().addTower(8);

        player1.getSchoolBoard().addStudentToDiningRoom(Colour.BLUE);
        player1.getSchoolBoard().addProfessor(Colour.BLUE);
        player2.getSchoolBoard().addStudentToDiningRoom(Colour.RED);
        player2.getSchoolBoard().addProfessor(Colour.RED);

        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(Colour.RED);
    }

    @Test
    void setting() {
        assertEquals(3, cardTest.getCost());
        assertEquals(2, gameTest.getNumberOfPlayer());
    }

    @Test
    void setGroupIsland() {
        cardTest.setGroupIsland(0);
        if(gameTest.getTable().getGroupIslandByIndex(0).getInfluence() != null){
            assertEquals(player1, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        }
    }

    private void settingBag() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 2; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }

        for (int i = 1; i < 12; i++) {
            if (i == 5) i++;
            gameTest.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(gameTest.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    private void settingCard() {
        for (int i = 0; i < gameTest.getNumberOfCharacterCard(); i++) {
            gameTest.getCharacterCardsByIndex(i).setting();
        }
    }
}