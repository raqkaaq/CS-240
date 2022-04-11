package com.raqkaaq.familymap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class EventActivity extends AppCompatActivity {
    String eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("eventId", eventId);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.eventFrame, fragment).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        return true;
    }
}