package AKDsMoreRelics.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class PowerCrystalModifier extends AbstractCardModifier {
    public PowerCrystalModifier() {

    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PowerCrystalModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return "AKDsMoreRelics:PowerCrystalModifier";
    }
}
