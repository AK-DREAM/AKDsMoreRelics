package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.EntropicBrew;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class OakBarrel extends CustomRelic {

    public static final String ID = DefaultMod.makeID("OakBarrel");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("OakBarrel.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("OakBarrel.png"));

    public OakBarrel() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractPlayer P = AbstractDungeon.player;
        P.potionSlots += 1;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "addPotionToRewards",
            paramtypez = {
            }
    )
    public static class StirringRodPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractDungeon.class.getName()) && m.getMethodName().equals("returnRandomPotion")) {
                        m.replace("{ " +
                                "if (" + AbstractDungeon.class.getName()+".player.hasRelic(\"AKDsMoreRelics:OakBarrel\")) $_="+ OakBarrel.StirringRodPatch.class.getName() + ".returnEntropicBrew(); " +
                                "else $_=$proceed($$); " +
                                "}");
                    }
                }
            };
        }
        public static AbstractPotion returnEntropicBrew() {
            return new EntropicBrew();
        }
    }
}
