package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.GoldenLancePower;
import AKDsMoreRelics.powers.LongWhipPower;
import AKDsMoreRelics.powers.StunGrenadePower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class LongWhip extends CustomRelic {

    public static final String ID = DefaultMod.makeID("LongWhip");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("LongWhip.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LongWhip.png"));

    public LongWhip() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(m, p, new LongWhipPower(m, p)));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage",
            paramtypez = {DamageInfo.class}
    )
    public static class LongWhipPatch3 {
        public static void Prefix(AbstractMonster mo, DamageInfo info) {
            if (AbstractDungeon.player.hasRelic("AKDsMoreRelics:LongWhip") && mo.currentHealth == mo.maxHealth &&
            info.type == DamageInfo.DamageType.NORMAL) {
                AbstractDungeon.player.getRelic("AKDsMoreRelics:LongWhip").flash();
            }
        }
    }
}
