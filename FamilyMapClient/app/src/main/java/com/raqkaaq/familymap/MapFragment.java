package com.raqkaaq.familymap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import Model.Event;
import Model.Person;
import Proxy.DataCache;
import Result.EventResult;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback{
    public static final String OPTIONS_MENU_KEY = "optionsMenuKey";
    public static final String EVENT_ID_KEY = "eventId";
    private GoogleMap map;
    private DataCache dataCache;
    private String startEventID;
    private String currEventID;
    private List<Polyline> lines;

    public MapFragment() {
    }
    public MapFragment(String startEventID){
        this.startEventID = startEventID;
    }
    public void setCurrEventID(String eventID){this.currEventID = eventID;}
    public String getCurrEventID(){return this.currEventID;}
    public static MapFragment newInstance(){
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(map != null){
            dataCache = DataCache.getInstance();
            map.clear();
            Set<Event> events = dataCache.filterEvents();
            for(Event event : events){
                LatLng marker = new LatLng(event.getLatitude(), event.getLongitude());
                map.addMarker(new MarkerOptions().position(marker)
                        .icon(BitmapDescriptorFactory.defaultMarker(color(event.getEventType()))))
                        .setTag(event.getEventID());
            }
            if(currEventID == null){
                draw(startEventID);
            } else {
                draw(currEventID);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        startEventID = getArguments().getString(EVENT_ID_KEY);
        boolean hasMenu = getArguments().getBoolean(OPTIONS_MENU_KEY);
        setHasOptionsMenu(hasMenu);
        lines = new ArrayList<>();
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        dataCache = DataCache.getInstance();
        Event start = dataCache.getEventIdMap().get(startEventID);
        Person person = dataCache.getPersonMap().get(start.getPersonID());
        String personID = person.getPersonID();
        TextView mapText = (TextView) getView().findViewById(R.id.mapText);
        mapText.setText(dataCache.getInfo(startEventID));
        String gen = person.getGender();
        Button gender = getView().findViewById(R.id.gender_indicator);
        if (gen.equalsIgnoreCase("m")) {
            gender.setBackgroundColor(getResources().getColor(R.color.blue));
            gender.setText(R.string.m);
        } else {
            gender.setBackgroundColor(getResources().getColor(R.color.pink));
            gender.setText(R.string.f);
        }
        LatLng startCoords = new LatLng(start.getLatitude(), start.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLng(startCoords));
        Set<Event> eventSet = dataCache.filterEvents();
        for(Event e : eventSet){
            LatLng marker = new LatLng(e.getLatitude(), e.getLongitude());
            map.addMarker(new MarkerOptions().position(marker).icon(BitmapDescriptorFactory.defaultMarker(color(e.getEventType())))).setTag(e.getEventID());
        }
        draw(startEventID);
        LinearLayout infoView = getView().findViewById(R.id.infoView);
        infoView.setOnClickListener(new View.OnClickListener() {
            String current = getCurrEventID();
            @Override
            public void onClick(View view) {
                if(current == null){
                    current = startEventID;
                }
                //Intent intent = new Intent(getActivity(), Pers)
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String eventId = (String) marker.getTag();
                draw(eventId);
                setCurrEventID(eventId);
                TextView bottom = (TextView) getView().findViewById(R.id.mapText);
                bottom.setTag(dataCache.getEventIdMap().get(eventId).getPersonID());
                bottom.setText(dataCache.getInfo(eventId));
                Event event =  dataCache.getEventIdMap().get(eventId);
                Person person = dataCache.getPersonMap().get(event.getPersonID());
                String gen = person.getGender();
                Button gender = getView().findViewById(R.id.gender_indicator);
                if (gen.equalsIgnoreCase("m")) {
                    gender.setBackgroundColor(getResources().getColor(R.color.blue));
                    gender.setText(R.string.m);
                } else {
                    gender.setBackgroundColor(getResources().getColor(R.color.pink));
                    gender.setText(R.string.f);
                }
                return false;
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.map_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        Intent intent;
        switch (menuItem.getItemId()){
            case R.id.settingsButton:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.searchButton:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private float color(String event){
        event = event.toLowerCase(Locale.ROOT);
        dataCache = DataCache.getInstance();
        List<Float> colors = Arrays.asList(0f, 120f, 240f, 300f);
        Map<String, Integer> eventTypeIndex = new HashMap<>();
        Set<String> types = dataCache.getEventType();
        Set<String> condensedTypes = new HashSet<>();
        int index = 0;
        for(String type : types){
            condensedTypes.add(type.toLowerCase(Locale.ROOT));
        }
        for(String type : condensedTypes){
            eventTypeIndex.put(type, index % colors.size());
            index++;
        }
        return colors.get(eventTypeIndex.get(event));
    }
    public void drawSpouse(String currEventID){
        Person p = dataCache.getPersonMap().get(dataCache.getEventIdMap().get(currEventID).getPersonID()); //gets person from eventid
        if(!dataCache.eventShown(currEventID)){
            return;
        } else if (p.getSpouseID() == null){
            return;
        }
        Person s = dataCache.getPersonMap().get(p.getSpouseID());
        if(s == null)
            return;
        Event pe = dataCache.getEventIdMap().get(currEventID);
        Set<Event> sSet = dataCache.getEventMap().get(s.getPersonID());
        Event se = null;
        for(Event e : sSet){
            if(e.getEventType().equalsIgnoreCase("birth")){
                se = e;
            }
        }
        if(!dataCache.eventShown(se.getEventID()))
            return;
        PolylineOptions pO = new PolylineOptions().add(new LatLng(pe.getLatitude(), pe.getLongitude()))
                .add(new LatLng(se.getLatitude(), se.getLongitude()))
                .width(10)
                .color(Color.MAGENTA);
        lines.add(map.addPolyline(pO));
    }
    public void drawFamily(String currEventID){
        if(!dataCache.eventShown(currEventID))
            return;
        Person p = dataCache.getPersonMap().get(dataCache.getEventIdMap().get(currEventID).getPersonID());
        if(p.getFatherID() != null && !p.getFatherID().equals("")){
            Person father = dataCache.getPersonMap().get(p.getFatherID());
            Event pe = dataCache.getEventIdMap().get(currEventID);
            Set<Event> fSet = dataCache.getEventMap().get(father.getPersonID());
            Event fe = null;
            for(Event e : fSet){
                if(e.getEventType().equalsIgnoreCase("birth")){
                    fe = e;
                }
            }
            if(dataCache.eventShown(fe.getEventID())){
                PolylineOptions pO = new PolylineOptions().add(new LatLng(pe.getLatitude(), pe.getLongitude()))
                        .add(new LatLng(fe.getLatitude(), fe.getLongitude()))
                        .width(10)
                        .color(Color.RED);
                lines.add(map.addPolyline(pO));
                drawFamily(fe.getEventID());
            }
        }
        if(p.getMotherID() != null && !p.getMotherID().equals("")){
            Person mother = dataCache.getPersonMap().get(p.getMotherID());
            Event pe = dataCache.getEventIdMap().get(currEventID);
            Set<Event> mSet = dataCache.getEventMap().get(mother.getPersonID());
            Event me = null;
            for(Event e : mSet){
                if(e.getEventType().equalsIgnoreCase("birth")){
                    me = e;
                }
            }
            if(dataCache.eventShown(me.getEventID())){
                PolylineOptions pO = new PolylineOptions().add(new LatLng(pe.getLatitude(), pe.getLongitude()))
                        .add(new LatLng(me.getLatitude(), me.getLongitude()))
                        .width(10)
                        .color(Color.RED);
                lines.add(map.addPolyline(pO));
                drawFamily(me.getEventID());
            }
        }
    }
    public void drawLife(String currEventID){
        Person p = dataCache.getPersonMap().get(dataCache.getEventIdMap().get(currEventID).getPersonID());
        if(!dataCache.personShown(p.getPersonID())){
            return;
        }
        Set<Event> events = dataCache.getEventMap().get(p.getPersonID());
        Event first = null;
        for(Event e : events){
            if(first == null){
                first = e;
            } else {
                if(first.getEventID() != null){
                    PolylineOptions pO = new PolylineOptions().add(new LatLng(first.getLatitude(), first.getLongitude()))
                            .add(new LatLng(e.getLatitude(), e.getLongitude()))
                            .width(10)
                            .color(Color.BLUE);
                    lines.add(map.addPolyline(pO));
                    first = e;
                }
            }
        }
    }
    void draw(String currEventID){
        for(Polyline line : lines){
            line.remove();
        }
        lines.clear();
        if(dataCache.isDrawSpouse())
            drawSpouse(currEventID);
        if(dataCache.isDrawFamily())
            drawFamily(currEventID);
        if(dataCache.isDrawLife())
            drawLife(currEventID);
    }
}