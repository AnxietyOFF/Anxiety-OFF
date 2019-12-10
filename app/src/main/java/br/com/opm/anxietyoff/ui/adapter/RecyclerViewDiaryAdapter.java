package br.com.opm.anxietyoff.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.model.DiaryNote;
import br.com.opm.anxietyoff.ui.selectionTracker.ItemDetail;

public class RecyclerViewDiaryAdapter extends RecyclerView.Adapter<RecyclerViewDiaryAdapter.ViewHolder> {

    private SelectionTracker selectionTracker;
    private List<DiaryNote> list;
    private Context context;

    public RecyclerViewDiaryAdapter(Context context, List<DiaryNote> list) {
        this.list = list;
        this.context = context;
    }

    public void setSelectionTracker(SelectionTracker selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_diary, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                /*
                StudentInterfaceActivity studentInterfaceActivity = (StudentInterfaceActivity) context;
                studentInterfaceActivity.setArticleFragment(list.get(position));
                */
            }
        });
        return new RecyclerViewDiaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryNote note = list.get(position);
        holder.bind(note, selectionTracker.isSelected(note));

        holder.title.setText(note.getTitle());
        holder.subTitle.setText(note.getSubTitle());
        holder.preview.setText(note.getPreview());
        holder.date.setText(note.getDate());

        if(note.isStarred()) holder.favorite.setImageResource(R.drawable.ic_favorite_on);
        else holder.favorite.setImageResource(R.drawable.ic_favorite_off);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void atualiza(List<DiaryNote> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(DiaryNote note) {
        list.remove(note);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, subTitle, preview, date;
        ImageButton favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.recyclerView_item_diary_textView_title);
            subTitle=itemView.findViewById(R.id.recyclerView_item_diary_textView_subTitle);
            preview=itemView.findViewById(R.id.recyclerView_item_diary_textView_preview);
            date=itemView.findViewById(R.id.recyclerView_item_diary_imageButton_date);
            favorite=itemView.findViewById(R.id.recyclerView_item_diary_imageButton_favorite);
        }

        public ItemDetailsLookup.ItemDetails getItemDetails() {
            return new ItemDetail(getAdapterPosition(), list.get(getAdapterPosition()));
        }

        public final void bind(DiaryNote item, boolean isActive) {
            itemView.setActivated(isActive);
        }

    }


}
