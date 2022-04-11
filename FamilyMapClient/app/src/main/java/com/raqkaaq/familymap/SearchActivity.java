package com.raqkaaq.familymap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Model.Event;
import Model.Person;
import Proxy.DataCache;

public class SearchActivity extends AppCompatActivity {
    private final int EVENT_TYPE = 0;
    private final int PERSON_TYPE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataCache dataCache = DataCache.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.searchRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        SearchView searchView = findViewById(R.id.searchField);
        List<Event> events = new ArrayList<>(dataCache.filterEvents());
        List<Person> people = new ArrayList<>(dataCache.filterHelper());
        Searcher adapter = new Searcher(events, people);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text){
                adapter.getFilter().filter(text);
                return false;
            }
        });
        adapter.getFilter().filter("");
        recyclerView.setAdapter(adapter);
    }
    private class Searcher extends RecyclerView.Adapter<SearchViewHolder> implements Filterable {
        private final List<Event> events;
        private final List<Person> people;
        private List<Event> filledEvents;
        private List<Person> filledPeople;

        private Searcher(List<Event> events, List<Person> people) {
            this.events = events;
            this.people = people;
            filledEvents = new ArrayList<>(events);
            filledPeople = new ArrayList<>(people);
        }
        @Override
        public int getItemViewType(int index){
            if(index < people.size()){
                return PERSON_TYPE;
            } else {
                return EVENT_TYPE;
            }
        }

        @Override
        public Filter getFilter() {
            return filter;
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            return new SearchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if(position < people.size()){
                holder.bind(people.get(position));
            } else {
                holder.bind(events.get(position - people.size()));
            }
        }

        @Override
        public int getItemCount() {
            return people.size() + events.size();
        }
        private Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Event> filteredE = new ArrayList<>();
                List<Person> filteredP = new ArrayList<>();
                DataCache dataCache = DataCache.getInstance();
                if(charSequence != null && charSequence.length() != 0){
                    String filter = charSequence.toString().toLowerCase(Locale.ROOT).trim();
                    for(Person p : dataCache.getPeople()){
                        if(p.getFirstName().toLowerCase(Locale.ROOT).contains(filter) || p.getLastName().toLowerCase(Locale.ROOT).contains(filter))
                            filteredP.add(p);
                    }
                    for(Event e : dataCache.getEvent()){
                        if(e.getEventType().toLowerCase(Locale.ROOT).contains(filter) ||
                        e.getCity().toLowerCase(Locale.ROOT).contains(filter) ||
                        e.getCountry().toLowerCase(Locale.ROOT).contains(filter) ||
                        Integer.toString(e.getYear()).toLowerCase(Locale.ROOT).contains(filter))
                            filteredE.add(e);
                    }
                }
                Pair<List<Person>, List<Event>> results = new Pair<>(filteredP, filteredE);
                FilterResults res = new FilterResults();
                res.values = results;
                return res;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                Pair<List<Person>, List<Event>> pair = (Pair<List<Person>, List<Event>>) filterResults.values;
                people.clear();
                people.addAll(pair.first);
                events.clear();
                events.addAll(pair.second);
                notifyDataSetChanged();
            }
        };
    }
    private class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView title;
        private final Button indicator;
        private final int type;
        private Event event;
        private Person person;

        private SearchViewHolder(View view, int type) {
            super(view);
            itemView.setOnClickListener(this);
            this.type = type;
            name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.title);
            indicator = itemView.findViewById(R.id.type_indicator);
        }
        private void bind(Person person){
            this.person = person;
            name.setText(this.person.getFirstName() + " " + this.person.getLastName());
            title.setText("");
            String gen = this.person.getGender();
            if(gen.equalsIgnoreCase("m")){
                indicator.setBackgroundColor(getColor(R.color.blue));
                indicator.setText("M");
            } else {
                indicator.setBackgroundColor(getColor(R.color.pink));
                indicator.setText("F");
            }
        }
        private void bind(Event event){
            this.event = event;
            DataCache dataCache = DataCache.getInstance();
            name.setText(dataCache.getInfo(this.event.getEventID()));
            Person p = dataCache.getPersonMap().get(this.event.getPersonID());
            title.setText(p.getFirstName() + " " + p.getLastName());
            indicator.setBackgroundColor(getResources().getColor(R.color.teal_200));
        }
        @Override
        public void onClick(View view) {
            if(type == PERSON_TYPE){
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra(PersonActivity.PERSON_ID_KEY, person.getPersonID());
                startActivity(intent);
            } else if(type == EVENT_TYPE){
                Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                intent.putExtra(MapFragment.EVENT_ID_KEY, event.getEventID());
                intent.putExtra(MapFragment.OPTIONS_MENU_KEY, false);
                startActivity(intent);
            }
        }
    }
}