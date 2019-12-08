package br.com.opm.anxietyoff.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.json.JsonData;
import br.com.opm.anxietyoff.model.SettingsItem;
import br.com.opm.anxietyoff.ui.activity.LoginActivity;

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
            case "article": {
                return jsonAdapter(adapterOptions[1]);
            }
            default:
                return null;
        }
    }

    private RecyclerViewArticleAdapter jsonAdapter(String fileURL) {
        JsonData jsonData = new JsonData(context, fileURL);
        return new RecyclerViewArticleAdapter(context, jsonData.getArticles());
    }

    private RecyclerViewSettingsAdapter SettingsAdapter() {
        List<SettingsItem> list = new ArrayList<>();
        View.OnClickListener aux;

        //Sair
        aux = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

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

}
