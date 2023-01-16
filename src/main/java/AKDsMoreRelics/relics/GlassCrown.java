package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.*;

public class GlassCrown extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GlassCrown");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GlassCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GlassCrown.png"));

    public GlassCrown() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    public boolean ok;

    @Override
    public void atBattleStart() { this.ok = true; this.beginLongPulse(); }

    @Override
    public int onAttacked(DamageInfo info, int dmg) {
        if (dmg > 0 && info.type == DamageInfo.DamageType.NORMAL) {
            this.grayscale = true;
            this.ok = false;
            this.stopPulse();
        }
        return dmg;
    }

    public void UpgradeRandomCard() {
        logger.info("OK");
        ArrayList<AbstractCard> possibleCards = new ArrayList();
        AbstractCard theCard = null;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) possibleCards.add(c);
        }
        if (!possibleCards.isEmpty()) {
            theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
            theCard.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(theCard);
        }
        if (theCard != null) {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }

    @Override
    public void onVictory() {
        if (this.ok) {
            this.flash();
            AbstractDungeon.getCurrRoom().addGoldToRewards(30);
            UpgradeRandomCard();
            AbstractDungeon.player.increaseMaxHp(5, true);
        }
        this.grayscale = false;
        this.stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
