package br.com.opm.anxietyoff.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;

public class LoginActivity extends AppCompatActivity {

    Authentication authentication;
    EditText editEmail, editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authentication = new Authentication(this);
        pickEditText();
    }

    private void pickEditText() {
        editEmail = findViewById(R.id.login_editText_email);
        editPass = findViewById(R.id.login_editText_pass);
    }

    public void onClickSwitch(View view) {
        Switch aux = (Switch) view;

        if (aux.isChecked()) aux.setText("Estudante");
        else aux.setText("Docente");

    }

    public void onClickForgotPassword(View view){
        Intent intent = new Intent(this, ForgottenPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onClickSignIn(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void onClickLogin(View view) {
        String email = editEmail.getText().toString(), password = editPass.getText().toString();

        authentication.login(email, password);
    }

}
