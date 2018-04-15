package com.gongyunhao.nowmeeting.deal;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/03/30
 *     desc   : 软键盘 观察者
 * </pre>
 */
public interface KeyBoardObserver {
    void update(boolean keyBoardVisibile, int keyBoardHeight);
}
