package psu.edu.dfm5288.cmpsc475finproj.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Combatant.class}, version = 1, exportSchema = false)
public abstract class CombatantDatabase extends RoomDatabase{

    public interface CombatantListener {
        void onCombatantReturned(Combatant combatant);
    }

    public abstract CombatantDAO combatantDAO();

    private static CombatantDatabase INSTANCE;

    public static CombatantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CombatantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CombatantDatabase.class, "combatant_database")
                            .addCallback(createCombatantDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createCombatantDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            createCombatantTable();
        }
    };

    private static void createCombatantTable() {

        for(int i = 0; i < DefaultContent.NAME.length; i++){
            insert(new Combatant(DefaultContent.NAME[i],
                    DefaultContent.INITDICE[i],
                    DefaultContent.INITMOD[i],
                    DefaultContent.INITSCORE[i],
                    DefaultContent.ARMOR[i],
                    DefaultContent.DEFENSE[i],
                    DefaultContent.PHYSMAX[i],
                    DefaultContent.PHYSCUR[i],
                    DefaultContent.STUNMAX[i],
                    DefaultContent.STUNCUR[i]));
        }
    }

    public static void getCombatant(String name, CombatantListener listener) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listener.onCombatantReturned((Combatant) msg.obj);
            }
        };

        (new Thread(() -> {
            Message msg = handler.obtainMessage();
            msg.obj = INSTANCE.combatantDAO().getCombatant(name);
            handler.sendMessage(msg);
        })).start();
    }

    public static void insert(Combatant combatant) {
        (new Thread(()-> INSTANCE.combatantDAO().insert(combatant))).start();
    }

    public static void delete(String name) {
        (new Thread(() -> INSTANCE.combatantDAO().delete(name))).start();
    }


    public static void update(Combatant combatant) {
        (new Thread(() -> INSTANCE.combatantDAO().update(combatant))).start();
    }

    public static void deleteAll(){
        (new Thread(() -> INSTANCE.combatantDAO().deleteAll())).start();
    }

    public static void nextPass(){
        (new Thread(() -> INSTANCE.combatantDAO().nextPass(10))).start();
    }

    public static void newTurn(int diceRoll, int initMod, String name){


        (new Thread(()->INSTANCE.combatantDAO().newTurn(diceRoll,initMod,name))).start();
    }

    public static void nextTurn(){
        (new Thread(()->INSTANCE.combatantDAO().nextTurn())).start();
    }






}
