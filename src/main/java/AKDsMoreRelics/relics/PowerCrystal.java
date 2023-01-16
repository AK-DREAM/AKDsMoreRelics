package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.PowerCrystalModifier;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class PowerCrystal extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PowerCrystal");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PowerCrystal.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PowerCrystal.png"));

    public PowerCrystal() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.POWER && c.cost > 0 && !CardModifierManager.hasModifier(c, "AKDsMoreRelics:PowerCrystalModifier")) {
                c.cost = c.cost-1;
                if (c.costForTurn > c.cost) c.costForTurn = c.cost;
                c.isCostModified = true;
                CardModifierManager.addModifier(c, new PowerCrystalModifier());
            }
        }
    }

    @Override
    public void onDrawOrDiscard() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.POWER && c.cost > 0 && !CardModifierManager.hasModifier(c, "AKDsMoreRelics:PowerCrystalModifier")) {
                c.cost = c.cost-1;
                if (c.costForTurn > c.cost) c.costForTurn = c.cost;
                c.isCostModified = true;
                CardModifierManager.addModifier(c, new PowerCrystalModifier());
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
