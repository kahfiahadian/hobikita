package com.example.rpl.hobi_kita_rev;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

/**
 * Created by boncelius on 09/05/2018.
 */

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {
    PhotoAdapter adapters;

    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelper(PhotoAdapter adapters) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.adapters = adapters;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapters.dissmiss(viewHolder.getAdapterPosition());
    }
}
