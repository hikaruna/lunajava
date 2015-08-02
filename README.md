Lunajava
====

Lunajavaは、Androidで2Dゲームを作るためのフレームワークです。

## Description

LunajavaはAndroidのTextureViewをラップして作られています。
Canvasの描画やループのロジックなど、ゲーム作成するにあたりよく作るこのになる仕組みを提供します。
Unityに少し似たインターフェースを提供します。
Sound関係はサポートしていません。

## Demo

このリポジトリをチェックアウトし、AndroidStudioにimportして下さい。
demoモジュールがLunajavaを使ったデモになります。

## Requirement

AppcompatV7に依存しています。

## Install

ProjectRoot/Module/build.gradle

```

+ repositories {
+     maven {
+         url 'http://hikaruna.github.io/lunajava/repository'
+     }
+ }


dependencies {
+    compile 'net.hikaruna:lunajava:+'
}

```

## Usage

```java

// GameActivityのサブクラスを作成します
// これは、ListViewActivityのようなもので、GameViewがR.id.gameというIDで一つだけ配置されたActivityです。
// ※ GameActivityを使わず、Gameビューを坦々で用いることも出来ます
class MainActivity extends GameActivity {

    // Gameの生成が終わった後のコールバッグを実装します
    @Override
    public void onGameCreated(Game game) {
        // Gameに様々なプロパティを設定します
        game.getFpsManager().setFps(30);

        // Gameに自作のScene(後述)のセットします
        game.setCurrentScene(new MyScene());
    }
}

// Gameの場面を表すクラス
// LunajavaではSceneにSpriteを配置することで
// ゲームを構築していきます
class MyScene extends Scene {

    // SceneがGameに初めてセットされた時のコールバック
    // ここで初期化処理をします
    @Override
    public void onCreate(Scene from, Game game) {

        // 自作スプライト(後述)をSceneにセット
        addChild(new MySprite());
    }

}

// MySceneにセットする予定の自作スプライトクラス
// このようにSpriteのサブクラスを作成することで
// 様々なGameObjectを定義します
class MySprite extends Sprite {

    private Paint p;

    // スプライトのコンストラクタで初期設定を行います
    public MySprite() {

        // スプライトの座標を設定
        // Lunajavaの座標基準は左上ではなく、中心です
        x = 300;
        y = 300;

        // スプライトの幅、高さを設定
        w = 100;
        h = 100;

        // 表示処理に使うPaintを初期化
        p = new Paint();
    }

    @Override
    public void onUpdate() {
        // 毎フレーム行う処理を定義
    }

    @Override
    public void onDraw(Canvas c) {
        // 表示処理を定義します
        // 自分の位置に赤い丸を描画します
        // getAbsoluteRect は自身の四隅の絶対座標を返します
        // Canvasに描画する時の座標はAbsolute座標を用いること)
        
        c.drawArc(getAbsoluteRect(), 0, 360, false, p);
    }

}

```

Lunajavaの基本的な使い方は以上です。
オプショナルな使い方として、Featureというものがあります。
これはSpriteに様々な「機能」を付与できるものです
net.hikaruna.lunajava.featureパッケージに定義されています。

* Resourcible: SpriteにResIdを設定するだけで画像を表示できるようになる機能
* Animation: SpriteにResIdの配列を指定するだけでアニメーションを表示できるようになる機能
* Phycs: Spriteに簡易的な物理挙動を持たせることが出来る機能。まだ、加速度が定義できる程度です。
* RectCollider: Spriteに四角の当たり判定を持たせることが出来る機能
* CollisionListenable: Spriteに当たり判定が発生した時のコールバックを指定できる機能
* Controllable: Spriteに、操作可能になることが出来る機能

詳しくはそれぞれのクラスのjdocを参照して下さい(後で作る)

APIが変動して、ドキュメントの更新が追いつかない場合があります。
このドキュメントは 0.0.1の時のものです
    


## Contribution

TBD

## Licence

[MIT](https://github.com/tcnksm/tool/blob/master/LICENCE)

## Author

[hikaruna](https://github.com/hikaruna)
