package com.raqkaaq.familymap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Set;

import Model.Event;
import Proxy.DataCache;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    private MapFragment mapFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataCache data = DataCache.getInstance();
        if(data.getAuth() == null){
            loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.loginFragmentMain);
            fragmentManager.beginTransaction()
                    .addToBackStack("login")
                    .commit();
        } else {
            switchToMapFragment();
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }




    public void switchToMapFragment(){
        mapFragment = new MapFragment();
        DataCache dataCache =  DataCache.getInstance();
        String userID = dataCache.getRootPersonID();
        Set<Event> userSet = dataCache.getEventMap().get(userID);
        String userBirthID = null;
        for(Event e : userSet){
            if(e.getEventType().equalsIgnoreCase("birth"))
                userBirthID = e.getEventID();
        }
        Bundle bundle = new Bundle();
        bundle.putString(MapFragment.EVENT_ID_KEY, userBirthID);
        bundle.putBoolean(MapFragment.OPTIONS_MENU_KEY, true);
        mapFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.loginFragmentMain, mapFragment)
                .addToBackStack("map")
                .commit();
    }
}