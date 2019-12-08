package br.com.opm.anxietyoff.ui.selectionTracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.com.opm.anxietyoff.model.DiaryNote;

public class ItemKeyProvider extends androidx.recyclerview.selection.ItemKeyProvider {

    private final List<DiaryNote> list;

    public ItemKeyProvider(int scope, List<DiaryNote> list) {
        super(scope);
        this.list= list;
    }

    @Nullable
    @Override
    public Object getKey(int position) {
        return list.get(position);
    }

    @Override
    public int getPosition(@NonNull Object key) {
        return list.indexOf(key);
    }

}