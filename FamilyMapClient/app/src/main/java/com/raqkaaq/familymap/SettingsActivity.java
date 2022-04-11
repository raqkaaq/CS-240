package com.raqkaaq.familymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import Proxy.DataCache;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        DataCache dataCache = DataCache.getInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Switch lifeStory = findViewById(R.id.lifeStoryLineSwitch);
        Switch familyLine = findViewById(R.id.familyTreeLineSwitch);
        Switch spouseLine = findViewById(R.id.spouseLineSwitch);
        Switch fatherSide = findViewById(R.id.fatherSideFilterSwitch);
        Switch motherSide = findViewById(R.id.mothersSideFilterSwitch);
        Switch maleEvents = findViewById(R.id.maleEventsFilterSwitch);
        Switch femaleEvents = findViewById(R.id.femaleEventsFilterSwitch);
        lifeStory.setChecked(dataCache.isDrawLife());
        familyLine.setChecked(dataCache.isDrawFamily());
        spouseLine.setChecked(dataCache.isDrawSpouse());
        fatherSide.setChecked(dataCache.isFatherFilter());
        motherSide.setChecked(dataCache.isMotherFilter());
        maleEvents.setChecked(dataCache.isMaleFilter());
        femaleEvents.setChecked(dataCache.isFemaleFilter());
        lifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setDrawLife(b);
            }
        });
        familyLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setDrawFamily(b);
            }
        });
        spouseLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setDrawSpouse(b);
            }
        });
        fatherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setFatherFilter(b);
            }
        });
        motherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setMotherFilter(b);
            }
        });
        maleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setMaleFilter(b);
            }
        });
        femaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setFemaleFilter(b);
            }
        });
        LinearLayout logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataCache.getInstance().clear();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent = new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        return true;
    }
}