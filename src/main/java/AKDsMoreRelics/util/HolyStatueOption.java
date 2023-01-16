package AKDsMoreRelics.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class HolyStatueOption extends AbstractCampfireOption {
    private static final RelicStrings relicStrings;
    public static final String[] DESCRIPTIONS;

    public HolyStatueOption(boolean active) {
        this.label = DESCRIPTIONS[1];
        this.usable = active;
        this.description = DESCRIPTIONS[2];
        this.img = loadImage("AKDsMoreRelicsResources/images/ui/HolyStatueOption.png");
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new HolyStatueEffect());
            for(int i = 0; i < 30; ++i) {
                AbstractDungeon.topLevelEffects.add(new CampfireSleepScreenCoverEffect());
            }
        }
    }

    static {
        relicStrings = CardCrawlGame.languagePack.getRelicStrings("AKDsMoreRelics:HolyStatue");
        DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    }
}