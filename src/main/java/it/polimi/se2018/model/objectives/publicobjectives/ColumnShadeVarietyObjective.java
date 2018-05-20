package it.polimi.se2018.model.objectives.publicobjectives;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Square;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ColumnShadeVarietyObjective extends PublicObjective{
    private static ColumnShadeVarietyObjective instance = null;

    private ColumnShadeVarietyObjective(String title){
        super(title);
        description = "Columns with no repeated values. 4 Point";
    }

    private static synchronized ColumnShadeVarietyObjective createInstance(String title){
        if (instance==null) instance = new ColumnShadeVarietyObjective(title);
        return instance;
    }

    public static ColumnShadeVarietyObjective instance(String title){
        if (instance==null) createInstance(title);
        return instance;
    }

    private static Predicate<Square> filterCol(final int column) {
        return square -> square.getCol() == column;
    }

    @Override
    public int evalPoints(Player player) {
        return ( (int)Stream.of(0,1,2,3,4) //the 5 columns
                .map(column ->
                        StreamSupport.stream(player.getWindow().spliterator(), false)
                                .filter(filterCol(column))
                                .map(Square::getDie)
                                .filter(Objects::nonNull)
                                .map(Die::getValue)
                                .distinct()
                                .count()
                )
                .filter(distinctValues -> distinctValues == 4)
                .count() * 4);
    }
}
