package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class RuneOfHorror extends CustomRelic {

    public static final String ID = DefaultMod.makeID("RuneOfHorror");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RuneOfHorror.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RuneOfHorror.png"));

    public RuneOfHorror() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onPlayerEndTurn() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.currentHealth > 0 && m.getIntentBaseDmg() < 0) {
                this.addToBot(new RelicAboveCreatureAction(m, this));
                this.addToBot(new FastShakeAction(m, 0.5F, 0.5F));
                this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -1), -1));
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
