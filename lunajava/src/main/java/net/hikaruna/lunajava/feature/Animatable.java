package net.hikaruna.lunajava.feature;

import android.support.annotation.Nullable;

import net.hikaruna.lunajava.support.Animation;

/**
 * Created by hikaru on 2015/05/08.
 */
public class Animatable extends Feature {

    /**
     * Integer
     */
    private Animation animation;
    private int deley;
    private long tick;

    public Animatable() {
        deley = 6;
    }


    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Feature>[] getDepends() {
        return new Class[]{Pictureble.class};
    }

    /**
     * This method depends Resourceble.
     *
     * @param animation item.
     *                  Enable Type is only resId:Integer.
     *                  resId:Integer is depends Resourceble.
     */
    public void setAnimation(Animation animation) {
        switch(animation.type) {
            case RES_ID:
                if (!getSprite().getFeatureManager().containsFeature(Resourceble.class)) {
                    throw new RuntimeException("依存解決失敗: AnimationにResourceIdを指定する機能はResourcebleに依存しています");
                }
                break;
            case PICTURE:
                if (!getSprite().getFeatureManager().containsFeature(Pictureble.class)) {
                    throw new RuntimeException("依存解決失敗: AnimationにPictureを指定する機能はPicturebleに依存しています");
                }
                break;
            case MIXED:
                if ((!getSprite().getFeatureManager().containsFeature(Resourceble.class)) || (!getSprite().getFeatureManager().containsFeature(Pictureble.class)) ) {
                    throw new RuntimeException("依存解決失敗: AnimationにPictureとResourceIdを指定する機能はPicturebleとResourcebleに依存しています");
                }
                break;
        }
        this.animation = animation;
    }

    @Override
    public void onUpdate() {
        if (animation == null) {
            return;
        }

        Animation.AnimationItem item = animation.get(((int) ((tick++ / deley) % animation.size())));
        switch(item.type) {
            case RES_ID:
                getSprite().getFeatureManager().getFeature(Resourceble.class).setResource(item.getResId());
                break;
            case PICTURE:
                getSprite().getFeatureManager().getFeature(Pictureble.class).setPicture(item.getPicture());
                break;
        }
    }

    public void setDeley(int deley) {
        this.deley = deley;
    }
}
