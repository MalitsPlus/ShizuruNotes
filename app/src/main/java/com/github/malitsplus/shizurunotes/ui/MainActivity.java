package com.github.malitsplus.shizurunotes.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.App;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.common.UpdateManager;
import com.github.malitsplus.shizurunotes.db.DBHelper;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.malitsplus.shizurunotes.ui.charalist.CharaListFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, UpdateManager.IFragmentCallBack {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] STORAGE_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public UpdateManager updateManager;
    private SharedViewModel sharedViewModel;

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(App.localeManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_chara, R.id.nav_clan_battle, R.id.nav_slideshow,
                R.id.nav_setting, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(toolbar, navController);
        NavigationUI.setupWithNavController(navigationView, navController);


        DBHelper.with(getApplication());
        I18N.application = getApplication();

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);


        updateManager = new UpdateManager(this, drawer);
        updateManager.setIFragmentCallBack(this);

        if(checkStoragePermission())
            updateManager.checkDatabaseVersion();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public boolean checkStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, REQUEST_EXTERNAL_STORAGE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateManager.checkDatabaseVersion();
                } else {
                    new MaterialDialog(this, MaterialDialog.getDEFAULT_BEHAVIOR())
                            .title(R.string.permission_request_explanation_title, null)
                            .message(R.string.permission_request_explanation_text, null,null)
                            .cancelOnTouchOutside(false)
                            .positiveButton(R.string.text_ok, null, materialDialog -> {
                                ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, REQUEST_EXTERNAL_STORAGE);
                                return null;
                            })
                            .show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void dbUpdateFinished() {
        sharedViewModel.loadData();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0);
        if(fragment instanceof CharaListFragment){
            ((CharaListFragment)fragment).updateList();
        }
    }
}
