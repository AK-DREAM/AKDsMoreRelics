package AKDsMoreRelics.util;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MagicEightModifier extends AbstractCardModifier {
    public MagicEightModifier() {

    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.freeToPlayOnce = true;
    }
    @Override
    public void onRemove(AbstractCard card) {
        card.freeToPlayOnce = false;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) { return true; }
    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) { return true; }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PowerCrystalModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "AKDsMoreRelics:MagicEightModifier";
    }
}
