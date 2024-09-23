package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.actions.QuillAction;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class PrimaryCombatManual extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PrimaryCombatManual");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PrimaryCombatManual.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PrimaryCombatManual.png"));

    public PrimaryCombatManual() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        return (c.rarity == AbstractCard.CardRarity.BASIC || c.rarity == AbstractCard.CardRarity.COMMON) ? damage + 2.0F : damage;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
