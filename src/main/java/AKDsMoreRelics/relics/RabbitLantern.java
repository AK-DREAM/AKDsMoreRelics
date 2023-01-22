package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class RabbitLantern extends CustomRelic {

    public static final String ID = DefaultMod.makeID("RabbitLantern");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RabbitLantern.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RabbitLantern.png"));

    public RabbitLantern() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public int onPlayerGainedBlock(float _blk) {
        int blk = MathUtils.floor(_blk);
        if (blk > 0) {
            this.addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));
            return blk-1;
        } else return blk;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
