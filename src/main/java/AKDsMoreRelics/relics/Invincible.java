package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Invincible extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Invincible");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Invincible.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Invincible.png"));

    public boolean isEliteOrBoss;

    public Invincible() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.isEliteOrBoss = AbstractDungeon.getCurrRoom().eliteTrigger;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                isEliteOrBoss = true;
            }
        }
    }

    public void atTurnStart() {
        if (this.isEliteOrBoss) this.counter = 30;
        else this.counter = 20;
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (this.counter != -1 && damageAmount > this.counter) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            damageAmount = this.counter;
        }
        return damageAmount;
    }
    @Override
    public void onLoseHp(int dmg) {
        if (this.counter != -1) {
            this.counter = Math.max(0, this.counter-dmg);
        }
    }

    public void onVictory() { this.counter = -1; }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
