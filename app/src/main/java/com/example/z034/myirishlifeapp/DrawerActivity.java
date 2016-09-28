package com.example.z034.myirishlifeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import org.json.JSONArray;

public class DrawerActivity extends Home
        implements NavigationView.OnNavigationItemSelectedListener {

    private String userId;
    private String Pin;
    private JSONArray PolicyInfo = null;
    private GridLayout PolicyGrid;
    private TextView nav_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.viewName);

        getPolicyInfo();
    }

    public void getPolicyInfo()
    {

        PolicyGrid = (GridLayout) findViewById(R.id.HomePolicyGrid);
        Intent intent = getIntent();
        this.userId = intent.getStringExtra(ApplicationConstants.Username);
        nav_user.setText(userId);
        this.Pin = intent.getStringExtra(ApplicationConstants.Pin);
        HttpUtilities.GetPolicyData(userId, Pin, getApplicationContext(), intent, PolicyGrid);
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
        getMenuInflater().inflate(R.menu.drawer, menu);
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
        Intent intent = getIntent();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_details) {
            Intent userDetailIntent = new Intent(getApplicationContext(), UserDetailActivity.class);
            userDetailIntent.putExtra(ApplicationConstants.Username, intent.getStringExtra(ApplicationConstants.Username));
            userDetailIntent.putExtra(ApplicationConstants.Pin, intent.getStringExtra(ApplicationConstants.Pin));
            startActivity(userDetailIntent);
        } else if (id == R.id.my_policies) {

        } else if (id == R.id.add_policy) {
            Intent addPolicyIntent = new Intent(getApplicationContext(), AddPolicyActivity.class);
            addPolicyIntent.putExtra(ApplicationConstants.Username, intent.getStringExtra(ApplicationConstants.Username));
            addPolicyIntent.putExtra(ApplicationConstants.Pin, intent.getStringExtra(ApplicationConstants.Pin));
            startActivity(addPolicyIntent);
        } else if (id == R.id.web_login) {
            Intent webLoginIntent = new Intent(getApplicationContext(),InitiateWebSessionActivity.class);
            webLoginIntent.putExtra(ApplicationConstants.Username, intent.getStringExtra(ApplicationConstants.Username));
            webLoginIntent.putExtra(ApplicationConstants.Pin, intent.getStringExtra(ApplicationConstants.Pin));
            startActivity(webLoginIntent);
        } else if (id == R.id.tools) {

        } else if (id == R.id.inbox) {

        } else if (id == R.id.contact_us) {
            Intent contactIntent = new Intent(getApplicationContext(), ContactUsActivity.class);
            startActivity(contactIntent);
        }else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
