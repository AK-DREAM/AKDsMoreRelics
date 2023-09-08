package AKDsMoreRelics.actions;

import AKDsMoreRelics.DefaultMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AutoPlayAction extends AbstractGameAction {
    AbstractPlayer p = AbstractDungeon.player;

    public AutoPlayAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
    }

    public boolean myCanUse(AbstractCard c) {
        if (c.type == AbstractCard.CardType.STATUS && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit")) {
            return false;
        } else if (c.type == AbstractCard.CardType.CURSE && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle")) {
            return false;
        } else {
            return c.hasEnoughEnergy();
        }
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 0) {
                this.isDone = true; return;
            }
            AbstractCard card = AbstractDungeon.player.hand.group.get(AbstractDungeon.player.hand.group.size() - 1);
            int prevCost = card.costForTurn;
            if (card.costForTurn > 0) --card.costForTurn;
            if (myCanUse(card)) {
                card.targetAngle = 0.0F;
                if (card.costForTurn > 0 && !card.freeToPlay() && !card.isInAutoplay && (!p.hasPower("Corruption") || card.type != AbstractCard.CardType.SKILL)) {
                    p.energy.use(card.costForTurn);
                }
                card.dontTriggerOnUseCard = true;
                this.addToTop(new NewQueueCardAction(card, true, false, true));
                AbstractDungeon.player.hand.removeCard(card);
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            card.costForTurn = prevCost;
        }
        this.tickDuration();
    }
}
