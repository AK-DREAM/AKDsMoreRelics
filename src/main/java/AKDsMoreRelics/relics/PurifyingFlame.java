package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class PurifyingFlame extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PurifyingFlame");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PurifyingFlame.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PurifyingFlame.png"));

    public PurifyingFlame() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = CampfireSleepEffect.class,
            method = "update",
            paramtypez = {
            }
    )
    public static class PurifyingFlamePatch {
        @SpireInsertPatch(
                rloc = 21,
                localvars = {}
        )
        public static void Insert(CampfireSleepEffect __instance) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:PurifyingFlame")) {
                ArrayList<AbstractCard> canRemove = new ArrayList<>();
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.type == AbstractCard.CardType.CURSE && (!c.cardID.equals("Necronomicurse") && !c.cardID.equals("CurseOfTheBell") && !c.cardID.equals("AscendersBane"))) {
                        canRemove.add(c);
                    }
                }
                if (canRemove.size() > 0) {
                    AbstractCard rmv = canRemove.get(AbstractDungeon.cardRandomRng.random(canRemove.size()-1));
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(rmv, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                    AbstractDungeon.player.masterDeck.removeCard(rmv);
                }
            }
        }
    }
}
