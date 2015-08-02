package net.hikaruna.demo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import net.hikaruna.lunajava.Game;
import net.hikaruna.lunajava.GameActivity;
import net.hikaruna.lunajava.Sprite;

/**
 * Created by hikaru on 2015/08/01.
 */
public class MainActivity extends GameActivity {
    @Override
    public void onGameCreated(Game game) {

        {
            // 画面の中央に白い四角
            Sprite sprite = new Sprite();
            sprite.setBackground(Color.WHITE);
            sprite.x = game.getWidth() / 2;
            sprite.y = game.getHeight() / 2;
            sprite.w = 20;
            sprite.h = 20;
            game.getCurrentScene().addChild(sprite);
        }

        {
            // 画面の中央にプルプル動く赤いまる
            final Paint p = new Paint();
            p.setColor(Color.RED);

            // 毎フレーム呼ばれるコールバックをオーバライドして動きを指定
            // 普通は、Spriteのサブクラスを定義して行う
            Sprite sprite = new Sprite() {
                @Override
                public void onUpdate() {
                    // -1~+1の間で座標がプルプル揺れ動く
                    x += Math.random() * 3 - 1.5;
                    y += Math.random() * 3 - 1.5;
                }

                @Override
                public void onDraw(Canvas c) {
                    // 画面に赤い丸を描く
                    c.drawArc(getAbsoluteRect(), 0, 360, false, p);
                }
            };

            sprite.x = game.getWidth() / 2;
            sprite.y = game.getHeight() / 2;
            sprite.w = 20;
            sprite.h = 20;
            game.getCurrentScene().addChild(sprite);
        }
    }
}
