package com.example.ermolaenkoalex.nytimes.ui.newslist;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Get from https://stackoverflow.com/questions/29146781/decorating-recyclerview-with-gridlayoutmanager-to-display-divider-between-item
public class ItemDecorationNewsList extends RecyclerView.ItemDecoration {

    private int sizeGridSpacingPx;
    private int gridSize;
    private boolean needLeftSpacing = false;

    public ItemDecorationNewsList(int gridSpacingPx, int gridSize) {
        this.sizeGridSpacingPx = gridSpacingPx;
        this.gridSize = gridSize;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int frameWidth = (int) ((parent.getWidth() - (float) sizeGridSpacingPx * (gridSize - 1)) / gridSize);
        int padding = parent.getWidth() / gridSize - frameWidth;
        int itemPosition = parent.getChildLayoutPosition(view);
        if (itemPosition < gridSize) {
            outRect.top = 0;
        } else {
            outRect.top = sizeGridSpacingPx;
        }
        if (itemPosition % gridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            needLeftSpacing = true;
        } else if ((itemPosition + 1) % gridSize == 0) {
            needLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (needLeftSpacing) {
            needLeftSpacing = false;
            outRect.left = sizeGridSpacingPx - padding;
            if ((itemPosition + 2) % gridSize == 0) {
                outRect.right = sizeGridSpacingPx - padding;
            } else {
                outRect.right = sizeGridSpacingPx / 2;
            }
        } else if ((itemPosition + 2) % gridSize == 0) {
            needLeftSpacing = false;
            outRect.left = sizeGridSpacingPx / 2;
            outRect.right = sizeGridSpacingPx - padding;
        } else {
            needLeftSpacing = false;
            outRect.left = sizeGridSpacingPx / 2;
            outRect.right = sizeGridSpacingPx / 2;
        }
        outRect.bottom = 0;
    }
}
