package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpertPlayerTest {
    private Player player1;

   @BeforeEach
   void setUp(){
       player1 = new ExpertPlayer("Viola", Wizard.TYPE_1);
   }

    @Test
    void addCoins() {
        for (int i = 1; i <= 10; i++) {
            player1.addCoins(i);
            assertEquals(i + 1, player1.getCoins());
            player1.removeCoins(i);
        }
    }

    @Test
    void removeCoins() {
        player1.addCoins(10);
        for (int i = 1; i <= 10; i++) {
            player1.removeCoins(i);
            assertEquals(10 - i + 1, player1.getCoins());
            player1.addCoins(i);
        }

    }

}