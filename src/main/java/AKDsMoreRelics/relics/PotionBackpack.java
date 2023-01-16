package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class PotionBackpack extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PotionBackpack");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PotionBackpack.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PotionBackpack.png"));

    public PotionBackpack() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = TopPanel.class,
            method = "destroyPotion",
            paramtypez = {
                    int.class
            }
    )
    public static class SellPotionPatch {
        @SpireInsertPatch(
                rloc = 0,
                localvars = {}
        )
        public static void Insert(TopPanel __instance, int slot) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:PotionBackpack") &&
                    !(AbstractDungeon.player.potions.get(slot) instanceof PotionSlot)) {
                AbstractDungeon.player.gainGold(30);
            }
        }
    }
}
