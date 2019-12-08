package br.com.opm.anxietyoff.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;

public class ForgottenPasswordActivity extends AppCompatActivity {

    private Authentication authentication;
    private EditText email;
    private Button find;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        getSupportActionBar().setTitle("Encontrar sua conta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        authentication = new Authentication(this);
        findViews();

        configEditText();
    }

    private void findViews() {
        email = findViewById(R.id.activity_forgotten_password_editText);
        find = findViewById(R.id.activity_forgotten_password_button);
        message = findViewById(R.id.activity_forgotten_password_textView);
    }

    private void configEditText() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = String.valueOf(s);
                if (text.equals("")) {
                    message.setVisibility(View.VISIBLE);
                    find.setVisibility(View.GONE);
                } else {
                    message.setVisibility(View.GONE);
                    find.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onClickFind(View view) {
        authentication.changePassword(email.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
