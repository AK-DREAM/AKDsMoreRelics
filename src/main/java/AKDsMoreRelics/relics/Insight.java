package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Insight extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Insight");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Insight.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Insight.png"));

    public Insight() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction u) {
        if (card.canUpgrade()) {
            card.upgrade();
            this.flash();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
