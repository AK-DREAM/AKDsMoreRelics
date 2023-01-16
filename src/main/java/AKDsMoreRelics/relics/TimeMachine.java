package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class TimeMachine extends CustomRelic implements ClickableRelic {

    public static final String ID = DefaultMod.makeID("TimeMachine");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TimeMachine.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TimeMachine.png"));

    public TimeMachine() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
        this.counter = -1;
    }

    @Override
    public void onRightClick() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.counter == -1) {
            this.flash();
            this.counter = p.currentHealth;
        } else if (this.counter > 0) {
            this.flash();
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            this.addToBot(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
            p.currentHealth = Math.min(this.counter, p.maxHealth);
            p.healthBarUpdatedEvent();
            this.counter = -2;
            this.usedUp();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
}
