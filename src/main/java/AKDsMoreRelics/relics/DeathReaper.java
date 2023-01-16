package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.DeathReaperPower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class DeathReaper extends CustomRelic {

    public static final String ID = DefaultMod.makeID("DeathReaper");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DeathReaper.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DeathReaper.png"));

    public DeathReaper() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage",
            paramtypez = {
                    DamageInfo.class
            }
    )
    public static class DeathReaperPatch1 {
        public static void Postfix(AbstractMonster mo, DamageInfo info) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:DeathReaper") &&
                    !mo.hasPower("AKDsMoreRelics:DeathReaperPower") && mo.currentHealth <= mo.maxHealth/2 && mo.currentHealth > 0) {
                AbstractDungeon.player.getRelic("AKDsMoreRelics:DeathReaper").flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player,
                        AbstractDungeon.player.getRelic("AKDsMoreRelics:DeathReaper")));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, null, new DeathReaperPower(mo, null)));
            }
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "heal",
            paramtypez = {
                    int.class
            }
    )
    public static class DeathReaperPatch2 {
        public static void Postfix(AbstractMonster mo, int amt) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:DeathReaper") &&
                    mo.hasPower("AKDsMoreRelics:DeathReaperPower") && mo.currentHealth > mo.maxHealth/2) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mo, mo, "AKDsMoreRelics:DeathReaperPower"));
            }
        }
    }
}
