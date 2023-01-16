package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.JokerPower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Joker extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Joker");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Joker.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Joker.png"));

    public Joker() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onRefreshHand() {
        if (AbstractDungeon.actionManager.actions.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded && !AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            if (AbstractDungeon.player.hand.size() == 1) {
                if (!p.hasPower("AKDsMoreRelics:JokerPower")) {
                    this.flash();
                    this.addToBot(new RelicAboveCreatureAction(p, this));
                    this.addToBot(new ApplyPowerAction(p, p, new JokerPower(p, p, 1)));
                }
            } else if (p.hasPower("AKDsMoreRelics:JokerPower")) {
                this.addToBot(new RemoveSpecificPowerAction(p, p, "AKDsMoreRelics:JokerPower"));
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
