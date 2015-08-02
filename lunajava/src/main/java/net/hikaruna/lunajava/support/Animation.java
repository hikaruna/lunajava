package net.hikaruna.lunajava.support;

import android.graphics.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hikaru on 2015/07/31.
 */
public class Animation {

    public final Type type;
    private final List<AnimationItem> animation;

    public Animation(AnimationItem... animationItems) {
        animation = new ArrayList<>();
        for(AnimationItem item : animationItems) {
            animation.add(item);
        }
        type = Type.MIXED;
    }

    public Animation(int... resIds) {
        animation = new ArrayList<>();
        for(int resId : resIds) {
            animation.add(new AnimationItem(resId));
        }
        type = Type.RES_ID;
    }

    public Animation(Picture... pictures) {
        animation = new ArrayList<>();
        for(Picture picture : pictures) {
            animation.add(new AnimationItem(picture));
        }
        type = Type.PICTURE;
    }

    public int size() {
        return animation.size();
    }

    public AnimationItem get(int location) {
        return animation.get(location);
    }

    public static class AnimationItem {

        public final Type type;
        private int resId;
        private Picture picture;

        public AnimationItem(int resId) {
            this.resId = resId;
            type = Type.RES_ID;
        }

        public AnimationItem(Picture picture) {
            this.picture = picture;
            type = Type.PICTURE;
        }

        public int getResId() {
            return resId;
        }

        public Picture getPicture() {
            return picture;
        }

        public enum Type {
            RES_ID, PICTURE
        }
    }

    public enum Type {
        RES_ID, PICTURE, MIXED
    }
}
