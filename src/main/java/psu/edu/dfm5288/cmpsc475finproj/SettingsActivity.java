package psu.edu.dfm5288.cmpsc475finproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Toolbar tB= findViewById(R.id.toolbar);
        setSupportActionBar(tB);



        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Switch dMSwitch = (Switch) findViewById(R.id.lightDarkMode);

        if(savedInstanceState != null){
            dMSwitch.setChecked(savedInstanceState.getBoolean("Dark Mode"));
        }
        //dMSwitch.setChecked(sharedPref.getBoolean("dark_mode", false));





    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean("Dark Mode", sharedPref.getBoolean("dark_mode", false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //handle toolbar selections
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        if(id  == R.id.action_add){
            intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.action_tracker){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    public void switchMode(View v){
        int id = v.getId();
        if(id == R.id.lightDarkMode){
            boolean dM = !sharedPref.getBoolean("dark_mode", false);
            Log.d("Test", String.valueOf(dM));
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("dark_mode", dM);
            editor.commit();

            if(dM) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }


        }
    }

}