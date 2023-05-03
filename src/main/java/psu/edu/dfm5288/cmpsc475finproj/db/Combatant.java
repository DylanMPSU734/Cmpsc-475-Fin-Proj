package psu.edu.dfm5288.cmpsc475finproj.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="combatants")
public class Combatant {

    public Combatant(String name, int initDice, int initMod, int initScore, int armor, int defense,
                     int physMax, int physCur, int stunMax, int stunCur) {

        this.name = name;
        this.initDice = initDice;
        this.initMod = initMod;
        this.initScore = initScore;
        this.armor = armor;
        this.defense = defense;
        this.physMax = physMax;
        this.physCur = physCur;
        this.stunMax = stunMax;
        this.stunCur = stunCur;

    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "initDice")
    public int initDice;

    @ColumnInfo(name = "initMod")
    public int initMod;

    @ColumnInfo(name = "initScore")
    public int initScore;

    @ColumnInfo(name = "armor")
    public int armor;

    @ColumnInfo(name = "defense")
    public int defense;

    @ColumnInfo(name = "physMax")
    public int physMax;

    @ColumnInfo(name = "physCur")
    public int physCur;

    @ColumnInfo(name = "stunMax")
    public int stunMax;

    @ColumnInfo(name = "stunCur")
    public int stunCur;

}
