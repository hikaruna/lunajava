package net.hikaruna.lunajava;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import net.hikaruna.lunajava.feature.Controllable;
import net.hikaruna.lunajava.support.FpsManager;
import net.hikaruna.lunajava.support.FpsMoniter;

/**
 * Created by hikaru on 2015/07/26.
 */
public class Game extends TextureView implements Runnable, TextureView.SurfaceTextureListener {

    private static final String TAG = "LUNA";
    private FpsManager fpsManager;
    private FpsMoniter fpsMoniter;
    public long frameCount;
    private Scene currentScene;
    private Thread thread;
    private OnGameListener listener;
    private boolean gameCreated;


    public Game(Context context) {
        super(context);
        init();
    }

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Game(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Game(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        this.fpsManager = new FpsManager();
        this.fpsMoniter = new FpsMoniter(10);

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Controllable.ControllEvent controllEvent = new Controllable.ControllEvent(event, Game.this);
                return getCurrentScene().onControll(controllEvent);
            }
        });

        setSurfaceTextureListener(this);

        this.listener = new OnGameListener() {

            @Override
            public void onGameCreated(Game game) {
            }

            @Override
            public void onGameResume() {
            }

            @Override
            public void onGamePause() {
            }
        };

        setCurrentScene(new Scene());
    }

    public OnGameListener getOnGameListener() {
        return listener;
    }

    public void setOnGameListener(OnGameListener listener) {
        this.listener = listener;
    }

    public synchronized void setCurrentScene(Scene scene) {
        scene.create(currentScene, this);
        this.currentScene = scene;
        scene.resume(currentScene, this);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public FpsManager getFpsManager() {
        return fpsManager;
    }

    public int getLocationInWindowX() {
        int[] xy = new int[2];
        getLocationInWindow(xy);
        return xy[0];
    }

    public int getLocationInWindowY() {
        int[] xy = new int[2];
        getLocationInWindow(xy);
        return xy[1];
    }

    public synchronized void start() {
        if (thread != null) {
            throw new RuntimeException("Game is already started.");
        }
        thread = new Thread(this);
        thread.start();
        Log.d(TAG, "Game start!");
    }

    public synchronized void stop() {
        thread = null;
        Log.d(TAG, "Game stop!");
    }

    @Override
    public void run() {
        fpsManager.start();
        while (thread != null) {
            if (fpsManager.sleep()) {
                continue;
            }
            Canvas c = lockCanvas();
            loop(c);
            unlockCanvasAndPost(c);
        }
    }

    private void loop(Canvas c) {
        c.drawColor(Color.BLACK);
        currentScene.update();
        currentScene.draw(c);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(30f);
        c.drawText(String.format("%5.02fFps", fpsMoniter.show()), 100, 100, p);
        frameCount++;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (!gameCreated) {
            listener.onGameCreated(this);
            gameCreated = true;
        }
        start();
        listener.onGameResume();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        listener.onGamePause();
        stop();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public interface OnGameListener {
        void onGameCreated(Game game);

        void onGameResume();

        void onGamePause();
    }
}
