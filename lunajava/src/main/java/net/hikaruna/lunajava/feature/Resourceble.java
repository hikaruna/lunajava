package net.hikaruna.lunajava.feature;

import android.graphics.Picture;
import android.support.annotation.Nullable;

import net.hikaruna.lunajava.Scene;
import net.hikaruna.lunajava.support.ResourceManager;

/**
 * Created by hikaru on 2015/05/08.
 */
public class Resourceble extends Feature {

    private Integer resId;
    private boolean useFlag;

    public void setResource(int resId) {
        this.resId = resId;
        this.useFlag = false;
        if (getSprite().getScene() != null) {
            setResourceReal();
        }
    }

    private void setResourceReal() {
        Picture p;
        if (useFlag) {
            p = getResourceManager().getResource(resId);
        } else {
            p = getResourceManager().useResource(resId);
        }
        getPictureble().setPicture(p);
    }

    @Override
    public void onSceneSetted(Scene scene) {
        if (resId != null) {
            setResourceReal();
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public Class<? extends Feature>[] getDepends() {
        return new Class[]{Pictureble.class};
    }

    private Pictureble getPictureble() {
        return getSprite().getFeatureManager().getFeature(Pictureble.class);
    }

    private ResourceManager getResourceManager() {
        return getSprite().getScene().getResourceManager();
    }
}

