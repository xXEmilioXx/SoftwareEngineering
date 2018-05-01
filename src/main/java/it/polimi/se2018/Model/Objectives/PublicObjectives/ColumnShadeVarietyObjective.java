package it.polimi.se2018.Model.Objectives.PublicObjectives;

import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Model.Square;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ColumnShadeVarietyObjective extends PublicObjective{
    private static ColumnShadeVarietyObjective instance = null;

    private ColumnShadeVarietyObjective(String imagePath, String title){
        super(imagePath,title);
    }
    private static synchronized ColumnShadeVarietyObjective createInstance(String imagePath, String title){
        if (instance==null) instance = new ColumnShadeVarietyObjective(imagePath, title);
        return instance;
    }

    public static ColumnShadeVarietyObjective instance(String imagePath, String title){
        if (instance==null) createInstance(imagePath, title);
        return instance;
    }

    private static Predicate<Square> filterCol(final int column) {
        return square -> square.getCol() == column;
    }

    @Override
    public int evalPoints(Player player) {
        return ( (int)Stream.of(1,2,3,4,5) //the 5 columns
                .map(column ->
                        StreamSupport.stream(player.getMap().spliterator(), false)
                                .filter(filterCol(column))
                                .map(Square::getDie)
                                .filter(Objects::nonNull)
                                .map(Die::getColor)
                                .distinct()
                                .count()
                )
                .filter(distinctValues -> distinctValues == 4)
                .count() * 4);
    }
}
