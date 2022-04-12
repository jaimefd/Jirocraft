package me.jiroscopio.jirocraftplugin.records.drops;

/*
* THE THREE L's OF DROP RATES
*
* Luck -> Higher chance of a pool activating (for rare single entry pools mostly)
* Looting -> More rolls, for more amount of items basically
* Luxury -> Higher chance of items tiering up
* */

import java.util.ArrayList;

public record DropsRecord(String id, ArrayList<DropPool> pools) {
}
