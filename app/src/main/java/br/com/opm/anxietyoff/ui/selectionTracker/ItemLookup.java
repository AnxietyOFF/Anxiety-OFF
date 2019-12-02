package br.com.opm.anxietyoff.ui.selectionTracker;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import br.com.opm.anxietyoff.ui.adapter.RecyclerViewDiaryAdapter;

public class ItemLookup extends ItemDetailsLookup {

    private final RecyclerView recyclerView;

    public ItemLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
            if (holder instanceof RecyclerViewDiaryAdapter.ViewHolder) {
                return ((RecyclerViewDiaryAdapter.ViewHolder) holder).getItemDetails();
            }
        }

        return null;
    }
}