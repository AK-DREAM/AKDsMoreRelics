package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.GoldenLancePower;
import AKDsMoreRelics.util.MagicEightModifier;
import AKDsMoreRelics.util.PowerCrystalModifier;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import javax.smartcardio.Card;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class MagicEight extends CustomRelic {

    public static final String ID = DefaultMod.makeID("MagicEight");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MagicEight.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MagicEight.png"));

    public MagicEight() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onRefreshHand() {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.actionManager.actions.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded && !AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (AbstractCard card : p.hand.group) {
                CardModifierManager.removeModifiersById(card, "AKDsMoreRelics:MagicEightModifier", false);
            }
            if (p.hand.size() >= 8) {
                this.flash();
                AbstractCard card = p.hand.group.get(7);
                CardModifierManager.addModifier(card, new MagicEightModifier());
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
