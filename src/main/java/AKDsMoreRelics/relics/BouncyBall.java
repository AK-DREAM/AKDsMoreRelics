package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class BouncyBall extends CustomRelic {

    public static final String ID = DefaultMod.makeID("BouncyBall");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BouncyBall.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BouncyBall.png"));

    public BouncyBall() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = DrawCardAction.class,
            method = "update",
            paramtypez = {}
    )
    public static class ReinforcedBalloonPatch {
        @SpireInsertPatch(
                rlocs = {31,41},
                localvars = {}
        )
        public static void Insert(DrawCardAction _) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.hasRelic(ID)) {
                AbstractRelic r = p.getRelic(ID);
                int amt = _.amount+p.hand.size()-BaseMod.MAX_HAND_SIZE;
                r.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, r));
                for (int i = 1; i <= amt; i++) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, 4, Settings.FAST_MODE));
                }
            }
        }
    }
}
