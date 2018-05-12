package ru.crypto.android.cryptomonitor.ui.view;

import android.support.v7.widget.RecyclerView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Обработчик скролла для {@link RecyclerView}
 * <p>
 * Используется для скрытия элемента сверху от {@link RecyclerView}
 * <p>
 * <ul>
 * <li> onHide() - вызывается при скролле вверх; </li>
 * <li> onShow() - вызывается при скролле вниз. </li>
 * </ul>
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private boolean showOnlyTopPosition = false;

    public HidingScrollListener() {

    }

    //Конструктор для случая, если данный ScrollListener должен вызывать onShow() только, когда выден самый верхний элемент списка.
    public HidingScrollListener(boolean showOnlyTopPosition) {
        this.showOnlyTopPosition = showOnlyTopPosition;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView.getChildAt(0) == null) {
            return;
        }
        int recyclerPositionTop = recyclerView.getChildAt(0).getTop();

        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible && recyclerPositionTop < 0) {
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible && isShowOnUpScroll(recyclerPositionTop, recyclerView)) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();

    private boolean isShowOnUpScroll(int recyclerPositionTop, RecyclerView recyclerView) {
        return !showOnlyTopPosition || recyclerPositionTop >= 0 && recyclerView.getPaddingTop() == recyclerPositionTop;
    }
}