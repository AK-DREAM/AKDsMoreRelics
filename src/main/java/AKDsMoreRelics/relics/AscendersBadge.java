package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class AscendersBadge extends CustomRelic {

    public static final String ID = DefaultMod.makeID("AscendersBadge");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AscendersBadge.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("AscendersBadge.png"));

    public AscendersBadge() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.gainGold(50);
        AbstractCard rmv = null;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals("AscendersBane")) rmv = c;
        }
        if (rmv != null) {
            this.flash();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(rmv, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(rmv);
        }
    }

    @Override
    public boolean canSpawn() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals("AscendersBane")) return true;
        }
        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
