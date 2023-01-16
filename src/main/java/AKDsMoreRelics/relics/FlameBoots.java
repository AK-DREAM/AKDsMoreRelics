package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class FlameBoots extends CustomRelic {

    public static final String ID = DefaultMod.makeID("FlameBoots");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FlameBoots.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FlameBoots.png"));

    public FlameBoots() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() { this.counter = 0; this.stopPulse(); }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction u) {
        if (this.counter < 6 && ++this.counter == 6) {
            AbstractPlayer p = AbstractDungeon.player;
            this.flash(); this.beginLongPulse();
            this.addToBot(new RelicAboveCreatureAction(p, this));
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 4)));
            this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 4)));
        }
    }
    @Override
    public void onVictory() { this.counter = -1; this.stopPulse(); }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
