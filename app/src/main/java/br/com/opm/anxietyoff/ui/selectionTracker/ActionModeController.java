package br.com.opm.anxietyoff.ui.selectionTracker;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.selection.SelectionTracker;

public class ActionModeController implements ActionMode.Callback {

    private final Context context;
    private final SelectionTracker selectionTracker;

    public ActionModeController(Context context, SelectionTracker selectionTracker) {
        this.context = context;
        this.selectionTracker = selectionTracker;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        selectionTracker.clearSelection();
    }
}
