package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class AED extends CustomRelic {

    public static final String ID = DefaultMod.makeID("AED");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AED.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("AED.png"));

    public AED() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public void onTrigger() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth <= 3 && p.currentHealth > 0) {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            p.heal(p.maxHealth-p.currentHealth);
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "endBattle",
            paramtypez = {
            }
    )
    public static class AEDPatch {
        public static void Prefix(AbstractRoom __instance) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:AED")) {
                AbstractDungeon.player.getRelic("AKDsMoreRelics:AED").onTrigger();
            }
        }
    }
}
