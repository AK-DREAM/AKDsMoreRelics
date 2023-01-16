package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class TowerShield extends CustomRelic {

    public static final String ID = DefaultMod.makeID("TowerShield");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TowerShield.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TowerShield.png"));

    public TowerShield() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "addBlock",
            paramtypez = {
                    int.class
            }
    )
    public static class TowerShieldPatchPatch {
        @SpireInsertPatch(
                rloc = 20,
                localvars = {"tmp"}
        )
        public static void Insert(AbstractCreature __instance, int blockAmt, float tmp) {
            if (__instance instanceof AbstractMonster && AbstractDungeon.player.hasRelic("AKDsMoreRelics:TowerShield")) {
                AbstractDungeon.player.getRelic("AKDsMoreRelics:TowerShield").flash();
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p,
                        AbstractDungeon.player.getRelic("AKDsMoreRelics:TowerShield")));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, MathUtils.floor(tmp))));
            }
        }
    }
}
