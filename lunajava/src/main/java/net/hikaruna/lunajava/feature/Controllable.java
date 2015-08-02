package net.hikaruna.lunajava.feature;

import android.view.MotionEvent;

import net.hikaruna.lunajava.Game;
import net.hikaruna.lunajava.Scene;
import net.hikaruna.lunajava.SpriteGroup;
import net.hikaruna.lunajava.support.ControllerManager;

/**
 * Created by hikaru on 2015/05/18.
 */
public class Controllable extends Feature {

    private ControllerManager manager;
    private Controller listener;

    @Override
    public void onSceneSetted(Scene scene) {
        manager = scene.getControllerManager();
        manager.add(this);
    }

    public void setController(Controller controller) {
        listener = controller;
    }

    public void onControll(ControllEvent event) {
        if(listener == null) {
            return;
        }
        event.setCurrent(getSprite().getParent());
        listener.onControll(event);
    }

    public interface Controller {
        void onControll(ControllEvent event);
    }

    public static class ControllEvent {

        public int gameOffsetX;
        public int gameOffsetY;
        public SpriteGroup current;

        private final MotionEvent event;
        private final Game game;

        public ControllEvent(MotionEvent event, Game game) {
            this.event = event;
            this.game = game;

        }

        public void setCurrent(SpriteGroup current) {
            this.current = current;
        }

        public int getX() {
            return (int) (event.getX() - game.getLocationInWindowX() - current.getLeft());
        }

        public int getY() {
            return (int) (event.getY() - game.getLocationInWindowY() - current.getTop());
        }
    }
}
