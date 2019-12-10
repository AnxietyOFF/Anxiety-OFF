package br.com.opm.anxietyoff.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.model.Answers;
import br.com.opm.anxietyoff.model.Questions;

public class RecyclerViewBeckAnxietyInventoryAdapter extends RecyclerView.Adapter<RecyclerViewBeckAnxietyInventoryAdapter.ViewHolder>{

    private Questions questions;
    private Context context;
    private Answers answers;

    public RecyclerViewBeckAnxietyInventoryAdapter(Context context, Questions questions) {
        this.questions = questions;
        this.context = context;
        answers=new Answers(questions.getTitles().size());
    }

    public Answers getAnswers() {
        answers.setupDate();
        answers.setupSum();
        return answers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_beck_test, parent, false);

        SeekBar seekBar=view.findViewById(R.id.recycler_view_item_beck_test_answer);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int i = (int)view.getTag();
                answers.getVote().set(i, progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        return new RecyclerViewBeckAnxietyInventoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.title.setText(questions.getTitles().get(position));
    }

    @Override
    public int getItemCount() {
        return questions.getTitles().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        SeekBar seekBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycler_view_item_beck_test_question);
            seekBar=itemView.findViewById(R.id.recycler_view_item_beck_test_answer);
        }

    }
}
