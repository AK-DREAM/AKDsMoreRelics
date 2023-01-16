package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class HugeHammer extends CustomRelic {

    public static final String ID = DefaultMod.makeID("HugeHammer");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HugeHammer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HugeHammer.png"));

    public HugeHammer() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    private boolean used;

    @Override
    public void atBattleStart() {
        this.used = false;
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        this.counter = this.used?-1:0;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (!this.used && info.owner == AbstractDungeon.player && target instanceof AbstractMonster) {
            this.counter += damageAmount;
        }
        if (this.counter >= 150) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                this.addToBot(new StunMonsterAction(m, AbstractDungeon.player));
            }
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.used = true; this.counter = -1;
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
