package br.com.opm.anxietyoff.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.model.Article;
import br.com.opm.anxietyoff.ui.activity.BeckAnxietyInventoryActivity;

public class TestArticleFragment extends Fragment {

    private static Article article;
    private ImageView icon;
    private TextView title, subTitle, text;

    public TestArticleFragment() {
    }

    public static TestArticleFragment newInstance(Article article) {
        Bundle args = new Bundle();
        args.putSerializable("article", article);
        TestArticleFragment f = new TestArticleFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_article, container, false);
        article= (Article) getArguments().getSerializable("article");
        Button doTest=view.findViewById(R.id.fragment_test_button_do_test);
        doTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BeckAnxietyInventoryActivity.class));
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

    private void getViews() {
        icon = getView().findViewById(R.id.fragment_test_icon);
        title = getView().findViewById(R.id.fragment_test_title);
        subTitle = getView().findViewById(R.id.fragment_test_subTitle);
        text = getView().findViewById(R.id.fragment_test_text);
    }

    private void setViews() {
        Glide.with(this).load(article.getImages().get(0)).error(R.drawable.worried)
                .apply(RequestOptions.circleCropTransform()).into(icon);
        title.setText(article.getTitle());
        subTitle.setText(article.getSubtitle());
        text.setText(Html.fromHtml(article.getText()));
    }

}
