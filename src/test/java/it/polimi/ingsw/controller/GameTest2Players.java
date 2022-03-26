package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest2Players {

    private GameController gameController;

    @BeforeEach
    void setUp(){
        gameController = new GameController(false, 2);
    }

    @Test
    void gameTest2Players(){

        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_2);
        Player player2 = new BasicPlayer("Viola", Wizard.TYPE_1);
        Player player3 = new BasicPlayer ("Sara", Wizard.TYPE_2)
    }

}
