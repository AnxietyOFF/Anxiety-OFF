package br.com.opm.anxietyoff.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

import br.com.opm.anxietyoff.R;
import pl.droidsonroids.gif.GifImageView;

public class CongratFragment extends Fragment {

    GifImageView gifImageView;

    public CongratFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_congrat, container, false);
        Button back=view.findViewById(R.id.fragment_congrat_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
        int[] resources = {R.drawable.cong_gif_0, R.drawable.cong_gif_1, R.drawable.cong_gif_2,
                R.drawable.cong_gif_3, R.drawable.cong_gif_4};
        int i = new Random().nextInt(resources.length);
        gifImageView.setImageResource(resources[i]);
    }

    private void getViews() {
        gifImageView = getView().findViewById(R.id.fragment_congrat_gif);
    }

}
