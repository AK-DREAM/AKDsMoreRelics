package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class HypnoticWatch extends CustomRelic {

    public static final String ID = DefaultMod.makeID("HypnoticWatch");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HypnoticWatch.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HypnoticWatch.png"));
    private boolean OK = false, used = false;
    public HypnoticWatch() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() { this.used = false; }

    @Override
    public void atTurnStart() {
        if (!this.used) {
            this.beginLongPulse();
            this.OK = true;
        }
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.OK = this.pulse = false;
    }

    @Override
    public void onPlayerEndTurn() {
        if (this.OK && !this.used) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                this.addToBot(new StunMonsterAction(mo, AbstractDungeon.player));
            }
            this.stopPulse();
            this.grayscale = this.used = true;
        }
    }

    @Override
    public void onVictory() {
        this.stopPulse();
        this.grayscale = false;
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
