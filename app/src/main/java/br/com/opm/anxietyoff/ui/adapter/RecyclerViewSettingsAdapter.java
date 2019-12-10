package br.com.opm.anxietyoff.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.model.SettingsItem;

public class RecyclerViewSettingsAdapter extends RecyclerView.Adapter<RecyclerViewSettingsAdapter.ViewHolder>{

    transient private List<SettingsItem> list;
    transient private Context context;

    public RecyclerViewSettingsAdapter(Context context, List<SettingsItem> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_settings, parent, false);
        return new RecyclerViewSettingsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SettingsItem item = list.get(position);
        holder.itemView.setOnClickListener(item.getOnClickListener());
        holder.imageView.setImageResource(item.getImageId());
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubtitle());
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

