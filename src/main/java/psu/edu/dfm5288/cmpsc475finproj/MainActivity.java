package psu.edu.dfm5288.cmpsc475finproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import psu.edu.dfm5288.cmpsc475finproj.db.Combatant;
import psu.edu.dfm5288.cmpsc475finproj.db.CombatantViewModel;
import psu.edu.dfm5288.cmpsc475finproj.db.CombatantDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    List<Combatant> combatants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up toolbar
        Toolbar tB= findViewById(R.id.toolbar);
        setSupportActionBar(tB);

        //create the buttons
        Button next = (Button) findViewById(R.id.nextPass);
        next.setOnClickListener(this); // calling onClick() method
        Button newT = (Button) findViewById(R.id.newTurn);
        newT.setOnClickListener(this);
        Button clr = (Button) findViewById(R.id.clearCombat);
        clr.setOnClickListener(this);

        //create the recylcerview
        RecyclerView recyclerView = findViewById(R.id.initiativeTracker);
        CombatantListAdapter adapter = new CombatantListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.combatants = adapter.combatants;

        CombatantViewModel combatantViewModel =
                new ViewModelProvider(this).get(CombatantViewModel.class);

        combatantViewModel.getAllCombatants().observe(this, adapter::setCombatants);


    }

    public class CombatantListAdapter extends RecyclerView.Adapter<CombatantListAdapter.CombatantViewHolder> {

        class CombatantViewHolder extends RecyclerView.ViewHolder {
            private final TextView nameView;
            private final TextView initValView;
            private final EditText physCur;
            private final TextView physMax;
            private final EditText stunCur;
            private final TextView stunMax;
            private final TextView armor;
            private final TextView defense;
            private final ImageView delete;
            private final CheckBox chkBox;
            private final TextView initText;
            private final TextView physText;
            private final TextView stunText;
            private final TextView defenseText;
            private final TextView armorText;

            /*
            private final Guideline gL1;
            private final Guideline gL2;
            private final Guideline gL3;
            private final Guideline gL4;
            private final Guideline gL5;
            private final Guideline gL6;
            private final Guideline gL7;
            */

            private Combatant combatant;

            private CombatantViewHolder(View itemView) {
                super(itemView);
                nameView = itemView.findViewById(R.id.initName);
                initValView = itemView.findViewById(R.id.initNumVal);
                physCur = itemView.findViewById(R.id.initPhysNumCur);
                physMax = itemView.findViewById(R.id.initPhysMax);
                stunCur = itemView.findViewById(R.id.initStunNumCur);
                stunMax = itemView.findViewById(R.id.initStunMax);
                armor = itemView.findViewById(R.id.initArmorNum);
                defense = itemView.findViewById(R.id.initDefNum);

                delete = itemView.findViewById(R.id.deleteThis);
                chkBox = itemView.findViewById(R.id.hasActed);
                initText = itemView.findViewById(R.id.initiativeVal);
                physText = itemView.findViewById(R.id.initPhys);
                stunText = itemView.findViewById(R.id.initStun);
                defenseText = itemView.findViewById(R.id.initDefense);
                armorText = itemView.findViewById(R.id.initArmor);

                /*
                gL1 = itemView.findViewById(R.id.guideline33);
                gL2 = itemView.findViewById(R.id.guideline2);
                gL3 = itemView.findViewById(R.id.guideline40);
                gL4 = itemView.findViewById(R.id.guideline3);
                gL5 = itemView.findViewById(R.id.guideline4);
                gL6 = itemView.findViewById(R.id.guideline8);
                gL7 = itemView.findViewById(R.id.guideline9);
                */


                itemView.setOnLongClickListener(view -> {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(intent);
                    return true;
                });
            }
        }

        private final LayoutInflater layoutInflater;
        private List<Combatant> combatants;

        CombatantListAdapter(Context context) { layoutInflater = LayoutInflater.from(context);}

        @Override
        public CombatantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.combatant_item, parent, false);
            return new CombatantViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CombatantViewHolder holder, int position) {
            if(combatants != null){
                Combatant current = combatants.get(position);
                holder.combatant = current;

                holder.nameView.setText(current.name);
                holder.initValView.setText(Integer.toString(current.initScore));
                holder.physCur.setText(Integer.toString(current.physCur));
                holder.physMax.setText("/" + Integer.toString(current.physMax));
                holder.stunCur.setText(Integer.toString(current.stunCur));
                holder.stunMax.setText("/" + Integer.toString(current.stunMax));
                holder.armor.setText(Integer.toString(current.armor));
                holder.defense.setText(Integer.toString(current.defense));

                holder.delete.setImageResource(R.drawable.ic_action_delete);
                holder.chkBox.setText("");
                holder.initText.setText("Initiative ");
                holder.physText.setText("Phys Cond ");
                holder.stunText.setText("Stun Cond");
                holder.armorText.setText("Armor");
                holder.defenseText.setText("Defense");

            }

            else{
                holder.nameView.setText("...");
                holder.initValView.setText("0");
                holder.physCur.setText("9");
                holder.physMax.setText("/9");
                holder.stunCur.setText("9");
                holder.stunMax.setText("/9");
                holder.armor.setText("12");
                holder.defense.setText("6");

                holder.delete.setImageResource(R.drawable.ic_action_delete);
                holder.chkBox.setText("");
                holder.initText.setText("Initiative ");
                holder.physText.setText("Phys Cond ");
                holder.stunText.setText("Stun Cond");
                holder.armorText.setText("Armor");
                holder.defense.setText("Defense");
            }


        }

        void setCombatants(List<Combatant> combatants){
            this.combatants = combatants;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if(combatants != null){ return combatants.size(); }
            else return 0;
        }

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

        if(id == R.id.action_add){
            intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.action_settings){
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    //handle button clicks
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.nextPass){
            CombatantDatabase.nextPass();

        }
        else if(id == R.id.newTurn){
            /*
            Random R = new Random();

            int initiative=0;
            for(int i=0;i<combatants.size();i++){
                String name = combatants.get(i).name;
                int initMod = combatants.get(i).initScore;
                for(int j=0; j< combatants.get(i).initDice; j++){
                    initiative += (R.nextInt(6)+1);
                }
                CombatantDatabase.newTurn(initiative, initMod, name);
            } */

            CombatantDatabase.nextTurn();
            Log.d("Test","newTurn");
        }
        else if(id == R.id.clearCombat){
            CombatantDatabase.deleteAll();
        }

    }
}