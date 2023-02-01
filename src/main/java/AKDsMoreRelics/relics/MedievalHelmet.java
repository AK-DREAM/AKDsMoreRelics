package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.HeartChainPower;
import AKDsMoreRelics.powers.MedievalHelmetPower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.EntropicBrew;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class MedievalHelmet extends CustomRelic implements ClickableRelic {

    public static final String ID = DefaultMod.makeID("MedievalHelmet");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MedievalHelmet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MedievalHelmet.png"));

    public MedievalHelmet() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() { this.counter = 0; }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.overlayMenu.endTurnButton.enabled) {
            AbstractPlayer p = AbstractDungeon.player;
            if (this.counter == 0) {
                this.counter = 1;
                this.addToBot(new ApplyPowerAction(p, p, new MedievalHelmetPower(p, p)));
            } else {
                this.counter = 0;
                this.addToBot(new RemoveSpecificPowerAction(p, p, "AKDsMoreRelics:MedievalHelmetPower"));
            }
        }
    }

    @Override
    public void onVictory() { this.counter = -1; }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage",
            paramtypez = { DamageInfo.class }
    )
    public static class MedievalHelmetPatch {
        @SpireInsertPatch( locator = Locator.class )
        public static void Insert(AbstractMonster __instance, DamageInfo info) {
            if (AbstractDungeon.player.hasPower("AKDsMoreRelics:MedievalHelmetPower") && info.type == DamageInfo.DamageType.NORMAL) {
                AbstractDungeon.actionManager.addToBottom(
                        new GainBlockAction(AbstractDungeon.player, __instance.lastDamageTaken));
            }

        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "lastDamageTaken");
                return new int[]{LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher)[0]+1};
            }
        }
    }
}
