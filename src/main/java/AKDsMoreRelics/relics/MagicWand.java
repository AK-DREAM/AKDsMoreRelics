package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConservePower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class MagicWand extends CustomRelic {

    public static final String ID = DefaultMod.makeID("MagicWand");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MagicWand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MagicWand.png"));
    private boolean OK = false;
    public MagicWand() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.OK) this.addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 2)));
    }

    @Override
    public void atTurnStart() {
        this.beginPulse();
        this.OK = this.pulse = true;
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.OK = this.pulse = false;
        }
    }

    @Override
    public void onVictory() {
        this.pulse = false;
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
