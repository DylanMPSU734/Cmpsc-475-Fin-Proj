package psu.edu.dfm5288.cmpsc475finproj.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CombatantDAO {


    @Query("SELECT * FROM combatants ORDER BY initScore DESC")
    LiveData<List<Combatant>> getAll();

    @Query("SELECT * FROM combatants WHERE name = :name")
    Combatant getCombatant(String name);

    @Query("UPDATE combatants SET initScore = (initScore - :sub)")
    void nextPass(int sub);

    @Query("UPDATE combatants SET initScore = :initMod + :diceRoll WHERE name = :name")
    void newTurn(int diceRoll, int initMod, String name);

    @Query("UPDATE combatants SET initScore = (ABS(RANDOM() % 6) * initDice)+ initMod ")
    void nextTurn();

    @Insert
    void insert(Combatant... combatant);

    @Update
    void update(Combatant... combatant);

    @Delete
    void delete(Combatant... combatant);

    @Query("DELETE FROM combatants WHERE name = :name")
    void delete(String name);

    @Query("DELETE FROM combatants")
    void deleteAll();





}

