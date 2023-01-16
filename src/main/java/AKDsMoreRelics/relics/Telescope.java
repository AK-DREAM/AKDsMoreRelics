package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Telescope extends CustomRelic implements ClickableRelic {

    public static final String ID = DefaultMod.makeID("Telescope");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Telescope.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Telescope.png"));

    public Telescope() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    public boolean used;

    @Override
    public void atBattleStart() { this.used = false; }

    @Override
    public void onRightClick() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!this.used && p.drawPile.size() > 0 && AbstractDungeon.overlayMenu.endTurnButton.enabled) {
            this.used = true;
            this.flash();
            this.grayscale = true;
            this.addToBot(new ScryAction(5));
        }
    }

    @Override
    public void onVictory() { this.grayscale = false; }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
