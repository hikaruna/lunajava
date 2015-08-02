package net.hikaruna.lunajava.support;

import android.graphics.Canvas;

import net.hikaruna.lunajava.Scene;
import net.hikaruna.lunajava.Sprite;
import net.hikaruna.lunajava.feature.Feature;

import java.util.LinkedList;

/**
 * Created by hikaru on 2015/05/09.
 */
public class FeatureManager {

    private final Sprite sprite;
    private LinkedList<Feature> features;

    public FeatureManager(Sprite sprite) {
        features = new LinkedList<>();
        this.sprite = sprite;
    }

    public synchronized <T extends Feature> T useFeature(Class<T> featureClass) {
        if (!containsFeature(featureClass)) {
            setFeature(featureClass);
        }

        return getFeature(featureClass);
    }

    private synchronized void setFeature(Class<? extends Feature> featureClass) {
        try {
            if (containsFeature(featureClass)) {
                throw new RuntimeException(featureClass.getSimpleName() + "is alredy enabled.");
            }
            Feature feature = featureClass.newInstance();
            resolveDependency(feature);
            feature.setSprite(sprite);
            features.add(0, feature);
            feature.onSpriteSetted(sprite);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized boolean containsFeature(Class<? extends Feature> featureClass) {
        for (Feature feature : features) {
            if (feature.getClass().equals(featureClass)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends Feature> T getFeature(Class<T> featureClass) {
        for (Feature feature : features) {
            if (feature.getClass().equals(featureClass)) {
                return (T) feature;
            }
        }
        return null;
    }

    private boolean resolveDependency(Feature feature) {
        if (feature.getDepends() == null) {
            return true;
        }

        for (Class<? extends Feature> depend : feature.getDepends()) {
            if (!containsFeature(depend)) {
                setFeature(depend);
                return false;
            }
        }
        return true;
    }

    public void update() {
        for (Feature feature : features) {
            feature.onUpdate();
        }
    }

    public void draw(Canvas c) {
        for (Feature feature : features) {
            feature.onDraw(c);
        }
    }

    public void destroy() {
        for (Feature feature : features) {
            feature.onDestroy();
        }
    }

    public void onSceneSetted(Scene scene) {
        for (Feature feature : features) {
            feature.onSceneSetted(scene);
        }
    }
}
