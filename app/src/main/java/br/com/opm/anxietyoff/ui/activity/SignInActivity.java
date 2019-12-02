package br.com.opm.anxietyoff.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.model.User;

public class SignInActivity extends AppCompatActivity {

    Authentication authentication;
    EditText editName, editEmail, editPass, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        authentication = new Authentication(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pickEditText();
    }

    private void pickEditText() {
        editName = findViewById(R.id.signIn_editText_name);
        editEmail = findViewById(R.id.signIn_editText_email);
        editPass = findViewById(R.id.signIn_editText_pass);
        editPhone = findViewById(R.id.signIn_editText_phone);
    }

    public void onClickRegister(View view) {
        String name = editName.getText().toString(), email = editEmail.getText().toString(),
                pass = editPass.getText().toString(), phone = editPhone.getText().toString();

        User user = new User(name, email, pass, phone);

        authentication.signIn(user);
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
