package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class SoulAbsorber extends CustomRelic {

    public static final String ID = DefaultMod.makeID("SoulAbsorber");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SoulAbsorber.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SoulAbsorber.png"));

    public SoulAbsorber() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

   static public boolean checkDarklings(AbstractMonster mo) {
        if (!mo.id.equals("Darkling")) return false;
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m == mo) continue;
            if (m.id.equals("Darkling") && !m.halfDead) return true;
        }
        return false;
    }
    static public boolean infoOK(DamageInfo info) {
        return info.owner != AbstractDungeon.player || info.type != DamageInfo.DamageType.NORMAL;
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage",
            paramtypez = {
                    DamageInfo.class
            }
    )
    public static class SoulAbsorberPatch {
        @SpireInsertPatch(rloc=94)
        public static void Insert(AbstractMonster m, DamageInfo info) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:SoulAbsorber") && infoOK(info)
            && !m.hasPower("Minion") && !checkDarklings(m)) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractRelic r = p.getRelic("AKDsMoreRelics:SoulAbsorber");
                r.flash(); AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, r));
                p.increaseMaxHp(2, true);
            }
        }
    }
}
