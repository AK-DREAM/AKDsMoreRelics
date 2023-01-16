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
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class GhostCloak extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GhostCloak");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GhostCloak.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GhostCloak.png"));

    public GhostCloak() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.counter = 2;
    }

    @Override
    public int onAttacked(DamageInfo info, int dmg) {
        if (dmg > 0 && info.type == DamageInfo.DamageType.NORMAL && this.counter != -1) {
            if ((--this.counter) == 0) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.counter = -1;
                AbstractPlayer p = AbstractDungeon.player;
                this.addToTop(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
            }
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
