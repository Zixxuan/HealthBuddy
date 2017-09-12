package com.example.l31106.healthbuddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by L31106 on 9/7/2017.
 */

public class LearnClass extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    TextView locText;
    WifiManager wmgr;
    private ListView lv;
    ArrayList<String> locationListArray = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_main);

     //   new JSONtask().execute("https://ml.internalpositioning.com/locations?group=wayFindp3");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wmgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        lv = (ListView) findViewById(R.id.locList);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        locText = (TextView) findViewById(R.id.locationText);
        Button locButton = (Button)findViewById(R.id.learnLocButton);
        locButton.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View view){
                if(wmgr.isWifiEnabled()){
                    WifiInfo wifiInfo = wmgr.getConnectionInfo();
                   if(wifiInfo.getSupplicantState().toString().equals("COMPLETED")) {

                /*          ---------------- REMEMBER TO USE LATER ---------------
                       wmgr.startScan();
                       List<ScanResult> results = wmgr.getScanResults();

                       for (ScanResult R : results) {
                               Log.d("textTag","WIFI FYPWF " + R.BSSID + " " + R.level);
                           if(R.SSID.equals("NYP-Account")) {
                               locationListArray.add("WIFI FYPWF " + R.BSSID + " " + R.level);

                               ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                       LearnClass.this,
                                       android.R.layout.simple_list_item_1,
                                       locationListArray);

                               lv.setAdapter(arrayAdapter);
                           }


                       }
                  */
                       String timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "";
                       Log.d("Time EPOCH", timeStamp);

                   }
                }else{

                    Toast.makeText(LearnClass.this, "Open Your WIFI Connection" , Toast.LENGTH_SHORT).show();
                }



            }


        });
    }

    public class JSONtask extends AsyncTask<String, String, String >{
        ArrayList<String> aList = new ArrayList<String>();

        @Override
        protected String doInBackground(String... params) {
            HttpsURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL link = new URL(params[0]);
                connection = (HttpsURLConnection) link.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader =  new BufferedReader(new InputStreamReader(stream));

                StringBuffer sBuffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null){
                    sBuffer.append(line);
                }
                String finalJson = sBuffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentArray = parentObject.getJSONObject("locations");

                Iterator<String> iterator = parentArray.keys();
                while(iterator.hasNext()){
                    aList.add(iterator.next());

                }

                return aList.toString();

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

          return null;
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    LearnClass.this,
                    android.R.layout.simple_list_item_1,
                    aList );
            lv.setAdapter(arrayAdapter);

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_Learn) {
            //No Need To Add Learn
        } else if (id == R.id.nav_Direction) {
            Intent dirIntent = new Intent(this, Location.class);
            //Prevent Stacking
            dirIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(dirIntent);

        } else if (id == R.id.nav_Help) {

        } else if (id == R.id.nav_Preferences) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(LearnClass.this);
            View mView = getLayoutInflater().inflate(R.layout.language_spinner, null);
            mBuilder.setTitle("Select Your Preferred Language");
            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinnerLanguage);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LearnClass.this,
                    android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.languageList));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);

            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Your Preferred Language")) {
                        Toast.makeText(LearnClass.this, mSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                }
            });

            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        } else if (id == R.id.nav_aboutWayFinder) {

        } else if (id == R.id.nav_Home){
            Intent homeIntent = new Intent(this, MainActivity.class);
            //Prevent Stacking
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
