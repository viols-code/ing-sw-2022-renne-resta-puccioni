package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IslandInfluenceTest {
    private IslandInfluence cardTest;
    private Game gameTest;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new IslandInfluence(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();

        player1 = new BasicPlayer("Viola", Wizard.TYPE_4, TowerColour.WHITE);
        player2 = new BasicPlayer("Laura", Wizard.TYPE_1, TowerColour.BLACK);

        gameTest.addPlayer(player1);
        gameTest.addPlayer(player2);

        player1.getSchoolBoard().addTower(8);
        player2.getSchoolBoard().addTower(8);

        player1.getSchoolBoard().addStudentToDiningRoom(Colour.BLUE);
        player1.getSchoolBoard().addProfessor(Colour.BLUE);
        player2.getSchoolBoard().addStudentToDiningRoom(Colour.RED);
        player2.getSchoolBoard().addProfessor(Colour.RED);

        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
    }

    @Test
    void setting() {
        assertEquals(3, cardTest.getCost());
        assertEquals(2, gameTest.getNumberOfPlayer());
    }

    @Test
    void setGroupIsland() {
        cardTest.setGroupIsland(0);
        assertEquals(player1, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertEquals(7, player1.getSchoolBoard().getTowers());
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        cardTest.setGroupIsland(0);
        assertEquals(player2, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertEquals(8, player1.getSchoolBoard().getTowers());
        assertEquals(7, player2.getSchoolBoard().getTowers());

        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        assertEquals(12, gameTest.getTable().getNumberOfGroupIsland());
        cardTest.setGroupIsland(1);
        assertEquals(11, gameTest.getTable().getNumberOfGroupIsland());
        assertEquals(player2, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
    }

    @Test
    void incrementCost() {
        int cost = cardTest.getCost();
        assertEquals(cost, cardTest.getCost());
        cardTest.incrementCost();
        assertEquals(cost + 1, cardTest.getCost());
    }

    @Test
    void setColour() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColour(colour));
        }
    }

    @Test
    void setColourAndIsland() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourAndIsland(colour, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0)));
        }
    }

    @Test
    public void setColourDiningRoomEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourDiningRoomEntrance(colour, colour));
        }
    }

    @Test
    public void setColourCardEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourCardEntrance(colour, colour));
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
            gameTest.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(i,0,gameTest.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    private void settingCard() {
        for (int i = 0; i < gameTest.getNumberOfCharacterCard(); i++) {
            gameTest.getCharacterCardByIndex(i).setting();
        }
    }
}