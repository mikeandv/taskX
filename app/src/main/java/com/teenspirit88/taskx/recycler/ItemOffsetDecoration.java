package com.teenspirit88.taskx.recycler;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offsetTopBottom;
    private int offsetLeftRight;

    public ItemOffsetDecoration(int offsetTopBottom, int offsetLeftRight) {
        this.offsetLeftRight = offsetLeftRight;
        this.offsetTopBottom = offsetTopBottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //Для первого элемента верхний отступ проставляем увеличенным на 2
        if(parent.getChildAdapterPosition(view) == 0) {
            outRect.right = offsetLeftRight;
            outRect.left = offsetLeftRight;
            outRect.top = offsetTopBottom * 2;
            outRect.bottom = offsetTopBottom;
        } else {
            outRect.right = offsetLeftRight;
            outRect.left = offsetLeftRight;
            outRect.top = offsetTopBottom;
            outRect.bottom = offsetTopBottom;
        }
    }
}
