package AKDsMoreRelics.powers;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.relics.HeartChain;
import AKDsMoreRelics.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HeartChainPower extends AbstractPower implements HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("HeartChainPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.

    public HeartChainPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("shackle");

        updateDescription();
    }

    @Override
    public int getHealthBarAmount() { return this.amount; }

    @Override
    public Color getColor() { return Color.GRAY.cpy(); }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new DamageAction(this.owner, new DamageInfo(HeartChain.virtualMonster, this.amount, DamageInfo.DamageType.HP_LOSS)));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
