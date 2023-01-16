package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class DonuAndDeca extends CustomRelic {

    public static final String ID = DefaultMod.makeID("DonuAndDeca");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DonuAndDeca.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DonuAndDeca.png"));

    public DonuAndDeca() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() { ++this.counter; }

    @Override
    public void atTurnStartPostDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (this.counter % 2 == 1) this.addToBot(new GainEnergyAction(1));
        else this.addToBot(new DrawCardAction(1));
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
