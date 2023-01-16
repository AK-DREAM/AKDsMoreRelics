package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class CurseStaff extends CustomRelic {

    public static final String ID = DefaultMod.makeID("CurseStaff");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CurseStaff.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CurseStaff.png"));

    public CurseStaff() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        for (int i = 1; i <= 2; i++) {
            float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Necronomicurse(), x, y));
        }
    }
    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.cardID.equals("Necronomicurse")) {
            this.flash();
            this.addToBot(new DrawCardAction(1));
            this.addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
