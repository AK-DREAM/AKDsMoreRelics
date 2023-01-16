package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Wrench extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Wrench");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Wrench.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Wrench.png"));

    public Wrench() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        flash();
        this.addToBot(new ArmamentsAction(true));
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
