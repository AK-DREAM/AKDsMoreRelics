package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.actions.AutoPlayAction;
import AKDsMoreRelics.actions.AutoPlayAction2;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class AutobotHelper extends CustomRelic {

    public static final String ID = DefaultMod.makeID("AutobotHelper");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AutobotHelper.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("AutobotHelper.png"));

    public AutobotHelper() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void onEquip() { ++AbstractDungeon.player.energy.energyMaster; }
    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void atTurnStart() {
        --this.counter;
        if (this.counter == 0) this.beginLongPulse();
    }

    @Override
    public void atTurnStartPostDraw() {
        this.addToBot(new AutoPlayAction());
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
