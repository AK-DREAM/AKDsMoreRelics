package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class GoldAnvil extends CustomRelic implements BetterOnSmithRelic {

    public static final String ID = DefaultMod.makeID("GoldAnvil");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldAnvil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldAnvil.png"));

    public GoldAnvil() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void betterOnSmith(AbstractCard card) {
        ArrayList<AbstractCard> possibleCards = new ArrayList();
        AbstractCard theCard = null;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                possibleCards.add(c);
            }
        }
        if (!possibleCards.isEmpty()) {
            theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
            theCard.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(theCard);
            float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(x, y));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy(), x, y));
        }
//        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
//            ArrayList<AbstractCard> canUpgrade;
//            if (c.cardID.equals(card.cardID) && c.canUpgrade()) {
//                this.flash();
//                float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
//                float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
//                c.upgrade();
//                AbstractDungeon.player.bottledCardUpgradeCheck(c);
//                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(x, y));
//                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
//            }
//        }
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
