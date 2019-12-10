package br.com.opm.anxietyoff.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.CloudFirestore;
import br.com.opm.anxietyoff.lib.DateFactory;
import br.com.opm.anxietyoff.model.Answers;
import br.com.opm.anxietyoff.ui.fragment.RecyclerListFragment;

public class RecyclerViewReportAdapter extends RecyclerView.Adapter<RecyclerViewReportAdapter.ViewHolder>{

    private List<Answers> list;
    RecyclerListFragment frag;
    private Context context;
    CloudFirestore firestore;

    public RecyclerViewReportAdapter(Context context, List<Answers> list, RecyclerListFragment frag) {
        this.list = list;
        this.frag=frag;
        this.context = context;
        firestore=new CloudFirestore(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_report, parent, false);

        ImageButton delete=view.findViewById(R.id.recyclerView_item_report_imageButton_delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmação de saída");
                builder.setMessage("Tem certeza que quer apagar esse relatório de teste de Beck?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        int position = (int) view.getTag();
                        deleteUpdateList(position);
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

                AlertDialog alerta = builder.create();
                alerta.show();
            }
        });

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();

            }
        });*/

        return new RecyclerViewReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        Answers answers = list.get(position);
        int sum=answers.getSum(), color;

        holder.number.setText(String.valueOf(sum));

        if(sum<=10){
            color=ResourcesCompat.getColor(context.getResources(),R.color.sum_0, null);
            holder.title.setText("Grau mínimo de ansiedade"); holder.subTitle.setText("0-10");
        }
        else if(sum<=21){
            color=ResourcesCompat.getColor(context.getResources(),R.color.sum_1, null);
            holder.title.setText("Ansiedade Leve"); holder.subTitle.setText("11-21");
        }
        else if(sum<=35){
            color=ResourcesCompat.getColor(context.getResources(),R.color.sum_2, null);
            holder.title.setText("Ansiedade moderada"); holder.subTitle.setText("22-35");
        }
        else{
            color=ResourcesCompat.getColor(context.getResources(),R.color.sum_3, null);
            holder.title.setText("Ansiedade severa"); holder.subTitle.setText("36+");
        }

        holder.number.setTextColor(color);

        Date before=answers.getDate(), now= Calendar.getInstance().getTime();

        if(DateFactory.isSameDate(before, now)){
            holder.date.setText(DateFactory.onlyTimeToString(before));
        }
        else{
            holder.date.setText(DateFactory.onlyDateToString(before));
        }
    }

    public void deleteUpdateList(int i){
        firestore.deleteBeckTestAnswers(list.get(i));
        list.remove(i);
        notifyItemRemoved(i);
    }

    public void changeList(List<Answers> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView number, title, subTitle, date;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recyclerView_item_report_textView_title);
            subTitle = itemView.findViewById(R.id.recyclerView_item_report_textView_subTitle);
            number=itemView.findViewById(R.id.recyclerView_item_report_textView_number);
            date=itemView.findViewById(R.id.recyclerView_item_report_textView_date);
        }

    }
}
