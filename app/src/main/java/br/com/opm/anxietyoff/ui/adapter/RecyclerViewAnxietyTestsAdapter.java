package br.com.opm.anxietyoff.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.model.Article;
import br.com.opm.anxietyoff.ui.activity.StudentInterfaceActivity;

public class RecyclerViewAnxietyTestsAdapter extends RecyclerView.Adapter<RecyclerViewAnxietyTestsAdapter.ViewHolder>{

    private List<Article> list;
    private Context context;

    public RecyclerViewAnxietyTestsAdapter(Context context, List<Article> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_generic, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                StudentInterfaceActivity studentInterfaceActivity = (StudentInterfaceActivity) context;
                studentInterfaceActivity.setTestArticleFragment(list.get(position));
            }
        });
        return new RecyclerViewAnxietyTestsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        Article article = list.get(position);

        Glide.with(context).load(article.getImages().get(0)).error(R.drawable.worried)
                .apply(RequestOptions.circleCropTransform()).into(holder.imageView);

        holder.title.setText(article.getTitle());
        holder.subTitle.setText(article.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, subTitle;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycler_view_item_textView_title);
            subTitle = itemView.findViewById(R.id.recycler_view_item_textView_subTitle);
            imageView = itemView.findViewById(R.id.recycler_view_item_imageView);
        }

    }
}
