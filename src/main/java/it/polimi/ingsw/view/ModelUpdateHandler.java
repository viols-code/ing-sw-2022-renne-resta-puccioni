package it.polimi.ingsw.view;

import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.beans.MockCard;

import java.util.List;

/**
 * Class responsible for handling the updates that are received from the server.
 */
public abstract class ModelUpdateHandler {
    private final View view;

    /**
     * Constructs a new ModelUpdateHandler.
     *
     * @param view the view responsible for this model update handler
     */
    protected ModelUpdateHandler(View view) {
        this.view = view;
    }

    /**
     * Gets the View.
     *
     * @return the view
     */
    public View getView() {
        return view;
    }

    public void updateActiveCharacterCard(int characterCard){
        getView().getModel().setCurrentCharacterCard(getView().getModel().getCharacterCardByIndex(characterCard));
    }

    public void updateCardCoins(int characterCard, int coins){
        getView().getModel().getCharacterCardByIndex(characterCard).setCost(coins);
    }

    public void updateCharacterCardsAvailable(List<CharacterCardEnumeration> characterCards){
        characterCards.stream()
                .filter(characterCard -> getView().getModel().getCharacterCardByType(characterCard) == null)
                .forEach(characterCard -> getView().getModel().addCharacterCard(new MockCard(characterCard)));
    }

}