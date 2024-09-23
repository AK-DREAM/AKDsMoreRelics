package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class HolographicShield extends CustomRelic {

    public static final String ID = DefaultMod.makeID("HolographicShield");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HolographicShield.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HolographicShield.png"));

    public HolographicShield() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    public void atBattleStart() {
        this.counter = 1;
    }

    public void atTurnStart() {
        this.counter += 1;
        this.flash();
        this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.counter));
    }

    public void onVictory() {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
