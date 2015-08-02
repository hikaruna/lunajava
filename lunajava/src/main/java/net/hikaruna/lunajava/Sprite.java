package net.hikaruna.lunajava;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;

import net.hikaruna.lunajava.feature.Feature;
import net.hikaruna.lunajava.support.FeatureManager;


/**
 * Created by hikaru on 2015/05/07.
 */
public class Sprite {

    public int x, y;
    public int w, h;
    private Paint background;
    private boolean hide;

    private SpriteGroup parent;
    private FeatureManager featureManager;

    public Sprite() {
        featureManager = new FeatureManager(this);
    }

    @Nullable
    public SpriteGroup getParent() {
        return parent;
    }

    public void setParent(SpriteGroup parent) {
        this.parent = parent;
    }

    public Scene getScene() {
        if(getParent() == null) {
            return null;
        }
        return getParent().getScene();
    }

    public Game getGame() {
        return getParent().getGame();
    }

    public Context getContext() {
        return getParent().getContext();
    }

    public float getTop() {
        return y - (float) h / 2;
    }

    public float getBottom() {
        return y + (float) h / 2;
    }

    public float getLeft() {
        return x - (float) w / 2;
    }

    public float getRight() {
        return x + (float) w / 2;
    }

    public RectF getRect() {
        return new RectF(getLeft(), getTop(), getRight(), getBottom());
    }

    public void setBackground(int background) {
        this.background = new Paint();
        this.background.setColor(background);
    }

    public float getAbsoluteX() {
        return getParent().getAbsoluteLeft() + x;
    }

    public void setAbsoluteX(float x) {
        this.x = (int) (x - getParent().getAbsoluteLeft());
    }

    public float getAbsoluteY() {
        return getParent().getAbsoluteTop() + y;
    }

    public void setAbsoluteY(float y) {
        this.y = (int) (y - getParent().getAbsoluteTop());
    }

    public float getAbsoluteTop() {
        return getAbsoluteY() - (float) h / 2;
    }

    public float getAbsoluteBottom() {
        return getAbsoluteY() + (float) h / 2;
    }

    public float getAbsoluteLeft() {
        return getAbsoluteX() - (float) w / 2;
    }

    public float getAbsoluteRight() {
        return getAbsoluteX() + (float) w / 2;
    }

    public RectF getAbsoluteRect() {
        return new RectF(getAbsoluteLeft(), getAbsoluteTop(), getAbsoluteRight(), getAbsoluteBottom());
    }

    public void show() {
        hide = false;
    }

    public void hide() {
        hide = true;
    }

    public boolean isHide() {
        return hide;
    }

    public void update() {
        featureManager.update();
        onUpdate();
    }

    public void onUpdate() {
    }

    public void draw(Canvas c) {
        if(hide) {
            return;
        }
        if (background != null) {
            c.drawRect(getAbsoluteRect(), background);
        }
        onDraw(c);
        featureManager.draw(c);
    }

    public void onDraw(Canvas c) {
    }

    public void destroy() {
        featureManager.destroy();
    }

    public void onSceneSetted(Scene scene) {
        featureManager.onSceneSetted(scene);
    }

    public <T extends Feature> T useFeature(Class<T> featureClass) {
        return featureManager.useFeature(featureClass);
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }
}

