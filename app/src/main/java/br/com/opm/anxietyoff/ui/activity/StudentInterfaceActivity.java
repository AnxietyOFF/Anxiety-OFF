package br.com.opm.anxietyoff.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.model.Article;
import br.com.opm.anxietyoff.ui.fragment.ArticleFragment;
import br.com.opm.anxietyoff.ui.fragment.HomeFragment;
import br.com.opm.anxietyoff.ui.fragment.RecyclerListFragment;

public class StudentInterfaceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fm;
    private View headerView;
    private TextView name, email;
    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_interface);

        authentication = new Authentication(this);
        fm = getSupportFragmentManager();

        findViews();
        setLayout();
        setProfile();
    }

    private void setProfile() {
        FirebaseUser currentUser = authentication.getCurrentUser();
        email.setText(currentUser.getEmail());
        name.setText(currentUser.getDisplayName());
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.student_interface_drawerLayout);
        navigationView = findViewById(R.id.student_interface_navView);
        headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.nav_header_textView_name);
        email = headerView.findViewById(R.id.nav_header_textView_email);
    }

    private void setLayout() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem item = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(item);
        item.setChecked(true);

        headerView.setClickable(false);
    }

    public void onClickSettings(View view) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(this, RecyclerListActivity.class);
        String[] list = {"settings"};
        intent.putExtra("adapter", list);
        intent.putExtra("title", "Settings");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void transaction(Fragment frag) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.student_frameLayout, frag);
        ft.commit();
    }

    public void setArticleFragment(Article article) {
        transaction(new ArticleFragment(article));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (!menuItem.isChecked()) {

            switch (menuItem.getItemId()) {
                case R.id.student_nav_item_home: {
                    transaction(new HomeFragment());
                    toolbar.setTitle(R.string.app_name);
                    break;
                }
                case R.id.student_nav_item_entender_ansiedade: {
                    String[] list = {"article", "entender_ansiedade.json"};
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("adapter", list);
                    RecyclerListFragment frag = new RecyclerListFragment();
                    frag.setArguments(bundle);
                    transaction(frag);
                    toolbar.setTitle("Para entender");
                    break;
                }
                case R.id.student_nav_item_tratar_ansiedade: {
                    Toast.makeText(this, "Tratar Ansiedade", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Para tratar");
                    break;
                }
                case R.id.student_nav_item_meditacao: {
                    Toast.makeText(this, "Meditação", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Meditação");
                    break;
                }
                case R.id.student_nav_item_respiracao: {
                    Toast.makeText(this, "Respiração", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Respiração");
                    break;
                }
                case R.id.student_nav_item_exercicios: {
                    Toast.makeText(this, "Exercícios", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Exercícios");
                    break;
                }
                case R.id.student_nav_item_sons: {
                    Toast.makeText(this, "Sons Relaxantes", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Sons");
                    break;
                }
                case R.id.student_nav_item_diario: {
                    Toast.makeText(this, "Diário", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Diário");
                    break;
                }
                case R.id.student_nav_item_teste: {
                    Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Teste");
                    break;
                }
                case R.id.student_nav_item_relatorio: {
                    Toast.makeText(this, "Relatório", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Relatório");
                    break;
                }
                default: {
                    Toast.makeText(this, "Ue??", Toast.LENGTH_SHORT).show();
                }
            }
        } else Toast.makeText(this, "Reselect", Toast.LENGTH_SHORT).show();

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}
