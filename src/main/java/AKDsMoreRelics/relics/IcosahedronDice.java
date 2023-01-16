package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.lang.annotation.Documented;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class IcosahedronDice extends CustomRelic {

    public static final String ID = DefaultMod.makeID("IcosahedronDice");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("IcosahedronDice.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("IcosahedronDice.png"));

    public IcosahedronDice() { super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK); }

    @Override
    public void onEquip() { this.counter = 0; }
    @Override
    public void atTurnStart() {
        if (this.counter != -2 && this.counter < 20) {
            ++this.counter;
            if (this.counter == 20) this.beginLongPulse();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory",
            paramtypez = {
            }
    )
    public static class IcosahedronDicePatchPatch {
        public static void Postfix(AbstractPlayer p) {
            if (p.hasRelic("AKDsMoreRelics:IcosahedronDice")) {
                AbstractRelic dice = p.getRelic("AKDsMoreRelics:IcosahedronDice");
                if (dice.counter >= 20) {
                    dice.stopPulse();
                    dice.flash(); dice.usedUp(); dice.counter = -2;
                    AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);
                }
            }
        }
    }
}
