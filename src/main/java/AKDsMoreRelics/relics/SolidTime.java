package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class SolidTime extends CustomRelic implements CustomSavable<List<String> > {

    public static final String ID = DefaultMod.makeID("SolidTime");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SolidTime.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SolidTime.png"));

    public SolidTime() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    public final int MAX_HP_DECREASE = 6;
    public List<AbstractCard> cards = new ArrayList<>();
    static Logger logger = LogManager.getLogger("test");

    public static AbstractCard getCardFromLibrary(String cardID) {
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (c.getKey().equals(cardID)) {
                return c.getValue();
            }
        }
        return null;
    }

    @Override
    public List<String> onSave() {
        List<String> retVal = new ArrayList<>();
        for (AbstractCard c : cards) {
            String s = (c.upgraded?1:0)+c.cardID;
            retVal.add(s);
        }
        return retVal;
    }

    @Override
    public void onLoad(List<String> list) {
        cards = new ArrayList<>();
        for (String s : list) {
            String cardID = s.substring(1);
            AbstractCard c = getCardFromLibrary(cardID);
            if (c != null) {
                if (s.charAt(0) == '1' && c.canUpgrade()) c.upgrade();
                cards.add(c);
            }
        }
        this.updateDescription();
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        for (AbstractCard c : cards) {
            logger.info("AKDsMoreRelics:SolidTime use "+c.cardID+"!!!!!!!!!!!!!!!!!");
            c.use(AbstractDungeon.player, AbstractDungeon.getRandomMonster());
        }
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction u) {
        if (c.type == AbstractCard.CardType.POWER) {
            AbstractCard rmv = null;
            for (AbstractCard c2 : AbstractDungeon.player.masterDeck.group) {
                if (c.uuid.equals(c2.uuid)) {
                    rmv = c2; break;
                }
            }
            if (rmv != null && AbstractDungeon.player.maxHealth > MAX_HP_DECREASE) {
                this.flash();
                AbstractPlayer p = AbstractDungeon.player;
                p.decreaseMaxHealth(MAX_HP_DECREASE);
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(p.hb.cX, p.hb.cY, AbstractGameAction.AttackEffect.FIRE));
                cards.add(rmv);
                AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(rmv, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(rmv);
                this.updateDescription();
            }
        }
    }

    public void updateDescription() {
        this.description = this.getUpdatedDescription2();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.getUpdatedDescription2()));
        this.initializeTips();
    }

    public String getUpdatedDescription2() {
        if (cards.isEmpty()) return DESCRIPTIONS[0];
        StringBuilder ret = new StringBuilder(DESCRIPTIONS[0] + DESCRIPTIONS[1]);
        for (AbstractCard c : cards) {
            ret.append(" #y").append(c.name).append(" ,");
        }
        ret.deleteCharAt(ret.length()-1);
        return ret.toString();
    }

    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

}
