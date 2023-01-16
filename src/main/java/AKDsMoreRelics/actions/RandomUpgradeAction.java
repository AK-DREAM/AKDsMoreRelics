package AKDsMoreRelics.actions;

import AKDsMoreRelics.DefaultMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class RandomUpgradeAction extends AbstractGameAction {
    private AbstractCard theCard = null;

    public RandomUpgradeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null) {
            ArrayList<AbstractCard> possibleCards = new ArrayList();
            Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                if (c.canUpgrade()) {
                    possibleCards.add(c);
                }
            }

            if (!possibleCards.isEmpty()) {
                this.theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                this.theCard.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(this.theCard);
            }
            DefaultMod.logger.info(theCard.cardID);
        }

        this.tickDuration();
        if (this.isDone && this.theCard != null) {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(this.theCard.makeStatEquivalentCopy()));
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }

    }
}
