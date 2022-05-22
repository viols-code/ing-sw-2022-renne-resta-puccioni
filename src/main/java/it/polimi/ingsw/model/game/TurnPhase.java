package it.polimi.ingsw.model.game;

/**
 * Enumeration of the turn phases in the game
 *
 * @version 1.0
 */
public enum TurnPhase {
    WAITING,
    PLAY_ASSISTANT_CARD,
    MOVE_STUDENT,
    MOVE_MOTHER_NATURE,
    CHOOSE_CLOUD_TILE,
    ENDGAME
}