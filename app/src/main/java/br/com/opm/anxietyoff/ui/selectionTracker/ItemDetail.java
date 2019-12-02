package br.com.opm.anxietyoff.ui.selectionTracker;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

import br.com.opm.anxietyoff.model.DiaryNote;

public class ItemDetail extends ItemDetailsLookup.ItemDetails {
    private final int adapterPosition;
    private final DiaryNote selectionKey;

    public ItemDetail(int adapterPosition, DiaryNote selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Object getSelectionKey() {
        return selectionKey;
    }
}