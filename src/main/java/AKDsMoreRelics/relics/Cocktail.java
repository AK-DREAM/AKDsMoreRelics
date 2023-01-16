package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.potions.LiquidMemories;
import com.megacrit.cardcrawl.potions.SmokeBomb;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class Cocktail extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Cocktail");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Cocktail.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Cocktail.png"));

    public Cocktail() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    private boolean used;

    @Override
    public void atBattleStart() { this.used = false; }

    @Override
    public void atTurnStartPostDraw() {
        if (!this.used) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractPotion po = AbstractDungeon.returnRandomPotion(true);
            while (po instanceof SmokeBomb || po instanceof FairyPotion || po instanceof LiquidMemories) {
                po = AbstractDungeon.returnRandomPotion(true);
            }
            AbstractCreature target = AbstractDungeon.player;
            if (po.isThrown && po.targetRequired) {
                target = AbstractDungeon.getRandomMonster();
            }
            po.use(target);
            this.used = true;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
