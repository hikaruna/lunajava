package net.hikaruna.lunajava.feature;

import android.graphics.Canvas;
import android.support.annotation.Nullable;

import net.hikaruna.lunajava.Scene;
import net.hikaruna.lunajava.Sprite;
import net.hikaruna.lunajava.support.CollisionManager;

/**
 * Created by hikaru on 2015/05/09.
 */
public abstract class AbstractCollider extends Feature {

    private CollisionManager manager;
    private Integer groupId;
    private CollisionListenable collision;
    private boolean debugMode;
    private boolean hit; // forDebug. if this frame already collisioned then true
    private boolean active;

    public AbstractCollider() {
        active = true;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Feature>[] getDepends() {
        return new Class[]{CollisionListenable.class};
    }

    @Override
    public void onSpriteSetted(Sprite sprite) {
        collision = sprite.getFeatureManager().getFeature(CollisionListenable.class);
        collision.addOnCollisionListener(new CollisionListenable.OnCollisionListner() {
            @Override
            public void onCollision(Sprite other) {
                hit = true;
            }
        });
    }

    @Override
    public void onSceneSetted(Scene scene) {
        manager = scene.getCollisionManager();
        setCollisionReal();
    }

    @Override
    public void onDraw(Canvas c) {
        if (debugMode && getActive()) {
            onDrawArea(c, hit);
            hit = false;
        }
    }


    @Override
    public void onDestroy() {
        removeSelf(groupId);
    }

    private void removeSelf(Integer groupId) {
        manager.getCollisionGroups().get(groupId).remove(this);
    }

    public void setGroup(Integer groupId) {
        Integer preId = this.groupId;
        this.groupId = groupId;
        if (manager != null) {
            removeSelf(preId);
            setCollisionReal();
        }
    }

    private void setCollisionReal() {
        manager.setCollicion(groupId, this);
    }

    public void collision(AbstractCollider other) {
        collision.collision(other.getSprite());
    }

    public abstract boolean check(AbstractCollider c2);

    public abstract void onDrawArea(Canvas c, boolean hit);

    public boolean getActive() {
        return active;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
