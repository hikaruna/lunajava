package net.hikaruna.lunajava.feature;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import net.hikaruna.lunajava.util.Vector2D;
import net.hikaruna.lunajava.util.ZeroDividedException;

/**
 * Created by hikaru on 2015/05/07.
 */
public class Physics extends Feature {

    public float speedX, speedY;
    private Float speedLimitX;
    private Float speedLimitY;
    private Float resistance = 0F;

    private boolean debugMode;
    private final Paint p;

    public Physics() {
        super();
        p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3F);
    }

    public Vector2D getSpeed() {
        return new Vector2D(speedX, speedY);
    }

    @Override
    public void onUpdate() {
        if (speedLimitX != null) {
            if (speedX < -speedLimitX) {
                speedX = -speedLimitX;
            } else if (speedX > speedLimitX) {
                speedX = speedLimitX;
            }
        }

        if (speedLimitY != null) {
            if (speedY < -speedLimitY) {
                speedY = -speedLimitY;
            } else if (speedY > speedLimitY) {
                speedY = speedLimitY;
            }
        }

        speedX *= 1 - resistance;
        speedY *= 1 - resistance;

        getSprite().x += speedX;
        getSprite().y += speedY;
    }

    @Override
    public void onDraw(Canvas c) {
        if(debugMode) {
            try {
                float sX = getSprite().getAbsoluteX();
                float sY = getSprite().getAbsoluteY();
                Vector2D start = new Vector2D(sX, sY);
                Vector2D speed = getSpeed().multiplication(30F);
                Vector2D end = start.addition(speed);
                Vector2D allowA = speed.rotation(+Math.PI * 3 / 4).nomalize().multiplication(30);
                Vector2D allowB = speed.rotation(-Math.PI * 3 / 4).nomalize().multiplication(30);
                c.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), p);
                c.drawLine(end.getX(), end.getY(), end.addition(allowA).getX(), end.addition(allowA).getY(), p);
                c.drawLine(end.getX(), end.getY(), end.addition(allowB).getX(), end.addition(allowB).getY(), p);
            }catch (ZeroDividedException e) {
            }
        }
    }

    public void setSpeedLimitX(float speedLimitX) {
        this.speedLimitX = speedLimitX;
    }

    public void setSpeedLimitY(float speedLimitY) {
        this.speedLimitY = speedLimitY;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public void setSpeedLimit(float speedLimit) {
        setSpeedLimitX(speedLimit);
        setSpeedLimitY(speedLimit);
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
