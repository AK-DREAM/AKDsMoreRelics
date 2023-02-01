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
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class FangNecklace extends CustomRelic {

    public static final String ID = DefaultMod.makeID("FangNecklace");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FangNecklace.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FangNecklace.png"));

    public FangNecklace() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }
    @Override
    public void onUnequip() { --AbstractDungeon.player.energy.energyMaster; }

    @Override
    public int onAttacked(DamageInfo info, int dmg) {
        AbstractPlayer p = AbstractDungeon.player;
        if (dmg > 0 && info.type == DamageInfo.DamageType.NORMAL && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            this.addToBot(new ApplyPowerAction(p, p, new PoisonPower(p, p, 1)));
        }
        return dmg;
    }

    @Override
    public void onVictory() { this.counter = -1; }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
