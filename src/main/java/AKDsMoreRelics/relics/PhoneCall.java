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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper.RoomResult;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.lwjgl.Sys;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class PhoneCall extends CustomRelic implements ClickableRelic {

    public static final String ID = DefaultMod.makeID("PhoneCall");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PhoneCall.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PhoneCall.png"));

    public PhoneCall() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
        this.counter = 0;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.gainGold(200);
    }

    @Override
    public void onRightClick() {
        if (this.counter == 0) {
            this.counter = 1;
            this.beginLongPulse();
        } else {
            this.counter = 0;
            this.stopPulse();
        }
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = com.megacrit.cardcrawl.helpers.EventHelper.class,
            method = "roll",
            paramtypez = { com.megacrit.cardcrawl.random.Random.class }
    )
    public static class PhoneCallPatch {
        public static SpireReturn<RoomResult> Prefix(com.megacrit.cardcrawl.random.Random eventRng) {
            System.out.println("PhoneCall");
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:PhoneCall")) {
                AbstractRelic r = AbstractDungeon.player.getRelic("AKDsMoreRelics:PhoneCall");
                if (r.counter == 1) {
                    r.counter = 0;
                    r.stopPulse();
                    r.flash();
                    return SpireReturn.Return(RoomResult.SHOP);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
