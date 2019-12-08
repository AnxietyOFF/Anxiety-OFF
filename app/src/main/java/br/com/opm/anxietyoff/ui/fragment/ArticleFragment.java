package br.com.opm.anxietyoff.ui.fragment;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.model.Article;

public class ArticleFragment extends Fragment {

    private Article article;
    private ImageView icon;
    private TextView title, subTitle, text;

    public ArticleFragment(Article article) {
        this.article = article;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
        setViews();
    }

    private void getViews() {
        icon = getView().findViewById(R.id.fragment_article_icon);
        title = getView().findViewById(R.id.fragment_article_title);
        subTitle = getView().findViewById(R.id.fragment_article_subTitle);
        text = getView().findViewById(R.id.fragment_article_text);
    }

    private void setViews() {
        Glide.with(this).load(article.getImages().get(0)).error(R.drawable.worried)
                .apply(RequestOptions.circleCropTransform()).into(icon);
        title.setText(article.getTitle());
        subTitle.setText(article.getSubtitle());
        text.setText(Html.fromHtml(article.getText()));
    }

}
