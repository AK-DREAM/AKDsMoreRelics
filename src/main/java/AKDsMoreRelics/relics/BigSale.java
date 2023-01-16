package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class BigSale extends CustomRelic {

    public static final String ID = DefaultMod.makeID("BigSale");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BigSale.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BigSale.png"));

    public BigSale() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

    @Override
    public void onEnterRoom(AbstractRoom m) {
        if (m instanceof ShopRoom) this.flash();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = ShopScreen.class,
            method = "initPotions",
            paramtypez = {
            }
    )
    public static class SellPotionPatch1 {
        public static void Postfix(ShopScreen __instance) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:BigSale")) {
                ArrayList<StorePotion> tmp = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "potions");
                for (StorePotion p : tmp) p.price = 1;
            }
        }
    }
    @SpirePatch(
            clz = StoreRelic.class,
            method = "purchaseRelic",
            paramtypez = {
            }
    )
    public static class SellPotionPatch2 {
        @SpireInsertPatch(
                rloc = 8,
                localvars = {}
        )
        public static void Insert(StoreRelic __instance) {
            if (__instance.relic.relicId.equals("AKDsMoreRelics:BigSale")) {
                ArrayList<StorePotion> tmp = ReflectionHacks.getPrivate(AbstractDungeon.shopScreen, ShopScreen.class, "potions");
                for (StorePotion p : tmp) p.price = 1;
            }
        }
    }
}
