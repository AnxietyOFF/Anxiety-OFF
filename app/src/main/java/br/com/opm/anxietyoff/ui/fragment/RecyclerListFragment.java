package br.com.opm.anxietyoff.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.model.Answers;
import br.com.opm.anxietyoff.ui.adapter.AdapterCreator;
import br.com.opm.anxietyoff.ui.adapter.RecyclerViewReportAdapter;

public class RecyclerListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

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
        progressBar=getView().findViewById(R.id.generic_recyclerView_progressBar);
        recyclerView = getView().findViewById(R.id.generic_recyclerView);
    }

    private void setRecyclerView() {
        AdapterCreator adapterCreator = new AdapterCreator(getContext());
        String[] adapters = getArguments().getStringArray("adapter");
        if(adapters[0].equals("report")) progressBar.setVisibility(View.VISIBLE);
        RecyclerView.Adapter adapter = adapterCreator.adapterChooser(adapters, RecyclerListFragment.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void updateReportRecyclerView(List<Answers> list){
        progressBar.setVisibility(View.GONE);
        RecyclerViewReportAdapter adapter = (RecyclerViewReportAdapter) recyclerView.getAdapter();
        adapter.changeList(list);
    }

}
