package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.RegenPower;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class UnknownPotion extends CustomRelic {

    public static final String ID = DefaultMod.makeID("UnknownPotion");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("UnknownPotion.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("UnknownPotion.png"));

    public UnknownPotion() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }
    @Override
    public void onUnequip() { --AbstractDungeon.player.energy.energyMaster; }

    @Override
    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
//        if (p.currentHealth > p.maxHealth/2) {
//            this.flash();
//            this.addToBot(new RelicAboveCreatureAction(p, this));
//            this.addToBot(new ApplyPowerAction(p, p, new PoisonPower(p, p, 2)));
//        }

        if (AbstractDungeon.miscRng.random(9) <= 2) {
            this.addToBot(new ApplyPowerAction(p, p, new RegenPower(p, 2)));
        }
        if (AbstractDungeon.miscRng.random(9) <= 5) {
            this.addToBot(new ApplyPowerAction(p, p, new PoisonPower(p, p, 2)));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
