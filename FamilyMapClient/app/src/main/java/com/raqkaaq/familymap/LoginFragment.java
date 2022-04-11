package com.raqkaaq.familymap;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import Model.AuthToken;
import Proxy.DataCache;
import Proxy.ServerProxy;
import Result.*;
import Request.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private MainActivity mainActivity;

    private static final String TAG = "LOGIN_FRAGMENT";
    private boolean loginSuccess;
    //LoginResult loginResult;
    private boolean registerSuccess;
    //RegisterResult registerResult;

    private EditText serverHost;
    private EditText serverPort;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioGroup gender;
    private Button signIn;
    private Button register;
    private Button defaultServer;

    //Strings to store the data from the register and login form
    private String host;
    private String port;
    private String user;
    private String pass;
    private String first;
    private String last;
    private String em;
    private String gen;



    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginSuccess = false;
        registerSuccess = false;
        host = "";
        port = "";
        user = "";
        pass = "";
        first = "";
        last = "";
        em = "";
        gen = "";
        serverHost = view.findViewById(R.id.editServerHost);
        serverPort = view.findViewById(R.id.editServerPort);
        username = view.findViewById(R.id.editUsername);
        password = view.findViewById(R.id.editPassword);
        firstName = view.findViewById(R.id.editFirstName);
        lastName = view.findViewById(R.id.editLastName);
        email = view.findViewById(R.id.editEmail);
        gender = view.findViewById(R.id.radioGender);
        signIn = view.findViewById(R.id.SignIn);
        register = view.findViewById(R.id.Register);
        defaultServer = view.findViewById(R.id.DefaultButton);
        signIn.setEnabled(false);
        register.setEnabled(false);
        serverHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                host = charSequence.toString();
                signIn.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&first.isEmpty()&&last.isEmpty()&&em.isEmpty()&&gen.isEmpty());
                register.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        serverPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                port = charSequence.toString();
                signIn.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&first.isEmpty()&&last.isEmpty()&&em.isEmpty()&&gen.isEmpty());
                register.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user = charSequence.toString();
                signIn.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&first.isEmpty()&&last.isEmpty()&&em.isEmpty()&&gen.isEmpty());
                register.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pass = charSequence.toString();
                signIn.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&first.isEmpty()&&last.isEmpty()&&em.isEmpty()&&gen.isEmpty());
                register.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                first = charSequence.toString();
                register.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&!first.isEmpty()&&!last.isEmpty()&&!em.isEmpty()&&!gen.isEmpty());
                signIn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                last = charSequence.toString();
                register.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&!first.isEmpty()&&!last.isEmpty()&&!em.isEmpty()&&!gen.isEmpty());
                signIn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                em = charSequence.toString();
                register.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&!first.isEmpty()&&!last.isEmpty()&&!em.isEmpty()&&!gen.isEmpty());
                signIn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int gender = radioGroup.getCheckedRadioButtonId();
                switch (gender) {
                    case R.id.radioMale:
                        gen = "m";
                        break;
                    case R.id.radioFemale:
                        gen = "f";
                        break;
                    case -1:
                        break;
                }
                register.setEnabled(!user.isEmpty()&&!pass.isEmpty()&&!host.isEmpty()&&!port.isEmpty()&&!first.isEmpty()&&!last.isEmpty()&&!em.isEmpty()&&!gen.isEmpty());
                signIn.setEnabled(false);
            }
        });

        class EventTask extends AsyncTask<Void, String, EventResult> {
            protected void onPreExecute() {
                Log.d("registereventonpreexecute", "Pre-execute");
            }
            @Override
            protected EventResult doInBackground(Void... voids) {
                publishProgress("Getting Events For User");
                ServerProxy serverProxy = new ServerProxy();
                EventResult eventResult = serverProxy.getEvents(host, port);
                return eventResult;
            }

            protected void onProgressUpdate(String... updateMessage) {
                //Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
            }

            protected void onPostExecute(EventResult eventResult) {
                DataCache data = DataCache.getInstance();
                data.setEvent(eventResult.getEvent());
            }
        }

        class PersonTask extends AsyncTask<Void, String, PersonResult> {
            protected void onPreExecute() {
                Log.d("registereventonpreexecute", "Pre-execute");
            }
            @Override
            protected PersonResult doInBackground(Void... voids) {
                publishProgress("Getting People For User");
                ServerProxy serverProxy = new ServerProxy();
                PersonResult personResult = serverProxy.getPeople(host, port);
                return personResult;
            }

            protected void onProgressUpdate(String... updateMessage) {
                //Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
            }

            protected void onPostExecute(PersonResult personResult) {
                DataCache data = DataCache.getInstance();
                data.setPeople(personResult.getPersons());
                data.sort();
                String name = data.getName();
                //Toast.makeText(getContext(), "Welcome " + data.getName(), Toast.LENGTH_LONG).show();
            }
        }
        class FamilyMapTask extends AsyncTask<Void, String, Void>{
            protected void onPreExecute(){
                EventTask events = new EventTask();
                events.execute();
                PersonTask people = new PersonTask();
                people.execute();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                publishProgress("Fetching Data for user");
                return null;
            }
            protected void onProgressUpdate(String... updateMessage){
                //Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
            }
            protected void onPostExecute(Void voids){
                mainActivity = (MainActivity) getContext();
                mainActivity.switchToMapFragment();
            }
        }
        class LoginTask extends AsyncTask<Void, String, LoginResult>{

            protected void onPreExecute(){
                Log.d("logineventonpreexecute", "Pre-execute");
            }
            @Override
            protected LoginResult doInBackground(Void... voids) {
                loginSuccess = false;
                LoginRequest loginRequest = new LoginRequest(user, pass);
                publishProgress("Logging in");
                ServerProxy serverProxy = new ServerProxy();
                LoginResult loginResult = serverProxy.login(loginRequest, host, port);
                Log.d("logineventinbackground", "AuthToken: " + loginResult.getAuthToken());
                return loginResult;
            }
            protected void onProgressUpdate(String... updateMessage){
                Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
            }
            protected void onPostExecute(LoginResult loginResult){
                if(loginResult.getAuthToken() == null){
                    Log.d("logineventnull", "AUTHTOKEN NULL");
                    loginSuccess = false;
                    Toast.makeText(getContext(), loginResult.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    loginSuccess = true;
                    Log.d("logineventtrue", "SUCCESSFUL LOGIN");
                    DataCache data = DataCache.getInstance();
                    AuthToken auth = new AuthToken(loginResult.getAuthToken(), loginResult.getUsername());
                    data.setAuth(auth);
                    data.setLogin(loginResult);
                    data.setRootPersonID(loginResult.getPersonID());
                    data.setServerHost(host);
                    data.setServerPort(port);
                }
                if(loginSuccess){
                    //Toast.makeText(getContext(), loginResult.getMessage(), Toast.LENGTH_SHORT);
                    FamilyMapTask familyMapTask = new FamilyMapTask();
                    familyMapTask.execute();
                }
            }
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginTask loginTask = new LoginTask();
                loginTask.execute();
            }

        });
        class RegisterTask extends AsyncTask<Void, String, RegisterResult>{
            protected void onPreExecute(){
                Log.d("registereventonpreexecute", "Pre-execute");
            }
            @Override
            protected RegisterResult doInBackground(Void... voids) {
                registerSuccess = false;
                RegisterRequest registerRequest = new RegisterRequest(user, pass, em, first, last, gen);
                publishProgress("Registering User");
                ServerProxy serverProxy = new ServerProxy();
                RegisterResult registerResult = serverProxy.register(registerRequest, host, port);
                Log.d("logineventinbackground", "AuthToken: " + registerResult.getAuthtoken());
                return registerResult;
            }
            protected void onProgressUpdate(String... updateMessage){
                Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
            }
            protected void onPostExecute(RegisterResult registerResult) {
                if (registerResult.getAuthtoken() == null) {
                    Log.d("registereventnull", "AUTHTOKEN NULL");
                    registerSuccess = false;
                    Toast.makeText(getContext(), registerResult.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    registerSuccess = true;
                    Log.d("registereventtrue", "SUCCESSFUL LOGIN");
                    DataCache data = DataCache.getInstance();
                    AuthToken auth = new AuthToken(registerResult.getAuthtoken(), registerResult.getUsername());
                    data.setAuth(auth);
                    data.setRegister(registerResult);
                    data.setRootPersonID(registerResult.getPersonID());
                    data.setServerHost(host);
                    data.setServerPort(port);
                }
                if (registerSuccess) {
                    Toast.makeText(getContext(), registerResult.getMessage(), Toast.LENGTH_SHORT);
                    FamilyMapTask familyMapTask = new FamilyMapTask();
                    familyMapTask.execute();
                }
            }
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterTask registerTask = new RegisterTask();
                registerTask.execute();
            }
        });
        //use to add a default server and port configuration for easy connection
        defaultServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverHost.setText(R.string.default_host);
                serverPort.setText(R.string.default_port);
            }
        });


        return view;
    }
}