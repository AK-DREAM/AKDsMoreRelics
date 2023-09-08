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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.lwjgl.Sys;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class SpeedyHand extends CustomRelic {

    public static final String ID = DefaultMod.makeID("SpeedyHand");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpeedyHand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpeedyHand.png"));

    public SpeedyHand() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public static SpeedyHandButton SHB = new SpeedyHandButton();

    @SpirePatch(clz = CardCrawlGame.class, method = "<ctor>")
    public static class SpeedyHandPatch {
        public static void Raw(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException {
            ClassPool pool = ctBehavior.getDeclaringClass().getClassPool();
            CtClass screen = pool.get(CardRewardScreen.class.getName());
            CtMethod methods[] = screen.getDeclaredMethods();
            for (CtMethod method : methods) {
                method.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().equals(SingingBowlButton.class.getName()) && m.getMethodName().equals("hide")) {
                            m.replace("{" + SpeedyHand.class.getName() + ".SHB.hide();" + "$_ = $proceed($$);" + "}");
                        }
                    }
                });
            }
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "update")
    public static class SpeedyHandPatchPatch2 {
        @SpireInsertPatch( rloc = 37, localvars = {} )
        public static void Insert(CardRewardScreen c) { SHB.update(); }
    }
    @SpirePatch(clz = CardRewardScreen.class, method = "render")
    public static class SpeedyHandPatchPatch4 {
        @SpireInsertPatch( rloc = 5, localvars = {} )
        public static void Insert(CardRewardScreen c, SpriteBatch sb) { SHB.render(sb); }
    }
    @SpirePatch(clz = CardRewardScreen.class, method = "reopen")
    public static class SpeedyHandPatchPatch6 {
        @SpireInsertPatch( rlocs = {19,26}, localvars = {} )
        public static void Insert(CardRewardScreen c) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:SpeedyHand")) {
                SHB.show(c, c.rItem);
            }
        }
    }
    @SpirePatch(clz = CardRewardScreen.class, method = "open")
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
