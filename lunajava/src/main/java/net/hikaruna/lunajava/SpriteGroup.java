package net.hikaruna.lunajava;

import android.graphics.Canvas;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hikaru on 2015/05/07.
 */
public class SpriteGroup extends Sprite {
    List<Sprite> children;

    public SpriteGroup() {
        super();
        children = new LinkedList<>();
    }

    public synchronized void addChild(Sprite child) {
        child.setParent(this);
        children.add(child);
    }

    public synchronized void removeChild(Sprite child) {
        child.setParent(null);
        children.remove(child);
    }


    public void update() {
        super.update();
        for(Sprite child : children) {
            child.update();
        }
    }

    @Override
    public final void draw(Canvas c) {
        super.draw(c);
        for(Sprite child : children) {
            child.draw(c);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        for(Sprite child : children) {
            child.destroy();
        }
    }
}
