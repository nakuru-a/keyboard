package com.sample.customkeyboard;

import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class NewKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private Keyboard keyboard_qwerty;
    private SharedPreferences prefs;
    private boolean caps;

    //初回だけ呼ばれる
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //初回だけ呼ばれる
    @Override
    public View onCreateInputView() {
        super.onCreateInputView();

        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        keyboardView.setPreviewEnabled(false);
        return keyboardView;
    }

    @Override
    public void onInitializeInterface() {

        // キーボードのレイアウト ファイルを読み込み Keyboardを生成する
        keyboard = new Keyboard(this, R.xml.keyboard);
        keyboard_qwerty = new Keyboard(this, R.xml.keyboard_qwerty);
    }

    //キーボードが表示されるたびに呼ばれるメソッド
    @Override
    public void onStartInputView(EditorInfo editorInfo, boolean restarting) {
        prefs = getSharedPreferences("NewKeyboardData", MODE_MULTI_PROCESS);
    }

    //キーボードが閉じる時に呼ばれるメソッド
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //キーを押した時
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        boolean doubleKey = prefs.getBoolean("doubleKey", false);

        InputConnection ic = getCurrentInputConnection();
        switch (primaryCode) {
            case KeyEvent.KEYCODE_1:
                ic.commitText(!doubleKey ? "1" : "11", 1);
                break;
            case KeyEvent.KEYCODE_2:
                ic.commitText(!doubleKey ? "2" : "22", 1);
                break;
            case KeyEvent.KEYCODE_3:
                ic.commitText(!doubleKey ? "3" : "33", 1);
                break;
            case KeyEvent.KEYCODE_4:
                ic.commitText(!doubleKey ? "4" : "44", 1);
                break;
            case KeyEvent.KEYCODE_5:
                ic.commitText(!doubleKey ? "5" : "55", 1);
                break;
            case KeyEvent.KEYCODE_6:
                ic.commitText(!doubleKey ? "6" : "66", 1);
                break;
            case KeyEvent.KEYCODE_7:
                ic.commitText(!doubleKey ? "7" : "77", 1);
                break;
            case KeyEvent.KEYCODE_8:
                ic.commitText(!doubleKey ? "8" : "88", 1);
                break;
            case KeyEvent.KEYCODE_9:
                ic.commitText(!doubleKey ? "9" : "99", 1);
                break;
            case KeyEvent.KEYCODE_0:
                ic.commitText(!doubleKey ? "0" : "00", 1);
                break;
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case KeyEvent.KEYCODE_ENTER:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                keyboardView.invalidateAllKeys();
                break;
            case 10000:
                keyboardView.setKeyboard(keyboard_qwerty);
                setInputView(keyboardView);
                break;
            case 10001:
                keyboardView.setKeyboard(keyboard);
                setInputView(keyboardView);
                break;
            default:
                char code = (char) primaryCode;
                if (caps && Character.isLetter(code)) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
                break;
        }
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeUp() {
    }

}
