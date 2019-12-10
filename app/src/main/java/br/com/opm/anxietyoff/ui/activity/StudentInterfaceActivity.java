package br.com.opm.anxietyoff.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.model.Article;
import br.com.opm.anxietyoff.ui.fragment.ArticleFragment;
import br.com.opm.anxietyoff.ui.fragment.BreathFragment;
import br.com.opm.anxietyoff.ui.fragment.HomeFragment;
import br.com.opm.anxietyoff.ui.fragment.RecyclerListFragment;
import br.com.opm.anxietyoff.ui.fragment.TestArticleFragment;

public class StudentInterfaceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fm;
    private View headerView;
    private TextView name, email;
    private Authentication authentication;
    private ImageView profileImage, bottomBarFake;
    private boolean painted;

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

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.student_interface_drawerLayout);
        navigationView = findViewById(R.id.student_interface_navView);
        headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.nav_header_textView_name);
        email = headerView.findViewById(R.id.nav_header_textView_email);
        profileImage = headerView.findViewById(R.id.nav_header_imageView_profile);
        bottomBarFake=findViewById(R.id.activity_student_interface_bottom_bar_fake);
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

    private void setProfile() {
        FirebaseUser currentUser = authentication.getCurrentUser();
        currentUser.getPhotoUrl();
        email.setText(currentUser.getEmail());
        name.setText(currentUser.getDisplayName());
        Glide.with(this).load(authentication.getCurrentUser().getPhotoUrl()).error(R.drawable.generic_user)
                .apply(RequestOptions.circleCropTransform()).into(profileImage);
    }

    public void onClickSettings(View view) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(this, RecyclerListActivity.class);
        String[] list = {"settings"};
        intent.putExtra("adapter", list);
        intent.putExtra("title", "Settings");
        startActivity(intent);
    }

    public void onClickCalmDownNow(View view) {
        startActivity(new Intent(this, CalmDownNowActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(fm.getBackStackEntryCount()==1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment frag) {
        fm.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction()
                .replace(R.id.student_frameLayout, frag)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }

    public void addFragment(Fragment frag){
        fm.beginTransaction()
                .replace(R.id.student_frameLayout, frag)
                .addToBackStack(null)
                .commit();
    }

    public void setArticleFragment(Article article) {
        addFragment(new ArticleFragment(article));
    }

    public void setTestArticleFragment(Article article) {
        addFragment(TestArticleFragment.newInstance(article));
    }

    private void recyclerViewBuilder(String[] list){
        Bundle bundle = new Bundle();
        bundle.putStringArray("adapter", list);
        RecyclerListFragment frag = new RecyclerListFragment();
        frag.setArguments(bundle);
        replaceFragment(frag);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (!menuItem.isChecked()) {

            switch (menuItem.getItemId()) {
                case R.id.student_nav_item_home:{
                    replaceFragment(new HomeFragment());
                    toolbar.setTitle(R.string.app_name);
                    break;
                }
                case R.id.student_nav_item_entender_ansiedade: {
                    String[] list = {"lesson", "entender_ansiedade.json"};
                    recyclerViewBuilder(list);
                    toolbar.setTitle("Para entender");
                    break;
                }
                case R.id.student_nav_item_tratar_ansiedade: {
                    String[] list = {"lesson", "tratar_ansiedade.json"};
                    recyclerViewBuilder(list);
                    toolbar.setTitle("Para tratar");
                    break;
                }
                case R.id.student_nav_item_meditacao: {
                    Toast.makeText(this, "Meditação", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("Meditação");
                    break;
                }
                case R.id.student_nav_item_respiracao: {
                    BreathFragment frag = new BreathFragment();
                    replaceFragment(frag);
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
                    String[] list = {"test", "testes_ansiedade.json"};
                    recyclerViewBuilder(list);
                    toolbar.setTitle("Testes");
                    break;
                }
                case R.id.student_nav_item_relatorio: {
                    String[] list = {"report"};
                    recyclerViewBuilder(list);
                    toolbar.setTitle("Relatório");
                    break;
                }
                default: {
                    Toast.makeText(this, "Ue??", Toast.LENGTH_SHORT).show();
                }
            }

            if(menuItem.getItemId()==R.id.student_nav_item_home) {
                painted=true;
                bottomBarFake.setVisibility(View.VISIBLE);
            }
            else if(painted){
                painted=false;
                bottomBarFake.setVisibility(View.GONE);
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}
