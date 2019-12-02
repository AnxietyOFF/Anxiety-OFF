package br.com.opm.anxietyoff.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.ui.adapter.AdapterCreator;

public class RecyclerListFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_generic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
        setRecyclerView();
    }


    private void getViews() {
        recyclerView = getView().findViewById(R.id.generic_recyclerView);
    }

    private void setRecyclerView() {
        AdapterCreator adapterCreator = new AdapterCreator(getContext());
        RecyclerView.Adapter adapter = adapterCreator.adapterChooser(getArguments().getStringArray("adapter"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

}
