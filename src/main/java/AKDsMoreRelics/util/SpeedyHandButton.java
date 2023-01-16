package AKDsMoreRelics.util;

import AKDsMoreRelics.DefaultMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

import javax.swing.*;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class SpeedyHandButton {
    private static final RelicStrings uiStrings;
    public static final String[] TEXT;
    private static final int W = 512;
    private static final int H = 256;
    private static final float SHOW_X;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private static final float TAKE_Y;
    private Color textColor;
    private Color btnColor;
    private boolean isHidden;
    private RewardItem rItem;
    private CardRewardScreen rewardScreen;
    private float controllerImgTextWidth;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;
    public static Texture RED_BUTTON;
    private float velocity, angle;
    public float duration, nowT;

    public SpeedyHandButton() {
        this.current_x = HIDE_X;
        this.target_x = this.current_x;
        this.textColor = Color.WHITE.cpy();
        this.btnColor = Color.WHITE.cpy();
        this.isHidden = true;
        this.rItem = null;
        this.controllerImgTextWidth = 0.0F;
        this.hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        this.hb.move((float)Settings.WIDTH / 2.0F, TAKE_Y);
        // this.velocity = 30; this.angle = 1;
        // this.duration = 3.0F;
    }

    public void update() {
        if (!this.isHidden) {
            this.hb.update();
            if (this.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (this.hb.hovered && InputHelper.justClickedLeft) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }

            if (this.hb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                CInputActionSet.proceed.unpress();
                this.hb.clicked = false;
                this.onClick();
                AbstractDungeon.closeCurrentScreen();
                this.hide();
            }

            this.nowT += Gdx.graphics.getDeltaTime();
//            if (this.nowT <= 0.0F) this.hide();

            if (this.current_x != this.target_x) {
                this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
                this.hb.move(this.current_x, TAKE_Y);
            }

            this.textColor.a = MathHelper.fadeLerpSnap(this.textColor.a, 1.0F);
            this.btnColor.a = this.textColor.a;
        }
    }

    public void onClick() {
        AbstractDungeon.player.getRelic("AKDsMoreRelics:SpeedyHand").flash();
        for (AbstractCard c : this.rewardScreen.rewardGroup) {
            c.upgrade();
            AbstractDungeon.effectsQueue.add(new FastCardObtainEffect(c, c.current_x, c.current_y));
        }
        AbstractDungeon.combatRewardScreen.rewards.remove(this.rItem);
    }

    public void hide() {
        if (!this.isHidden) {
            this.isHidden = true;
        }
    }

    public void show(CardRewardScreen screen, RewardItem rItem) {
        this.isHidden = false;
        this.textColor.a = 0.0F;
        this.btnColor.a = 0.0F;
        this.current_x = HIDE_X;
        this.target_x = SHOW_X;
        this.rewardScreen = screen;
        this.rItem = rItem;
    }

    public void render(SpriteBatch sb) {
        if (!this.isHidden) {
            this.renderButton(sb);
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[1], this.current_x, TAKE_Y, this.textColor);
        }
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setLighter(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.7F));
        sb.draw(RED_BUTTON, this.current_x - 256.0F, TAKE_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        sb.setBlendFunction(770, 771);
    }
    public boolean isLightTme(float nowT) {
        float tmp = nowT*2;
        return Math.floor(tmp) % 2 == 1;
    }

    private void renderButton(SpriteBatch sb) {
        sb.setColor(this.btnColor);
        sb.draw(RED_BUTTON, this.current_x - 256.0F, TAKE_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        if ((this.hb.hovered && !this.hb.clickStarted) || isLightTme(nowT)) {
            this.setLighter(sb);
        }
        if (Settings.isControllerMode) {
            if (this.controllerImgTextWidth == 0.0F) {
                this.controllerImgTextWidth = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[1], 9999.0F, 0.0F);
            }

            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), this.current_x - 32.0F - this.controllerImgTextWidth / 2.0F - 38.0F * Settings.scale, TAKE_Y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

        this.hb.render(sb);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getRelicStrings("AKDsMoreRelics:SpeedyHand");
        TEXT = uiStrings.DESCRIPTIONS;
        SHOW_X = (float)Settings.WIDTH / 2.0F;
        HIDE_X = (float)Settings.WIDTH / 2.0F;
        HITBOX_W = 260.0F * Settings.scale;
        HITBOX_H = 80.0F * Settings.scale;
        TAKE_Y = (float)Settings.HEIGHT / 2.0F - 440.0F * Settings.scale;
        RED_BUTTON = loadImage("AKDsMoreRelicsResources/images/ui/takeALL.png");
    }
}
