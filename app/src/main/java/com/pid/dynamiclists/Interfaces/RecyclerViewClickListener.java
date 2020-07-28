package com.pid.dynamiclists.Interfaces;

import android.view.View;

/**
 * The interface Recycler view click listener.
 * @author Ilya Mishin
 */
public interface RecyclerViewClickListener {
    /**
     * Recycler view list clicked.
     *
     * @param v        the view
     * @param position the position
     */
    public void recyclerViewListClicked(View v, int position);
}