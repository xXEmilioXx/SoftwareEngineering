package it.polimi.se2018.Model.Objectives.PrivateObjectives;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Player;

import java.util.stream.StreamSupport;

public class ShadesOfGreenObjective extends PrivateObjective {
    private static ShadesOfGreenObjective instance = null;
    final private static Color color = Color.GREEN;

    private ShadesOfGreenObjective(String imagePath, String title){
        super(imagePath,title);
    }
    private synchronized static ShadesOfGreenObjective createInstance(String imagePath, String title){
        if (instance==null) instance = new ShadesOfGreenObjective(imagePath, title);
        return instance;
    }

    public static ShadesOfGreenObjective instance(String imagePath, String title){
        if (instance==null) createInstance(imagePath, title);
        return instance;
    }
    public int evalPoints(Player player){
        return (int) StreamSupport.stream(player.getMap().spliterator(),false)
                .filter(square -> square.getDie().getColor()==color)
                .count();
    }
}
