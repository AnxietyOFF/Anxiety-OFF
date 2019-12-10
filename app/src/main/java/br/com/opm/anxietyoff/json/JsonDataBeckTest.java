package br.com.opm.anxietyoff.json;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import br.com.opm.anxietyoff.model.Questions;

public class JsonDataBeckTest {

    private Questions questions;
    private String fileURL;
    private Context context;

    public JsonDataBeckTest(Context context, String fileURL) {
        this.context = context;
        this.fileURL = fileURL;
        atualizaQuestions();
    }

    private void atualizaQuestions() {
        Gson gson = new Gson();
        String jsonText = readJsonFile();
        questions = gson.fromJson(jsonText, Questions.class);
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

    public Questions getQuestions() {
        return questions;
    }

}
