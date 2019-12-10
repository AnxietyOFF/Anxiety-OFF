package br.com.opm.anxietyoff.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.firebase.CloudFirestore;

public class SplashActivity extends AppCompatActivity {

    Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new CloudFirestore(this).configureFirestoreSettings();
        authentication =new Authentication(this);
        verifyLog();
    }

    private void verifyLog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(authentication.check()) startActivity(new Intent(getBaseContext(), StudentInterfaceActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                else startActivity(new Intent(getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
