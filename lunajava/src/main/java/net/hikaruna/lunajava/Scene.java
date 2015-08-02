package net.hikaruna.lunajava;

import android.content.Context;

import net.hikaruna.lunajava.feature.Controllable;
import net.hikaruna.lunajava.support.CollisionManager;
import net.hikaruna.lunajava.support.ControllerManager;
import net.hikaruna.lunajava.support.ResourceManager;

/**
 * Created by hikaru on 2015/05/07.
 */
public class Scene extends SpriteGroup {
    private boolean initialized;
    private Game game;
    private ResourceManager resourceManager;
    private CollisionManager collisionManager;
    private ControllerManager controllerManager;

    public void create(Scene from, Game game) {
        if(initialized) {
            return;
        }

        this.game = game;
        this.resourceManager = new ResourceManager(getContext().getResources());
        this.collisionManager = new CollisionManager();
        this.controllerManager = new ControllerManager();
        x = game.getWidth() / 2;
        y = game.getHeight() / 2;
        w = game.getWidth();
        h = game.getHeight();

        onCreate(from, game);
        initialized = true;
    }

    /**
     * 初めてGameにセットされた時に呼ばれる
     *
     * @param from
     * @param game
     */
    public void onCreate(Scene from, Game game) {
    }

    /**
     * Gameにセットされるたびに呼ばれる
     *
     * @param from
     * @param game
     */
    public void resume(Scene from, Game game) {
        onResume(from, game);
    }

    public void onResume(Scene from, Game game) {
    }

    @Override
    public Context getContext() {
        return game.getContext();
    }

    @Override
    public Scene getScene() {
        return this;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public synchronized void addChild(Sprite child) {
        super.addChild(child);
        child.onSceneSetted(this);
    }

    @Override
    public void update() {
        controllerManager.update();
        super.update();
        collisionManager.update();
    }

    @Override
    public SpriteGroup getParent() {
        return this;
    }

    @Override
    public float getAbsoluteX() {
        return x;
    }

    @Override
    public float getAbsoluteY() {
        return y;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public boolean onControll(Controllable.ControllEvent event) {
        event.setCurrent(this);
        return controllerManager.onControll(event);
    }
}
