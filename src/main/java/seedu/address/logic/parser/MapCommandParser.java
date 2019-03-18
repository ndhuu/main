package seedu.address.logic.parser;

import javafx.util.Pair;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.MapObject;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntaxProject.*;

public class MapCommandParser implements Parser<MapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_YEAR, PREFIX_RACE, PREFIX_MEDICAL);

        if (noPrefixes(argMultimap) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }
        MapObject newMap = parseCriteria(argMultimap);
        return new MapCommand(newMap);
    }

    /**
     * Returns true if the argMultimap contains any valid prefixes.1
     */
    private static boolean noPrefixes(ArgumentMultimap argMultimap){
        if(argMultimap.getAllValues(PREFIX_YEAR).isEmpty() && argMultimap.getAllValues(PREFIX_RACE).isEmpty()
                && argMultimap.getAllValues(PREFIX_MEDICAL).isEmpty()){
            return true;}
        return false;
    }

    /**
     * Splits tokens into proper data for MapCommand() to handle
     */
    private MapObject parseCriteria(ArgumentMultimap argMultimap){
        int prefixPoints;
        String prefixCriteria;
        String yearOperator = null;
        String criteriaHolder;

        Pair<Integer, Integer> localAgePair = new Pair<>(0,0);
        Pair<Integer, String> localRacePair = new Pair<>(0,"");
        Pair<Integer, String> localMedicalPair = new Pair<>(0,"");

        //TODO: write input checks on the criteria for all
        if(!argMultimap.getAllValues(PREFIX_YEAR).isEmpty()){
            criteriaHolder = argMultimap.getValue(PREFIX_YEAR).get();
            prefixPoints = Integer.parseInt(criteriaHolder.substring(0,1));
            yearOperator = criteriaHolder.substring(1,2);
            prefixCriteria = criteriaHolder.substring(2,criteriaHolder.length());
            localAgePair = new Pair<>(prefixPoints,Integer.parseInt(prefixCriteria));
        } else {
            localAgePair = new Pair<>(0,0);
        }

        if(!argMultimap.getAllValues(PREFIX_RACE).isEmpty()){
            criteriaHolder = argMultimap.getValue(PREFIX_RACE).get();
            prefixPoints = Integer.parseInt(criteriaHolder.substring(0,1));
            prefixCriteria = criteriaHolder.substring(1,criteriaHolder.length());
            localRacePair = new Pair<>(prefixPoints,prefixCriteria);
        } else {
            localRacePair = new Pair<>(0,"");
        }

        if(!argMultimap.getAllValues(PREFIX_MEDICAL).isEmpty()){
            criteriaHolder = argMultimap.getValue(PREFIX_MEDICAL).get();
            prefixPoints = Integer.parseInt(criteriaHolder.substring(0,1));
            prefixCriteria = criteriaHolder.substring(1,criteriaHolder.length());
            localMedicalPair = new Pair<>(prefixPoints,prefixCriteria);
        } else {
            localMedicalPair = new Pair<>(0,"");
        }

        MapObject newMap = new MapObject(localAgePair, yearOperator, localRacePair, localMedicalPair);
        return newMap;
    }

}
