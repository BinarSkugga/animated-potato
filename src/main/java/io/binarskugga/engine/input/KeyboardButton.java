package io.binarskugga.engine.input;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardButton {
    public static List<KeyboardButton> all = new ArrayList<>();

    public static KeyboardButton UNKNOWN = new KeyboardButton(GLFW_KEY_UNKNOWN, "unknown");
    public static KeyboardButton SPACE = new KeyboardButton(GLFW_KEY_SPACE, "space");
    public static KeyboardButton APOSTROPHE = new KeyboardButton(GLFW_KEY_APOSTROPHE, "apostrophe");
    public static KeyboardButton COMMA = new KeyboardButton(GLFW_KEY_COMMA, "comma");
    public static KeyboardButton MINUS = new KeyboardButton(GLFW_KEY_MINUS, "minus");
    public static KeyboardButton PERIOD = new KeyboardButton(GLFW_KEY_PERIOD, "period");
    public static KeyboardButton SLASH = new KeyboardButton(GLFW_KEY_SLASH, "slash");
    public static KeyboardButton ZERO = new KeyboardButton(GLFW_KEY_0, "zero");
    public static KeyboardButton ONE = new KeyboardButton(GLFW_KEY_1, "one");
    public static KeyboardButton TWO = new KeyboardButton(GLFW_KEY_2, "two");
    public static KeyboardButton THREE = new KeyboardButton(GLFW_KEY_3, "three");
    public static KeyboardButton FOUR = new KeyboardButton(GLFW_KEY_4, "four");
    public static KeyboardButton FIVE = new KeyboardButton(GLFW_KEY_5, "five");
    public static KeyboardButton SIX = new KeyboardButton(GLFW_KEY_6, "six");
    public static KeyboardButton SEVEN = new KeyboardButton(GLFW_KEY_7, "seven");
    public static KeyboardButton EIGHT = new KeyboardButton(GLFW_KEY_8, "eight");
    public static KeyboardButton NINE = new KeyboardButton(GLFW_KEY_9, "nine");
    public static KeyboardButton SEMICOLON = new KeyboardButton(GLFW_KEY_SEMICOLON, "semicolon");
    public static KeyboardButton EQUAL = new KeyboardButton(GLFW_KEY_EQUAL, "equal");
    public static KeyboardButton A = new KeyboardButton(GLFW_KEY_A, "a");
    public static KeyboardButton B = new KeyboardButton(GLFW_KEY_B, "b");
    public static KeyboardButton C = new KeyboardButton(GLFW_KEY_C, "c");
    public static KeyboardButton D = new KeyboardButton(GLFW_KEY_D, "d");
    public static KeyboardButton E = new KeyboardButton(GLFW_KEY_E, "e");
    public static KeyboardButton F = new KeyboardButton(GLFW_KEY_F, "f");
    public static KeyboardButton G = new KeyboardButton(GLFW_KEY_G, "g");
    public static KeyboardButton H = new KeyboardButton(GLFW_KEY_H, "h");
    public static KeyboardButton I = new KeyboardButton(GLFW_KEY_I, "i");
    public static KeyboardButton J = new KeyboardButton(GLFW_KEY_J, "j");
    public static KeyboardButton K = new KeyboardButton(GLFW_KEY_K, "k");
    public static KeyboardButton L = new KeyboardButton(GLFW_KEY_L, "l");
    public static KeyboardButton M = new KeyboardButton(GLFW_KEY_M, "m");
    public static KeyboardButton N = new KeyboardButton(GLFW_KEY_N, "n");
    public static KeyboardButton O = new KeyboardButton(GLFW_KEY_O, "o");
    public static KeyboardButton P = new KeyboardButton(GLFW_KEY_P, "p");
    public static KeyboardButton Q = new KeyboardButton(GLFW_KEY_Q, "q");
    public static KeyboardButton R = new KeyboardButton(GLFW_KEY_R, "r");
    public static KeyboardButton S = new KeyboardButton(GLFW_KEY_S, "s");
    public static KeyboardButton T = new KeyboardButton(GLFW_KEY_T, "t");
    public static KeyboardButton U = new KeyboardButton(GLFW_KEY_U, "u");
    public static KeyboardButton V = new KeyboardButton(GLFW_KEY_V, "v");
    public static KeyboardButton W = new KeyboardButton(GLFW_KEY_W, "w");
    public static KeyboardButton X = new KeyboardButton(GLFW_KEY_X, "x");
    public static KeyboardButton Y = new KeyboardButton(GLFW_KEY_Y, "y");
    public static KeyboardButton Z = new KeyboardButton(GLFW_KEY_Z, "z");
    public static KeyboardButton LEFT_BRACKET = new KeyboardButton(GLFW_KEY_LEFT_BRACKET, "left bracket");
    public static KeyboardButton BACKSLASH = new KeyboardButton(GLFW_KEY_BACKSLASH, "backslash");
    public static KeyboardButton RIGHT_BRACKET = new KeyboardButton(GLFW_KEY_RIGHT_BRACKET, "right bracket");
    public static KeyboardButton GRAVE_ACCENT = new KeyboardButton(GLFW_KEY_GRAVE_ACCENT, "grave accent");
    public static KeyboardButton ESCAPE = new KeyboardButton(GLFW_KEY_ESCAPE, "escape");
    public static KeyboardButton ENTER = new KeyboardButton(GLFW_KEY_ENTER, "enter");
    public static KeyboardButton TAB = new KeyboardButton(GLFW_KEY_TAB, "tab");
    public static KeyboardButton BACKSPACE = new KeyboardButton(GLFW_KEY_BACKSPACE, "backspace");
    public static KeyboardButton INSERT = new KeyboardButton(GLFW_KEY_INSERT, "insert");
    public static KeyboardButton DELETE = new KeyboardButton(GLFW_KEY_DELETE, "delete");
    public static KeyboardButton RIGHT = new KeyboardButton(GLFW_KEY_RIGHT, "right");
    public static KeyboardButton LEFT = new KeyboardButton(GLFW_KEY_LEFT, "left");
    public static KeyboardButton DOWN = new KeyboardButton(GLFW_KEY_DOWN, "down");
    public static KeyboardButton UP = new KeyboardButton(GLFW_KEY_UP, "up");
    public static KeyboardButton PAGE_UP = new KeyboardButton(GLFW_KEY_PAGE_UP, "page up");
    public static KeyboardButton PAGE_DOWN = new KeyboardButton(GLFW_KEY_PAGE_DOWN, "page down");
    public static KeyboardButton HOME = new KeyboardButton(GLFW_KEY_HOME, "home");
    public static KeyboardButton END = new KeyboardButton(GLFW_KEY_END, "end");
    public static KeyboardButton CAPS_LOCK = new KeyboardButton(GLFW_KEY_CAPS_LOCK, "caps lock");
    public static KeyboardButton SCROLL_LOCK = new KeyboardButton(GLFW_KEY_SCROLL_LOCK, "scroll lock");
    public static KeyboardButton NUM_LOCK = new KeyboardButton(GLFW_KEY_NUM_LOCK, "num lock");
    public static KeyboardButton PRINT_SCREEN = new KeyboardButton(GLFW_KEY_PRINT_SCREEN, "print screen");
    public static KeyboardButton PAUSE = new KeyboardButton(GLFW_KEY_PAUSE, "pause");
    public static KeyboardButton F1 = new KeyboardButton(GLFW_KEY_F1, "f1");
    public static KeyboardButton F2 = new KeyboardButton(GLFW_KEY_F2, "f2");
    public static KeyboardButton F3 = new KeyboardButton(GLFW_KEY_F3, "f3");
    public static KeyboardButton F4 = new KeyboardButton(GLFW_KEY_F4, "f4");
    public static KeyboardButton F5 = new KeyboardButton(GLFW_KEY_F5, "f5");
    public static KeyboardButton F6 = new KeyboardButton(GLFW_KEY_F6, "f6");
    public static KeyboardButton F7 = new KeyboardButton(GLFW_KEY_F7, "f7");
    public static KeyboardButton F8 = new KeyboardButton(GLFW_KEY_F8, "f8");
    public static KeyboardButton F9 = new KeyboardButton(GLFW_KEY_F9, "f9");
    public static KeyboardButton F10 = new KeyboardButton(GLFW_KEY_F10, "f10");
    public static KeyboardButton F11 = new KeyboardButton(GLFW_KEY_F11, "f11");
    public static KeyboardButton F12 = new KeyboardButton(GLFW_KEY_F12, "f12");
    public static KeyboardButton F13 = new KeyboardButton(GLFW_KEY_F13, "f13");
    public static KeyboardButton F14 = new KeyboardButton(GLFW_KEY_F14, "f14");
    public static KeyboardButton F15 = new KeyboardButton(GLFW_KEY_F15, "f15");
    public static KeyboardButton F16 = new KeyboardButton(GLFW_KEY_F16, "f16");
    public static KeyboardButton F17 = new KeyboardButton(GLFW_KEY_F17, "f17");
    public static KeyboardButton F18 = new KeyboardButton(GLFW_KEY_F18, "f18");
    public static KeyboardButton F19 = new KeyboardButton(GLFW_KEY_F19, "f19");
    public static KeyboardButton F20 = new KeyboardButton(GLFW_KEY_F20, "f20");
    public static KeyboardButton F21 = new KeyboardButton(GLFW_KEY_F21, "f21");
    public static KeyboardButton F22 = new KeyboardButton(GLFW_KEY_F22, "f22");
    public static KeyboardButton F23 = new KeyboardButton(GLFW_KEY_F23, "f23");
    public static KeyboardButton F24 = new KeyboardButton(GLFW_KEY_F24, "f24");
    public static KeyboardButton F25 = new KeyboardButton(GLFW_KEY_F25, "f25");
    public static KeyboardButton KP_ZERO = new KeyboardButton(GLFW_KEY_KP_0, "kp zero");
    public static KeyboardButton KP_ONE = new KeyboardButton(GLFW_KEY_KP_1, "kp one");
    public static KeyboardButton KP_TWO = new KeyboardButton(GLFW_KEY_KP_2, "kp two");
    public static KeyboardButton KP_THREE = new KeyboardButton(GLFW_KEY_KP_3, "kp three");
    public static KeyboardButton KP_FOUR = new KeyboardButton(GLFW_KEY_KP_4, "kp four");
    public static KeyboardButton KP_FIVE = new KeyboardButton(GLFW_KEY_KP_5, "kp five");
    public static KeyboardButton KP_SIX = new KeyboardButton(GLFW_KEY_KP_6, "kp six");
    public static KeyboardButton KP_SEVEN = new KeyboardButton(GLFW_KEY_KP_7, "kp seven");
    public static KeyboardButton KP_EIGHT = new KeyboardButton(GLFW_KEY_KP_8, "kp eight");
    public static KeyboardButton KP_NINE = new KeyboardButton(GLFW_KEY_KP_9, "kp nine");
    public static KeyboardButton KP_DECIMAL = new KeyboardButton(GLFW_KEY_KP_DECIMAL, "kp decimal");
    public static KeyboardButton KP_DIVIDE = new KeyboardButton(GLFW_KEY_KP_DIVIDE, "kp divide");
    public static KeyboardButton KP_MULTIPLY = new KeyboardButton(GLFW_KEY_KP_MULTIPLY, "kp multiply");
    public static KeyboardButton KP_SUBTRACT = new KeyboardButton(GLFW_KEY_KP_SUBTRACT, "kp subtract");
    public static KeyboardButton KP_ADD = new KeyboardButton(GLFW_KEY_KP_ADD, "kp add");
    public static KeyboardButton KP_ENTER = new KeyboardButton(GLFW_KEY_KP_ENTER, "kp enter");
    public static KeyboardButton KP_EQUAL = new KeyboardButton(GLFW_KEY_KP_EQUAL, "kp equal");
    public static KeyboardButton LEFT_SHIFT = new KeyboardButton(GLFW_KEY_LEFT_SHIFT, "left shift");
    public static KeyboardButton LEFT_CONTROL = new KeyboardButton(GLFW_KEY_LEFT_CONTROL, "left control");
    public static KeyboardButton LEFT_ALT = new KeyboardButton(GLFW_KEY_LEFT_ALT, "left alt");
    public static KeyboardButton LEFT_SUPER = new KeyboardButton(GLFW_KEY_LEFT_SUPER, "left super");
    public static KeyboardButton RIGHT_SHIFT = new KeyboardButton(GLFW_KEY_RIGHT_SHIFT, "right shift");
    public static KeyboardButton RIGHT_CONTROL = new KeyboardButton(GLFW_KEY_RIGHT_CONTROL, "right control");
    public static KeyboardButton RIGHT_ALT = new KeyboardButton(GLFW_KEY_RIGHT_ALT, "right alt");
    public static KeyboardButton RIGHT_SUPER = new KeyboardButton(GLFW_KEY_RIGHT_SUPER, "right super");
    public static KeyboardButton MENU = new KeyboardButton(GLFW_KEY_MENU, "menu");

    @Getter public int code;
    @Getter public String name;

    KeyboardButton(int code, String name) {
        this.code = code;
        this.name = name;

        KeyboardButton.all.add(this);
    }

    public static KeyboardButton get(int code) {
        for(KeyboardButton key : KeyboardButton.all) {
            if(key.code == code) return key;
        }

        return KeyboardButton.UNKNOWN;
    }

    public static KeyboardButton get(String name) {
        for(KeyboardButton key : KeyboardButton.all) {
            if(key.name.equals(name)) return key;
        }

        return KeyboardButton.UNKNOWN;
    }
}
