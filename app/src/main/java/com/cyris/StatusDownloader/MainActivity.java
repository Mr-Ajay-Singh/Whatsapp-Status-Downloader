package com.cyris.StatusDownloader;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.cyris.StatusDownloader.ui.adapters.WhatsappFragmentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity  {

    private int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;
    private AppBarConfiguration mAppBarConfiguration;
    ViewPager mainViewPager;
    WhatsappFragmentAdapter whatsappFragmentAdapter;
    TabLayout tabLayout;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbarInMain);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.navigtion_svg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });



        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                return false;
            }
        });

       FloatingActionButton fab = findViewById(R.id.fabInMain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                if(intent!=null)
                {
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Whatsapp not Installed",Toast.LENGTH_SHORT).show();
                }


            }
        });
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_view);

     tabLayout = findViewById(R.id.tabLayoutInMain);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.statusInDrawer)
                {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                if(menuItem.getItemId() == R.id.howToUseInDrawer)
                {
                    drawer.closeDrawer(Gravity.LEFT);
                    dialog.show();

                }

                if(menuItem.getItemId() == R.id.shareInDrawer)
                {
                    drawer.closeDrawer(Gravity.LEFT);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp");
                    if(intent.resolveActivity(getPackageManager())!=null)
                    {
                        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Check this app to Download whatsapp status image/video in gallery with status downloader app download it:-\n\nhttps://play.google.com/store/apps/details?id=com.cyris.whatsappstatudownload");
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Whatsapp not Installed",Toast.LENGTH_SHORT).show();
                    }

                }

                if(menuItem.getItemId() == R.id.rateUsInDrawer)
                {

                   // Toast.makeText(MainActivity.this,"I will work Later",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.cyris.whatsappstatudownload")));
                    drawer.closeDrawer(Gravity.LEFT);

                }

                if(menuItem.getItemId() == R.id.privacyPolicyInDrawer)
                {

                    //Toast.makeText(MainActivity.this,"I will work Later",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cyrisstudio.blogspot.com/p/privacy-policy-cyris-studio-built.html")));
                    drawer.closeDrawer(Gravity.LEFT);

                }

                if(menuItem.getItemId() == R.id.contctUsInDrawer)
                {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cyrisstudio.blogspot.com/p/contact-us.html")));
                    // Toast.makeText(MainActivity.this,"I will work Later",Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(Gravity.LEFT);

                }
                if(menuItem.getItemId() == R.id.otherAppInDrawer)
                {

                    //Toast.makeText(MainActivity.this,"I will work Later",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Cyris+Studio")));
                    drawer.closeDrawer(Gravity.LEFT);

                }
                return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

      //  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
     //   NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
      //  NavigationUI.setupWithNavController(navigationView, navController);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);


            } else {
                setViewPager();
            }
        }
        else
        {

                Toast.makeText(this,"App is not compatible for your device",Toast.LENGTH_SHORT).show();
                finishAffinity();


        }
        //setViewPager();

      //  tabLayout.setupWithViewPager(mainViewPager);
      //  tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    private void setViewPager() {
        whatsappFragmentAdapter = new WhatsappFragmentAdapter(getSupportFragmentManager());
        mainViewPager = findViewById(R.id.viewPagerInMain);
        mainViewPager.setAdapter(whatsappFragmentAdapter);
        tabLayout.setupWithViewPager(mainViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setPackage("com.whatsapp");
                if(intent.resolveActivity(getPackageManager())!=null)
                {
                    intent.putExtra(Intent.EXTRA_TEXT,"Hey, Check this app to Download whatsapp status image/video in gallery with status downloader app download it:-\n\nhttps://play.google.com/store/apps/details?id=com.cyris.whatsappstatudownload");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Whatsapp not Installed",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dialog.show();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
      //  return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
       /* NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();*/
       return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE && grantResults[0] == PackageManager.PERMISSION_DENIED)
        {
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            setViewPager();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
