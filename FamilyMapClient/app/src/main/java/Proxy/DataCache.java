package Proxy;

import Model.*;
import Result.LoginResult;
import Result.RegisterResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DataCache {
    private static DataCache instance = new DataCache();
    public static DataCache getInstance() {
        return instance;
    }
    private DataCache(){}

    private List<Person> people; //list of all people related to the active user
    private List<Event> event; //list of all events related to the active user
    private Map<String, Person> personMap; //List of all people per personid
    private Map<String, Set<Event>> eventMap; //list of all events per personid
    public Map<String, Event> getEventIdMap() {
        return eventIdMap;
    }

    public void setEventIdMap(Map<String, Event> eventIdMap) {
        this.eventIdMap = eventIdMap;
    }

    private Map<String, Event> eventIdMap; //list of all events per eventid
    private Set<String> paternalAncestors;
    private Set<String> maternalAncestors;

    public Map<String, Set<Person>> getChildren() {
        return children;
    }

    private Map<String, Set<Person>> children = new HashMap<>();
    private AuthToken auth;
    private LoginResult login;
    private RegisterResult register;
    private String serverHost;
    private String serverPort;
    private String rootPersonID;
    private boolean drawSpouse = false;
    private boolean drawFamily = false;
    private boolean fatherFilter = true;
    private boolean motherFilter = true;

    public boolean isMotherFilter() {
        return motherFilter;
    }

    public void setMotherFilter(boolean motherFilter) {
        this.motherFilter = motherFilter;
    }

    public boolean isMaleFilter() {
        return maleFilter;
    }

    public void setMaleFilter(boolean maleFilter) {
        this.maleFilter = maleFilter;
    }

    private boolean maleFilter = true;

    public boolean isFatherFilter() {
        return fatherFilter;
    }

    public void setFatherFilter(boolean fatherFilter) {
        this.fatherFilter = fatherFilter;
    }

    public boolean isFemaleFilter() {
        return femaleFilter;
    }

    public void setFemaleFilter(boolean femaleFilter) {
        this.femaleFilter = femaleFilter;
    }

    private boolean femaleFilter = true;

    public boolean isDrawSpouse() {
        return drawSpouse;
    }

    public void setDrawSpouse(boolean drawSpouse) {
        this.drawSpouse = drawSpouse;
    }

    public boolean isDrawFamily() {
        return drawFamily;
    }

    public void setDrawFamily(boolean drawFamily) {
        this.drawFamily = drawFamily;
    }

    public boolean isDrawLife() {
        return drawLife;
    }

    public void setDrawLife(boolean drawLife) {
        this.drawLife = drawLife;
    }

    private boolean drawLife = false;

    public Set<String> getEventType() {
        return eventType;
    }

    private Set<String> eventType;

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
        Set<String> personID = new HashSet<>();
        eventIdMap = new HashMap<>();
        Map<String, Set<Event>> eventMap = new HashMap<>();
        eventType = new HashSet<>();
        for(Event e : event){
            personID.add(e.getPersonID());
        }
        for (String person : personID) {
            Set<Event> curr = new HashSet<>();
            for (Event eve : event) {
                if (eve.getPersonID().equals(person))
                    curr.add(eve);
            }
            eventMap.put(person, curr);
        }
        for(Event eve : event){
            eventIdMap.put(eve.getEventID(), eve);
            eventType.add(eve.getEventType());
        }
        setEventMap(eventMap);
    }

    public Map<String, Person> getPersonMap() {
        return personMap;
    }

    public Map<String, Set<Event>> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, Set<Event>> eventMap) {
        this.eventMap = eventMap;
    }

    public Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public void setPaternalAncestors(Set<String> paternalAncestors) {
        this.paternalAncestors = paternalAncestors;
    }

    public Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public void setMaternalAncestors(Set<String> maternalAncestors) {
        this.maternalAncestors = maternalAncestors;
    }

    public LoginResult getLogin() { return login; }

    public void setLogin(LoginResult login) {
        this.login = login;
    }

    public RegisterResult getRegister(){ return register; }

    public void setRegister(RegisterResult register){ this.register = register; }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getRootPersonID() {
        return rootPersonID;
    }

    public void setRootPersonID(String rootPersonID) {
        this.rootPersonID = rootPersonID;
    }

    public AuthToken getAuth() {
        return auth;
    }
    public void setAuth(AuthToken auth) {
        this.auth = auth;
    }

    /*Person getPersonById(String id){ //gets person by person id

    }
    Event getEventById(String id){ //gets event by event id

    }
    List<Event> getPersonEvents(String id){ //gets associated events to person id

    }*/

    public String getName(){
        Person p = personMap.get(rootPersonID);
        String name = p.getFirstName() + " " + p.getLastName();
        return name;
    }

    public void sort(){
        personMap = new HashMap<>();
        paternalAncestors = new HashSet<>();
        maternalAncestors = new HashSet<>();
        for(Person p : people){
            personMap.put(p.getPersonID(), p);
            addChildren(p);
        }
        fillPaternal(rootPersonID);
        fillMaternal(rootPersonID);
    }
    public void addChildren(Person p){
        if(!p.getFatherID().equals("")){
            Set<Person> fatherChild = children.get(p.getFatherID());
            if(fatherChild == null){
                fatherChild = new HashSet<>();
                children.put(p.getFatherID(), fatherChild);
            }
            fatherChild.add(p);
        }
        if(!p.getMotherID().equals("")){
            Set<Person> motherChild = children.get(p.getMotherID());
            if(motherChild == null){
                motherChild = new HashSet<>();
                children.put(p.getMotherID(), motherChild);
            }
            motherChild.add(p);
        }
    }
    public void fillPaternal(String personID) {
        if (!personMap.get(personID).getFatherID().equals("")) {
            paternalAncestors.add(personMap.get(personID).getFatherID());
            fillPaternal(personMap.get(personID).getFatherID());
            if (!personID.equals(rootPersonID)) {
                paternalAncestors.add(personMap.get(personID).getMotherID());
                fillPaternal(personMap.get(personID).getMotherID());
            }
        }
    }
    public void fillMaternal(String personID){
        if(!personMap.get(personID).getMotherID().equals("")){
            maternalAncestors.add(personMap.get(personID).getMotherID());
            fillMaternal(personMap.get(personID).getMotherID());
            if(!personID.equals(rootPersonID)) {
                maternalAncestors.add(personMap.get(personID).getFatherID());
                fillMaternal(personMap.get(personID).getFatherID());
            }
        }
    }
    public String getInfo(String eventID){
        Event e = eventIdMap.get(eventID);
        Person p = personMap.get(e.getPersonID());
        return p.getFirstName() + " " + p.getLastName() + "\n" + e.getEventType().toLowerCase(Locale.ROOT) + " " + e.getCity() + ", " + e.getCountry() + "(" + e.getYear() + ")";
    }

    public Set<Person> filterHelper() {
        Person user = personMap.get(rootPersonID);
        Set<Person> personSet = new HashSet<>();
        if (maleFilter && user.getGender().equals("m")) {
            personSet.add(user);
        }
        if (femaleFilter && user.getGender().equals("f")) {
            personSet.add(user);
        }
        if (fatherFilter && maleFilter) {
            Set<Person> temp = new HashSet<>();
            for(String p : paternalAncestors){
                if(personMap.get(p).getGender().equals("m"))
                    temp.add(personMap.get(p));
            }
            personSet.addAll(temp);
        }

        if (fatherFilter && femaleFilter) {
            Set<Person> temp = new HashSet<>();
            for(String p : paternalAncestors){
                if(personMap.get(p).getGender().equals("f"))
                    temp.add(personMap.get(p));
            }
            personSet.addAll(temp);
        }

        if (motherFilter && maleFilter) {
            Set<Person> temp = new HashSet<>();
            for(String p : maternalAncestors){
                if(personMap.get(p).getGender().equals("m"))
                    temp.add(personMap.get(p));
            }
            personSet.addAll(temp);;
        }

        if (motherFilter && femaleFilter) {
            Set<Person> temp = new HashSet<>();
            for(String p : maternalAncestors){
                if(personMap.get(p).getGender().equals("f"))
                    temp.add(personMap.get(p));
            }
            personSet.addAll(temp);
        }
        return personSet;
    }
    public Set<Event> filterEvents(){
        Set<Event> filtered = new HashSet<>();
        Set<Person> per = filterHelper();
        for(Person p : per){
            filtered.addAll(eventMap.get(p.getPersonID()));
        }
        return filtered;
    }
    public boolean eventShown(String eventId){
        Set<Event> filtered = filterEvents();
        for(Event e : filtered){
            if(e.getEventID().equals(eventId))
                return true;
        }
        return false;
    }
    public boolean personShown(String personID){
        Set<Person> filtered = filterHelper();
        for(Person p: filtered){
            if(p.getPersonID().equals(personID))
                return true;
        }
        return false;
    }
    public void clear(){
        instance = new DataCache();
    }
    public String eventInfo(String eventId){
        Event e = eventIdMap.get(eventId);
        return e.getEventType().toUpperCase(Locale.ROOT) + ": " + e.getCity() + ", " + e.getCountry() + "(" + e.getYear() + ")";
    }
}
