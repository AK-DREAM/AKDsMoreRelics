package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class GoldMagnet extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GoldMagnet");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldMagnet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldMagnet.png"));

    public GoldMagnet() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    private boolean active;

    @Override
    public void atBattleStart() {
        this.active = false;
        boolean isEliteOrBoss = AbstractDungeon.getCurrRoom().eliteTrigger;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                isEliteOrBoss = true;
            }
        }
        if (!isEliteOrBoss) {
            int rng = AbstractDungeon.miscRng.random(3);
            DefaultMod.logger.info("GoldMagnet rng: " + rng);
            if (rng == 3) {
                this.active = true;
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomRelicTier());
            }
        }
    }

    @Override
    public void onVictory() {
        if (this.active) {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
