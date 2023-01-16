package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Vintage extends CustomRelic implements CustomSavable<ArrayList<Integer>> {

    public static final String ID = DefaultMod.makeID("Vintage");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Vintage.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Vintage.png"));

    public static ArrayList<Integer> cntOfCombat;
    public static final String[] DES = CardCrawlGame.languagePack.getRelicStrings(ID).DESCRIPTIONS;

    public Vintage() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public static void updPotionDes(AbstractPotion po) {
        if (0 <= po.slot && po.slot < cntOfCombat.size() && po.tips.size() > 0) {
            po.tips.get(0).body += " NL NL " + DES[1] + cntOfCombat.get(po.slot) + DES[2];
            if (cntOfCombat.get(po.slot) >= 5) po.tips.get(0).body += DES[3];
        }
    }

    @Override
    public void onEquip() {
        cntOfCombat = new ArrayList<>();
        for (int i = 0; i < AbstractDungeon.player.potionSlots; i++) {
            cntOfCombat.add(0);
            updPotionDes(AbstractDungeon.player.potions.get(i));
        }
    }

    @Override
    public ArrayList<Integer> onSave() { return cntOfCombat; }

    @Override
    public void onLoad(ArrayList<Integer> load) {
        cntOfCombat = load;
        for (AbstractPotion po : AbstractDungeon.player.potions) {
            po.initializeData();
            updPotionDes(po);
        }
    }

    @Override
    public void onVictory() {
        for (int i = 0; i < cntOfCombat.size(); i++) {
            cntOfCombat.set(i, cntOfCombat.get(i)+1);
        }
        for (AbstractPotion po : AbstractDungeon.player.potions) {
            po.initializeData();
            updPotionDes(po);
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractPotion.class,
            method = "getPotency",
            paramtypez = {
            }
    )
    public static class VintagePatch1 {
        public static int Postfix(int potency, AbstractPotion po) {
            if (AbstractDungeon.player != null && !AbstractDungeon.player.hasRelic("SacredBark")
                    && AbstractDungeon.player.hasRelic(ID)) {
                if (0 <= po.slot && po.slot < cntOfCombat.size() && Vintage.cntOfCombat.get(po.slot) >= 5) {
                    potency *= 2;
                }
            }
            return potency;
        }
    }

    @SpirePatch(
            clz = AbstractPotion.class,
            method = "setAsObtained",
            paramtypez = {
                    int.class
            }
    )
    public static class VintagePatch2 {
        public static void PostFix(AbstractPotion po, int slot) {
            if (AbstractDungeon.player.hasRelic(ID)) {
                while (cntOfCombat.size() <= slot) cntOfCombat.add(0);
                cntOfCombat.set(slot, 0);
                updPotionDes(po);
            }
        }
    }

}
