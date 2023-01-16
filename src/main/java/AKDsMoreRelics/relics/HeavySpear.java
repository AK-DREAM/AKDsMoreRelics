package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class HeavySpear extends CustomRelic {

    public static final String ID = DefaultMod.makeID("HeavySpear");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HeavySpear.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HeavySpear.png"));

    public HeavySpear() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmt, AbstractCreature mo) {
        if (info.owner == AbstractDungeon.player && info.type == DamageInfo.DamageType.NORMAL && mo instanceof AbstractMonster && damageAmt >= (mo.maxHealth+1)/2) {
            this.addToTop(new InstantKillAction(mo));
            this.addToTop(new RelicAboveCreatureAction(mo, this));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
