package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class SpikeShield extends CustomRelic {

    public static final String ID = DefaultMod.makeID("SpikeShield");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpikeShield.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpikeShield.png"));

    public SpikeShield() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "decrementBlock",
            paramtypez = {
                    DamageInfo.class,
                    int.class
            }
    )
    public static class SpikeShieldPatchPatch {
        public static void Prefix(AbstractCreature p, DamageInfo info, int dmg) {
            if (p instanceof AbstractPlayer && info.owner instanceof AbstractMonster &&
                    info.type == DamageInfo.DamageType.NORMAL && p.currentBlock > 0 &&
                    ((AbstractPlayer) p).hasRelic("AKDsMoreRelics:SpikeShield") && dmg > 0) {
                ((AbstractPlayer) p).getRelic("AKDsMoreRelics:SpikeShield").flash();
                dmg = Math.min(dmg, p.currentBlock);
                AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(p, dmg, DamageInfo.DamageType.THORNS)));
            }
        }
    }
}
