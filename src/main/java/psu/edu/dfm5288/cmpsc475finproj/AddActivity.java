package psu.edu.dfm5288.cmpsc475finproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

import psu.edu.dfm5288.cmpsc475finproj.db.Combatant;
import psu.edu.dfm5288.cmpsc475finproj.db.CombatantDatabase;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar tB= findViewById(R.id.toolbar);
        setSupportActionBar(tB);

        Button button = (Button) findViewById(R.id.add_new);
        button.setOnClickListener(this);


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

        if(id  == R.id.action_settings){
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.action_tracker){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        String name =  ((EditText) findViewById(R.id.newCharName)).getText().toString();
        if(name.equals("")){
            Toast.makeText(this,"Name must be filled in", Toast.LENGTH_LONG).show();
            return;
        }

        String initDiceText = ((EditText) findViewById(R.id.initDice)).getText().toString();
        if(initDiceText.equals("")) initDiceText = "1";
        if(Integer.parseInt(initDiceText) > 5) initDiceText = "5";
        int initDice = Integer.parseInt(initDiceText);

        String initModText = ((EditText) findViewById(R.id.initMod)).getText().toString();
        if(initModText.equals("")) initModText = "6";
        int initMod = Integer.parseInt(initModText);

        String armorText = ((EditText) findViewById(R.id.armorNum)).getText().toString();
        if(armorText.equals("")) armorText = "12";
        int armor = Integer.parseInt(armorText);

        String defText = ((EditText) findViewById(R.id.defNum)).getText().toString();
        if(defText.equals("")) defText = "6";
        int def = Integer.parseInt(defText);

        String physMaxText = ((EditText) findViewById(R.id.physNum)).getText().toString();
        if(physMaxText.equals("")) physMaxText = "9";
        int physMax = Integer.parseInt(physMaxText);

        String stunMaxText = ((EditText) findViewById(R.id.stunNum)).getText().toString();
        if(stunMaxText.equals("")) stunMaxText = "9";
        int stunMax = Integer.parseInt(stunMaxText);

        int initiativeScore = 0;
        for(int i = 0; i < initDice;i++){
            initiativeScore+= (r.nextInt(6)+1);
        }
        Combatant combatant = new Combatant(name, initDice, initMod, initiativeScore, armor,
                                           def, physMax, physMax, stunMax, stunMax);



        CombatantDatabase.insert(combatant);

        EditText nm = findViewById(R.id.newCharName);
        nm.setText("");
        EditText pN = findViewById(R.id.physNum);
        pN.setText("");
        EditText sN = findViewById(R.id.stunNum);
        sN.setText("");
        EditText aN = findViewById(R.id.armorNum);
        aN.setText("");
        EditText dN = findViewById(R.id.defNum);
        dN.setText("");
        EditText iD = findViewById(R.id.initDice);
        iD.setText("");
        EditText iM = findViewById(R.id.initMod);
        iM.setText("");



    }


}