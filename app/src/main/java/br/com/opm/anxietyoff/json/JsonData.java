package br.com.opm.anxietyoff.json;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import br.com.opm.anxietyoff.model.Article;

public class JsonData {

    private List<Article> articles;
    private String fileURL;
    private Context context;

    public JsonData(Context context, String fileURL) {
        this.context = context;
        this.fileURL = fileURL;
        atualizaArticles();
    }

    private void atualizaArticles() {
        Gson gson = new Gson();
        String jsonText = readJsonFile();
        articles = Arrays.asList(gson.fromJson(jsonText, Article[].class));
    }

    public String readJsonFile() {
        String text = "";
        try {
            InputStream is = context.getAssets().open(fileURL);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return text;
    }

    public List<Article> getArticles() {
        return articles;
    }

}
