package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class DustOfLife extends CustomRelic {

    public static final String ID = DefaultMod.makeID("DustOfLife");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DustOfLife.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DustOfLife.png"));

    public DustOfLife() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.color == AbstractCard.CardColor.COLORLESS &&
                (card.rarity == AbstractCard.CardRarity.UNCOMMON || card.rarity == AbstractCard.CardRarity.RARE)) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 3));
            action.exhaustCard = true;
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
