package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.PowerCrystalModifier;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class OverclockedChip extends CustomRelic {

    public static final String ID = DefaultMod.makeID("OverclockedChip");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("OverclockedChip.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("OverclockedChip.png"));

    public OverclockedChip() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }
    @Override
    public void onUnequip() { --AbstractDungeon.player.energy.energyMaster; }

    @Override
    public void atBattleStartPreDraw() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cost >= 2 && !c.isEthereal && !CardModifierManager.hasModifier(c, "basemod:EtherealCardModifier")) {
                CardModifierManager.addModifier(c, new EtherealMod());
            }
        }
    }
    @Override
    public void onDrawOrDiscard() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cost >= 2 && !c.isEthereal && !CardModifierManager.hasModifier(c, "basemod:EtherealCardModifier")) {
                CardModifierManager.addModifier(c, new EtherealMod());
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
