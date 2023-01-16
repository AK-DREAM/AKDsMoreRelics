package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.GoldenLancePower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class GoldenLance extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GoldenLance");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldenLance.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldenLance.png"));

    public GoldenLance() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    public boolean onlyAttack() {
        if (AbstractDungeon.player.hand.group.isEmpty()) return false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type != AbstractCard.CardType.ATTACK) return false;
        }
        return true;
    }


    @Override
    public void onRefreshHand() {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.actionManager.actions.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded && !AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && onlyAttack()) {
            if (!p.hasPower("AKDsMoreRelics:GoldenLancePower")) {
                this.flash();
                this.addToBot(new ApplyPowerAction(p, p, new GoldenLancePower(p, p)));
                this.beginLongPulse();
            }
        } else {
            if (p.hasPower("AKDsMoreRelics:GoldenLancePower")) {
                this.addToBot(new RemoveSpecificPowerAction(p, p, "AKDsMoreRelics:GoldenLancePower"));
                this.stopPulse();
            }
        }
    }

    @Override
    public void onVictory() {
        this.stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
