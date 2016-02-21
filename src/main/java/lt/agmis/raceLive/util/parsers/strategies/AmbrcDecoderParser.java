package lt.agmis.raceLive.util.parsers.strategies;

import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.models.ParsedDecoderData;
import lt.agmis.raceLive.util.parsers.interfaces.ParserStrategy;

/**
 * Created by m.jankus on 2016-02-21.
 */
public class AmbrcDecoderParser implements ParserStrategy {

    @Override
    public ParsedDecoderData parseCheckpoint(String raw) {
        ParsedDecoderData newCheckpoint = new ParsedDecoderData();
        setData(newCheckpoint, raw);
        return newCheckpoint;
    }

    private void setData(ParsedDecoderData parsedDecoderData, String rawData){
        String[] rawDataParts = rawData.split("t");
        parsedDecoderData.setTime(Double.parseDouble(cleanStringData(rawDataParts[4])));
        parsedDecoderData.setTransponderNumber(Integer.parseInt(cleanStringData(rawDataParts[3])));
    }

    private String cleanStringData(String data){
        return data.substring(0, data.length()-1);
    }
}
