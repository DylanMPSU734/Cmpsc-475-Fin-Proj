package psu.edu.dfm5288.cmpsc475finproj.db;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CombatantViewModel extends AndroidViewModel{
    private LiveData<List<Combatant>> combatants;

    public CombatantViewModel(Application application){
        super(application);
        combatants = CombatantDatabase.getDatabase(getApplication()).combatantDAO().getAll();
    }

    public LiveData<List<Combatant>> getAllCombatants(){return combatants;}


}

