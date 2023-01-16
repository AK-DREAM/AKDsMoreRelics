package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Garlic extends CustomRelic implements OnReceivePowerRelic {

    public static final String ID = DefaultMod.makeID("Garlic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Garlic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Garlic.png"));
    public Garlic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    private int turns;
    @Override
    public void atBattleStart() {
        this.turns = 0; this.beginLongPulse();
    }
    @Override
    public void atTurnStart() {
        ++this.turns;
        if (this.turns > 1) this.stopPulse();
    }
    @Override
    public void onVictory() { this.stopPulse(); }

    @Override
    public boolean onReceivePower(AbstractPower p, AbstractCreature c) {
        if (this.turns == 1 && p.type == AbstractPower.PowerType.DEBUFF) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return false;
        } else return true;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
