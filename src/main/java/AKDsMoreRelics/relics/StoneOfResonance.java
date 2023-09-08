package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.powers.StunGrenadePower;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class StoneOfResonance extends CustomRelic {

    public static final String ID = DefaultMod.makeID("StoneOfResonance");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StoneOfResonance.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StoneOfResonance.png"));

    public StoneOfResonance() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction u) {
        List<AbstractCard> sameCard = new ArrayList<>();
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) if (card.cardID.equals(c.cardID)) sameCard.add(c);
        if (sameCard.size() > 1) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(p, this));
            AbstractCard c2 = card.makeStatEquivalentCopy();
            c2.current_x = card.current_x; c2.target_x = card.target_x;
            c2.current_y = card.current_y; c2.target_y = card.target_y;
            c2.cardID += StoneOfResonance.ID;
            c2.purgeOnUse = true;
            this.addToTop(new NewQueueCardAction(c2, u.target, false, true));
        }
    }

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class UseCardActionBuryPatch {
        @SpireInsertPatch(rloc = 20)
        public static SpireReturn<Void> Insert(UseCardAction u) {
            AbstractCard card = ReflectionHacks.getPrivate(u, UseCardAction.class, "targetCard");
            if (card.cardID.endsWith(StoneOfResonance.ID)) return SpireReturn.Return();
            else return SpireReturn.Continue();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
