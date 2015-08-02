package net.hikaruna.lunajava.util;

/**
 * Created by hikaru on 2015/07/05.
 */
public class Vector2D {
    private float x, y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float scale() {
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public Vector2D nomalize() throws ZeroDividedException {
        try {
            float nX = x / scale();
            float nY = y / scale();
            return new Vector2D(nX, nY);
        }catch (ArithmeticException e) {
            throw new ZeroDividedException();
        }
    }

    public Vector2D addition(Vector2D other) {
        return new Vector2D(x + other.getX(), y + other.getY());
    }

    public Vector2D subtraction(Vector2D other) {
        return new Vector2D(x - other.getX(), y - other.getY());
    }

    public Vector2D multiplication(float other) {
        return new Vector2D(x * other, y * other);
    }

    public Vector2D division(float other) {
        return new Vector2D(x / other, y / other);
    }

    public Vector2D rotation(double angleRadian) {
        double nX = x * Math.cos(angleRadian) - y * Math.sin(angleRadian);
        double nY = x * Math.sin(angleRadian) + y * Math.cos(angleRadian);
        return new Vector2D(nX, nY);
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
