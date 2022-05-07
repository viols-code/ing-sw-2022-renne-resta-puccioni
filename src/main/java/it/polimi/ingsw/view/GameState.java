package it.polimi.ingsw.view;

/**
 * Enumerates all the different possible states that characterize the game.
 */
public enum GameState {
    CONNECTING,
    CHOOSING_NAME,
    CHOOSING_PLAYERS,
    CHOOSING_GAME_MODE,
    WAITING_PLAYERS,
    STARTING,
    SELECT_ASSISTANT_CARD,
    WAIT_SELECT_ASSISTANT_CARD,
    PLAYING
}
