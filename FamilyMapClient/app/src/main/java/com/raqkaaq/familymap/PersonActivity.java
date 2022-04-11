package com.raqkaaq.familymap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Model.Event;
import Model.Person;
import Proxy.DataCache;

public class PersonActivity extends AppCompatActivity {
    public static final String PERSON_ID_KEY = "person_id_key";
    private String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        pid = intent.getStringExtra(PERSON_ID_KEY);
        DataCache dataCache = DataCache.getInstance();
        Person p = dataCache.getPersonMap().get(pid);
        TextView firstName = findViewById(R.id.person_first_name);
        TextView lastName = findViewById(R.id.person_last_name);
        TextView gender = findViewById(R.id.person_gender);
        firstName.setText(p.getFirstName());
        lastName.setText(p.getLastName());
        if(p.getGender().equalsIgnoreCase("m")){
            gender.setText("Male");
        } else {
            gender.setText("Female");
        }
        ExpandableListView expandableListView = findViewById(R.id.expand);
        Set<Event> events = dataCache.getEventMap().get(pid);
        Set<Person> people = new HashSet<>();
        if(!p.getFatherID().equals("")){
            people.add(dataCache.getPersonMap().get(p.getFatherID()));
        }
        if(!p.getMotherID().equals("")){
            people.add(dataCache.getPersonMap().get(p.getMotherID()));
        }
        if(!p.getSpouseID().equals("")){
            people.add(dataCache.getPersonMap().get(p.getSpouseID()));
        }
        if(dataCache.getChildren().containsKey(pid)){
            people.addAll(dataCache.getChildren().get(pid));
        }
        expandableListView.setAdapter(new ExpandableAdapter(events, people));
    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent = new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        return true;
    }
    private class ExpandableAdapter extends BaseExpandableListAdapter {
        private static final int EVENTS_GROUP = 0;
        private static final int PERSON_GROUP = 1;
        private final List<Event> events = new ArrayList<>();
        private final List<Person> people = new ArrayList<>();
        public ExpandableAdapter(Set<Event> events, Set<Person> people){
            this.events.addAll(events);
            this.people.addAll(people);
        }
        @Override
        public int getGroupCount(){
            return 2;
        }
        @Override
        public int getChildrenCount(int group){
            if(group == EVENTS_GROUP){
                return events.size();
            } else if (group == PERSON_GROUP) {
                return people.size();
            }
            return 0;
        }
        @Override
        public Object getGroup(int group){
            if(group == EVENTS_GROUP){
                return "Life Events";
            } else if(group == PERSON_GROUP){
                return "Family";
            } else {
                return "";
            }
        }
        @Override
        public Object getChild(int group, int child){
            if(group == EVENTS_GROUP){
                return events.get(child);
            } else if(group == PERSON_GROUP){
                return events.get(child);
            } else {
                return 0;
            }
        }
        @Override
        public long getGroupId(int group){
            return group;
        }
        @Override
        public long getChildId(int group, int child){
            return child;
        }
        @Override
        public boolean hasStableIds(){
            return false;
        }
        @Override
        public View getGroupView(int group, boolean expanded, View view, ViewGroup parent){
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }
            TextView title = view.findViewById(R.id.main_title);
            if(group == EVENTS_GROUP){
                title.setText("Life Events");
            } else if (group == PERSON_GROUP){
                title.setText("Family");
            }
            return view;
        }
        @Override
        public View getChildView(int group, int child, boolean isLast, View view, ViewGroup parent){
            View view1 = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            if(group == EVENTS_GROUP){
                fillEvent(view1, child);
            } else if (group == PERSON_GROUP){
                fillFamily(view1, child);
            }
            return view1;
        }
        private void fillEvent(View view, int child){
            TextView name = view.findViewById(R.id.name);
            TextView title = view.findViewById(R.id.title);
            Button indicator = view.findViewById(R.id.type_indicator);
            DataCache dataCache = DataCache.getInstance();
            Event e = events.get(child);
            name.setText(dataCache.eventInfo(e.getEventID()));
            Person p = dataCache.getPersonMap().get(e.getPersonID());
            title.setText(p.getFirstName() + " " + p.getLastName());
            indicator.setBackgroundColor(getResources().getColor(R.color.teal_200));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    intent.putExtra(MapFragment.EVENT_ID_KEY, e.getEventID());
                    intent.putExtra(MapFragment.OPTIONS_MENU_KEY, false);
                    startActivity(intent);
                }
            });
        }
        private void fillFamily(View view, int child) {
            TextView name = view.findViewById(R.id.name);
            TextView title = view.findViewById(R.id.title);
            Button indicator = view.findViewById(R.id.type_indicator);
            Person p = people.get(child);
            name.setText(p.getFirstName() + " " + p.getLastName());
            DataCache dataCache = DataCache.getInstance();
            Person root = dataCache.getPersonMap().get(pid);
            String status = "Child";
            if (root != null) {
                if (root.getSpouseID().equals(p.getPersonID()))
                    status = "Spouse";
                else if (root.getFatherID().equals(p.getPersonID()))
                    status = "Father";
                else if (root.getMotherID().equals(p.getPersonID()))
                    status = "Mother";
            }
            title.setText(status);
            String gen = p.getGender();
            if(gen.equalsIgnoreCase("M")){
                indicator.setBackgroundColor(getResources().getColor(R.color.blue));
                indicator.setText("M");
            } else {
                indicator.setBackgroundColor(getResources().getColor(R.color.pink));
                indicator.setText("F");
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    intent.putExtra(PersonActivity.PERSON_ID_KEY, p.getPersonID());
                    startActivity(intent);
                }
            });
        }
        @Override
        public boolean isChildSelectable(int group, int child){
            return true;
        }
    }

}