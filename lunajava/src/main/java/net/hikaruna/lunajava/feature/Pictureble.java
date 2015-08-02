package net.hikaruna.lunajava.feature;

import android.graphics.Canvas;
import android.graphics.Picture;

/**
 * Created by hikaru on 2015/05/08.
 */
public class Pictureble extends Feature {

    private Picture picture;

    @Override
    public void onDraw(Canvas c) {
        if(picture != null) {
            c.drawPicture(picture, getSprite().getRect());
        }
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
