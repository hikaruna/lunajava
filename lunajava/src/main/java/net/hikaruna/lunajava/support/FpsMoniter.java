package net.hikaruna.lunajava.support;

/**
 * Created by hikaru on 2015/05/07.
 */
public class FpsMoniter {

    long past;
    int wait;
    long[] pool;
    float fps;
    private long count;

    public FpsMoniter(int wait) {
        this.wait = wait;
        pool = new long[wait];
    }

    /**
     * It must call once per frame.
     * @return calculated real fps
     */
    public float show() {
        int i = (int)(count++ % wait);
        long now = System.nanoTime();
        pool[i] = now - past;
        past = now;

        if(i == 0) {
            long sum = 0L;
            for(long l : pool) {
                sum += l;
            }

            float ave = (float) sum / wait;
            fps = 1000000000 / ave;
        }

        return fps;
    }
}
