package br.com.opm.anxietyoff.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.ui.fragment.BreathFragment;
import br.com.opm.anxietyoff.ui.fragment.CongratFragment;
import br.com.opm.anxietyoff.ui.fragment.CycleEndFragment;
import br.com.opm.anxietyoff.ui.fragment.HearFragment;
import br.com.opm.anxietyoff.ui.fragment.SeeFragment;
import br.com.opm.anxietyoff.ui.fragment.SmellFragment;
import br.com.opm.anxietyoff.ui.fragment.TasteFragment;
import br.com.opm.anxietyoff.ui.fragment.TouchFragment;

public class CalmDownNowActivity extends AppCompatActivity {

    private int step=0;
    private FragmentManager fm;
    private ImageButton buttonBefore, buttonNext;
    private ConstraintLayout bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calm_down_now);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Acalmar agora");

        fm = getSupportFragmentManager();
        findViews();
        updateFrame();
    }

    private void findViews() {
        buttonBefore=findViewById(R.id.activity_calm_down_imageButton_before);
        buttonNext=findViewById(R.id.activity_calm_down_imageButton_next);
        bottomBar=findViewById(R.id.activity_calm_down_bottom_bar);
    }

    private void verifyExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação de saída");
        builder.setMessage("Tem certeza que quer deixar o ciclo atual de relaxamento?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        AlertDialog alerta = builder.create();
        alerta.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if(step<0) finish();
            else verifyExit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(step<0) super.onBackPressed();
        else verifyExit();
    }

    public void restart(){
        step=0;
        buttonNext.setVisibility(View.VISIBLE);
        updateFrame();
    }

    public void onClickBefore(View view){
        step--;
        updateFrame();
    }

    public void onClickNext(View view){
        step++;
        updateFrame();
    }

    public void onClickDone(View view){
        step=-1;
        bottomBar.setVisibility(View.GONE);
        transaction(new CongratFragment());
    }

    public void transaction(Fragment frag) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.activity_calm_down_frameLayout, frag);
        ft.commit();
    }

    private void updateFrame(){

        Fragment frag;

        switch (step){
            case 0:{
                buttonBefore.setVisibility(View.GONE);
                frag=new BreathFragment();
                break;
            }
            case 1:{
                buttonBefore.setVisibility(View.VISIBLE);
                frag=new SeeFragment();
                break;
            }
            case 2:{
                frag=new TouchFragment();
                break;
            }
            case 3:{
                frag=new HearFragment();
                break;
            }
            case 4:{
                frag=new SmellFragment();
                break;
            }
            case 5:{
                buttonNext.setVisibility(View.VISIBLE);
                frag=new TasteFragment();
                break;
            }
            default:{
                buttonNext.setVisibility(View.GONE);
                frag=new CycleEndFragment();
                break;
            }
        }

        transaction(frag);
    }

}
