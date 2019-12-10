package br.com.opm.anxietyoff.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;

public class DeleteAccountActivity extends AppCompatActivity {

    EditText pass1, pass2;
    ImageView imageView;
    Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        authentication=new Authentication(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
        setViews();
    }

    private void setViews() {
        int[] resources = {R.drawable.menhera_chan_crying_0, R.drawable.menhera_chan_crying_1, R.drawable.menhera_chan_crying_2};
        int i = new Random().nextInt(resources.length);
        imageView.setImageResource(resources[i]);
    }

    private void findViews() {
        pass1=findViewById(R.id.activity_delete_account_editText_password1);
        pass2=findViewById(R.id.activity_delete_account_editText_password2);
        imageView=findViewById(R.id.activity_delete_account_imageView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickDelete(View view){
        String password1=pass1.getText().toString(), password2=pass2.getText().toString();

        if(password1.equals(password2)){
            authentication.delete(password1);
        }
        else Toast.makeText(this, "Senhas não coincidem", Toast.LENGTH_SHORT).show();

    }

}
