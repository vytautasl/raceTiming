package lt.agmis.raceLive.util.parsers;

import lt.agmis.raceLive.models.DecoderType;
import lt.agmis.raceLive.util.parsers.interfaces.ParserStrategy;
import lt.agmis.raceLive.util.parsers.strategies.AmbrcDecoderParser;

/**
 * Created by m.jankus on 2016-02-21.
 */
public class ParserFactory {
    public RawDecoderDataParser CreateParser(String decoderSerialNumber){
        ParserStrategy parserStrategy = null;
        DecoderType decoderType = resolveDecoderType(decoderSerialNumber);

        if(decoderType.equals(DecoderType.AMBRC_V1)){
            parserStrategy = new AmbrcDecoderParser();
        }

        return new RawDecoderDataParser(parserStrategy);
    }

    private DecoderType resolveDecoderType(String decoderSerialNumber){
        //need to decide how to implement this logic
        if(decoderSerialNumber != null){
        }
        return DecoderType.AMBRC_V1;
    }

}
