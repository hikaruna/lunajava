package net.hikaruna.lunajava.feature;

import net.hikaruna.lunajava.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hikaru on 2015/05/09.
 */
public class CollisionListenable extends Feature {

    private List<OnCollisionListner> listeners;

    public CollisionListenable() {
        listeners = new ArrayList<>();
    }

    public void addOnCollisionListener(OnCollisionListner listener) {
        listeners.add(listener);
    }

    public void collision(Sprite other) {
        for(OnCollisionListner listner : listeners) {
            listner.onCollision(other);
        }
    }

    public interface OnCollisionListner {
        void onCollision(Sprite other);
    }
}
