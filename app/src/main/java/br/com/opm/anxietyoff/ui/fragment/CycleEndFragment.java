package br.com.opm.anxietyoff.ui.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.ui.activity.CalmDownNowActivity;
import pl.droidsonroids.gif.GifImageView;

public class CycleEndFragment extends Fragment {

    GifImageView gifImageView;

    public CycleEndFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cycle_end, container, false);

        Button button=view.findViewById(R.id.fragment_cycle_end_button_restart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalmDownNowActivity calmDownNowActivity= (CalmDownNowActivity) getContext();
                calmDownNowActivity.restart();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
        setViews();
    }

    private void setViews() {
        int[] resources = {R.drawable.end_gif_0, R.drawable.end_gif_1, R.drawable.end_gif_2,
                R.drawable.end_gif_3, R.drawable.end_gif_4};
        int i = new Random().nextInt(resources.length);
        gifImageView.setImageResource(resources[i]);
    }

    private void getViews() {
        gifImageView = getView().findViewById(R.id.fragment_cycle_end_gif);
    }

}
