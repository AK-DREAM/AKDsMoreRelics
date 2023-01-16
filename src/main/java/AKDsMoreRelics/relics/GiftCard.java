package AKDsMoreRelics.relics;

import AKDsMoreRelics.DefaultMod;
import AKDsMoreRelics.util.TextureLoader;
import basemod.AutoAdd;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;

import java.util.ArrayList;
import java.util.Iterator;

import static AKDsMoreRelics.DefaultMod.makeRelicOutlinePath;
import static AKDsMoreRelics.DefaultMod.makeRelicPath;

public class GiftCard extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GiftCard");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GiftCard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GiftCard.png"));
    public GiftCard() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        if (AbstractDungeon.getCurrRoom() instanceof ShopRoom) {
            ArrayList<StoreRelic> relics = ReflectionHacks.getPrivate(AbstractDungeon.shopScreen, ShopScreen.class, "relics");
            for (StoreRelic r : relics) {
                int slot = ReflectionHacks.getPrivate(r, StoreRelic.class, "slot");
                if (slot != 2 && !r.isPurchased) {
                    r.price = 0;
                }
            }
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
