package net.hikaruna.lunajava.support;

/**
 * Created by hikaru on 2015/05/07.
 */
public class FpsManager {

    private int fps;

    private long now;
    private long next;

    public FpsManager() {
        this.fps = 10;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void start() {
        now = System.nanoTime();
        next = now + 1000000000 / fps;
    }


    /**
     * @return should do continue is true
     */
    public synchronized boolean sleep() {
        now = System.nanoTime();
        if (next > now) {
            try {
                Thread.sleep((next - now) / 1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        next += 1000000000 / fps;
        if (next < 0) {
            next += 1000000000 / fps;
        }
        return false;
    }
}
