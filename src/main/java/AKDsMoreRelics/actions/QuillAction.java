package AKDsMoreRelics.actions;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class QuillAction extends AbstractGameAction {
    private static final RelicStrings relicStrings;
    public static final String[] TEXT;

    public AbstractPlayer p;
    public ArrayList<AbstractCard> notPower = new ArrayList<>();

    public QuillAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : p.hand.group) {
                if (c.type != AbstractCard.CardType.POWER) {
                    notPower.add(c);
                }
            }
            if (notPower.size() == p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            p.hand.group.removeAll(notPower);
            AbstractDungeon.handCardSelectScreen.open(TEXT[1], this.amount, false, true, false, false, true);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (!c.isEthereal) {
                    c.retain = true;
                    c.modifyCostForCombat(-1);
                }
                p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    public void returnCards() {
        for (AbstractCard c : notPower) {
            p.hand.addToTop(c);
        }
        p.hand.refreshHandLayout();
    }

    static {
        relicStrings = CardCrawlGame.languagePack.getRelicStrings("AKDsMoreRelics:Quill");
        TEXT = relicStrings.DESCRIPTIONS;
    }
}
