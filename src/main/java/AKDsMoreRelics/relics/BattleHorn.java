package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class BattleHorn extends CustomRelic {

    public static final String ID = DefaultMod.makeID("BattleHorn");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BattleHorn.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BattleHorn.png"));

    public BattleHorn() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= 2 && card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new GainEnergyAction(1));
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
