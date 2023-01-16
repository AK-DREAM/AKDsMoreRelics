package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class DeathCountdown extends CustomRelic {

    public static final String ID = DefaultMod.makeID("DeathCountdown");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DeathCountdown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DeathCountdown.png"));

    public DeathCountdown() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }
    @Override
    public void onUnequip() { --AbstractDungeon.player.energy.energyMaster; }
    @Override
    public void atBattleStart() {
        this.counter = 8;
        // Patch for ReplayTheSpire
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.id.equals("FadingForestBoss")) this.counter = -1;
        }
        // Patch for ReplayTheSpire
    }

    @Override
    public void atTurnStart() {
        if (this.counter > 0) --this.counter;
        if (this.counter == 0) this.beginLongPulse();
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.counter == 0) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(p, this));
            this.addToBot(new VFXAction(new LightningEffect(p.hb.cX, p.hb.cY)));
            this.addToBot(new LoseHPAction(p, p, 99999));
            this.stopPulse(); this.counter = -1; this.grayscale = true;
        }

    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
        this.stopPulse();
    }

    public boolean canSpawn() { return AbstractDungeon.floorNum >= 16; }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
