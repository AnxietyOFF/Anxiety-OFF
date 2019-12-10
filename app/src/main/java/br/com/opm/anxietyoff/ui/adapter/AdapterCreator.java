package br.com.opm.anxietyoff.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.firebase.CloudFirestore;
import br.com.opm.anxietyoff.json.JsonDataArticles;
import br.com.opm.anxietyoff.json.JsonDataBeckTest;
import br.com.opm.anxietyoff.model.Answers;
import br.com.opm.anxietyoff.model.Questions;
import br.com.opm.anxietyoff.model.SettingsItem;
import br.com.opm.anxietyoff.ui.activity.DeleteAccountActivity;
import br.com.opm.anxietyoff.ui.activity.LoginActivity;
import br.com.opm.anxietyoff.ui.fragment.RecyclerListFragment;

public class AdapterCreator {

    Context context;

    public AdapterCreator(Context context) {
        this.context = context;
    }

    public RecyclerView.Adapter adapterChooser(String[] adapterOptions) {
        switch (adapterOptions[0]) {
            case "settings": {
                return SettingsAdapter();
            }
            case "lesson": {
                return lessonsAdapter(adapterOptions[1]);
            }
            case "test":{
                return testsAdapter(adapterOptions[1]);
            }
            default:
                return null;
        }
    }

    public RecyclerView.Adapter adapterChooser(String[] adapterOptions, Fragment frag) {
        switch (adapterOptions[0]) {
            case "settings": {
                return SettingsAdapter();
            }
            case "lesson": {
                return lessonsAdapter(adapterOptions[1]);
            }
            case "test":{
                return testsAdapter(adapterOptions[1]);
            }
            case "report":{
                return reportsAdapter((RecyclerListFragment) frag);
            }
            default:
                return null;
        }
    }

    private RecyclerViewReportAdapter reportsAdapter(RecyclerListFragment frag) {

        List<Answers> list=new ArrayList<>();

        new CloudFirestore(context, frag).getBeckTestAnswers();

        return new RecyclerViewReportAdapter(context, list, frag);
    }

    private RecyclerViewAnxietyTestsAdapter testsAdapter(String fileURL) {
        JsonDataArticles jsonDataArticles = new JsonDataArticles(context, fileURL);
        return new RecyclerViewAnxietyTestsAdapter(context, jsonDataArticles.getArticles());
    }

    private RecyclerViewLessonsAdapter lessonsAdapter(String fileURL) {
        JsonDataArticles jsonDataArticles = new JsonDataArticles(context, fileURL);
        return new RecyclerViewLessonsAdapter(context, jsonDataArticles.getArticles());
    }

    private RecyclerViewSettingsAdapter SettingsAdapter() {
        List<SettingsItem> list = new ArrayList<>();
        View.OnClickListener aux;

        //Excluir conta
        aux=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(">>>excluir");
                context.startActivity(new Intent(context, DeleteAccountActivity.class));
            }
        };
        list.add(new SettingsItem("Excluir Conta", "Apagar todos os dados da conta e deslogar", aux, R.drawable.ic_delete_account));

        //Sair
        aux = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                System.out.println(">>>logoff");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmação de logoff");
                builder.setMessage("Deseja mesmo deslogar da conta atual?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        new Authentication(context).logoff();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

                AlertDialog alerta = builder.create();
                alerta.show();
            }
        };
        list.add(new SettingsItem("Sair", "Deslogar da conta atual", aux, R.drawable.ic_exit));

        return new RecyclerViewSettingsAdapter(context, list);

    }

    public RecyclerViewBeckAnxietyInventoryAdapter beckTestAdapter(){
        Questions list=new JsonDataBeckTest(context, "teste_beck_questoes.json").getQuestions();

        return new RecyclerViewBeckAnxietyInventoryAdapter(context, list);

    }

}
