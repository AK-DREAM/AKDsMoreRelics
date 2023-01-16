package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.SpeedyHandButton;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class SpeedyHand extends CustomRelic {

    public static final String ID = DefaultMod.makeID("SpeedyHand");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpeedyHand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpeedyHand.png"));

    public SpeedyHand() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public static SpeedyHandButton SHB = new SpeedyHandButton();
    // 1
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "update",
            paramtypez = {}
    )
    public static class SpeedyHandPatchPatch1 {
        @SpireInsertPatch( rloc = 10, localvars = {} )
        public static void Insert(CardRewardScreen c) { SHB.hide(); }
    }
    // 2
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "update",
            paramtypez = {}
    )
    public static class SpeedyHandPatchPatch2 {
        @SpireInsertPatch( rloc = 37, localvars = {} )
        public static void Insert(CardRewardScreen c) { SHB.update(); }
    }
    // 3
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "cardSelectUpdate",
            paramtypez = {}
    )
    public static class SpeedyHandPatchPatch3 {
        @SpireInsertPatch( rloc = 32, localvars = {} )
        public static void Insert(CardRewardScreen c) { SHB.hide(); }
    }
    // 4
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class SpeedyHandPatchPatch4 {
        @SpireInsertPatch( rloc = 5, localvars = {} )
        public static void Insert(CardRewardScreen c, SpriteBatch sb) { SHB.render(sb); }
    }
    // 5
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "reopen",
            paramtypez = {}
    )
    public static class SpeedyHandPatchPatch5 {
        public static void Prefix(CardRewardScreen c) { SHB.hide(); }
    }
    // 6
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "reopen",
            paramtypez = {}
    )
    public static class SpeedyHandPatchPatch6 {
        @SpireInsertPatch( rlocs = {19,26}, localvars = {} )
        public static void Insert(CardRewardScreen c) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:SpeedyHand")) {
                SHB.show(c, c.rItem);
            }
        }
    }
    // 7
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "open",
            paramtypez = {
                    ArrayList.class,
                    RewardItem.class,
                    String.class
            }
    )
    public static class SpeedyHandPatchPatch7 {
        @SpireInsertPatch( rloc = 19, localvars = {} )
        public static void Insert(CardRewardScreen c, ArrayList<AbstractCard> a1, RewardItem a2, String a3) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:SpeedyHand")) {
                SHB.nowT = 0.0F;
                SHB.show(c, a2);
            }
        }
    }
}
