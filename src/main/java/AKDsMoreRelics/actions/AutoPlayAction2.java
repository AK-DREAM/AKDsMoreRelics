
package AKDsMoreRelics.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AutoPlayAction2 extends AbstractGameAction {

    public AutoPlayAction2() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
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
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                if (myCanUse(card) && 0 <= card.costForTurn && card.costForTurn <= 1) {
                    AbstractDungeon.player.drawPile.group.remove(card);
                    AbstractDungeon.getCurrRoom().souls.remove(card);
                    AbstractDungeon.player.limbo.group.add(card);
                    card.current_y = -200.0F * Settings.scale;
                    card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    card.target_y = (float)Settings.HEIGHT / 2.0F;
                    card.targetAngle = 0.0F;
                    card.lighten(false);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.applyPowers();
                    AbstractPlayer p = AbstractDungeon.player;
                    if (card.costForTurn > 0 && !card.freeToPlay() && !card.isInAutoplay && (!p.hasPower("Corruption") || card.type != AbstractCard.CardType.SKILL)) {
                        p.energy.use(card.costForTurn);
                    }
                    this.addToTop(new NewQueueCardAction(card, true, false, true));
                    this.addToTop(new UnlimboAction(card));
                    if (!Settings.FAST_MODE) {
                        this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                    } else {
                        this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                    }
                }
            }
            this.isDone = true;
        }

    }
}
