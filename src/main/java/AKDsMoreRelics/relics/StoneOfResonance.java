package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.StunGrenadePower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class StoneOfResonance extends CustomRelic {

    public static final String ID = DefaultMod.makeID("StoneOfResonance");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StoneOfResonance.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StoneOfResonance.png"));

    public StoneOfResonance() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction u) {
        List<AbstractCard> sameCard = new ArrayList<>();
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.discardPile.group) if (card.cardID.equals(c.cardID)) sameCard.add(c);
        for (AbstractCard c : p.hand.group) if (card.cardID.equals(c.cardID)) sameCard.add(c);
        for (AbstractCard c : p.drawPile.group) if (card.cardID.equals(c.cardID)) sameCard.add(c);
        if (sameCard.size() > 1) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(p, this));
            u.exhaustCard = true;
            sameCard.stream().filter(c -> c != card).forEach(
                    c -> {
                        AbstractCard c2 = c.makeStatEquivalentCopy();
                        c2.current_x = c.current_x; c2.target_x = c.target_x;
                        c2.current_y = c.current_y; c2.target_y = c.target_y;
                        c2.cardID += "StoneOfResonance";
                        c2.exhaust = true;
                        this.addToTop(new NewQueueCardAction(c2, u.target, false, true));
                    }
            );
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
