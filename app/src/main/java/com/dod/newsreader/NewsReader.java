package com.dod.newsreader;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dod.newsreader.author.AuthorFragment;
import com.dod.newsreader.newspaper.NewspaperFragment;
import com.dod.newsreader.utils.FontApplicator;

public class NewsReader extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String TARGET = NewspaperFragment.TARGET;
    ActionBarDrawerToggle toggle;
    private FontApplicator fontApplicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fontApplicator = new FontApplicator(getApplicationContext(), "Roboto_Light.ttf").applyFont(getWindow().getDecorView());
    }

    public FontApplicator getFontApplicator()
    {
        return fontApplicator;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newspapers) {
           NewspaperFragment newspaperFragment = new NewspaperFragment();
            showFragment(newspaperFragment);
        } else if (id == R.id.nav_authors) {
            AuthorFragment authorFragment = new AuthorFragment();
            showFragment(authorFragment);
        } else if (id == R.id.nav_favourites) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();

        if (getFragmentManager().findFragmentById(R.id.content_news_reader) == null) {
            NewspaperFragment newspaperFragment = new NewspaperFragment();
            if (getIntent().hasExtra(TARGET)) {
                Bundle args = new Bundle();
                args.putString(newspaperFragment.TARGET, getIntent().getStringExtra(TARGET));
                newspaperFragment.setArguments(args);
            }

            showFragment(newspaperFragment);

        }
    }

    public void showFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_news_reader , fragment)
                .commit();
    }
}
