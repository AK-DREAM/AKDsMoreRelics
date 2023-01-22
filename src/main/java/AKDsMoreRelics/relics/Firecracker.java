package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.DeathReaperPower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Firecracker extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Firecracker");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Firecracker.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Firecracker.png"));

    public Firecracker() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
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
        @SpireInsertPatch(rloc = 13)
        public static void Insert(AbstractMonster mo, DamageInfo info) {
            if (!AbstractDungeon.player.hasRelic("AKDsMoreRelics:Firecracker")) return;
            if (info.owner != AbstractDungeon.player || info.output < 2 || info.type == DamageInfo.DamageType.HP_LOSS) return;
            int dmg = info.output/2;
            AbstractMonster mo2 = AbstractDungeon.getRandomMonster(mo);
            if (mo2 != null) {
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(mo2,
                        AbstractDungeon.player.getRelic("AKDsMoreRelics:Firecracker")));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo2,
                        new DamageInfo(AbstractDungeon.player, dmg, DamageInfo.DamageType.THORNS),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }
}
