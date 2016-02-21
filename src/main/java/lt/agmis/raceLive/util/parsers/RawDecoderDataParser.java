package lt.agmis.raceLive.util.parsers;

import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.models.ParsedDecoderData;
import lt.agmis.raceLive.util.parsers.interfaces.ParserStrategy;

/**
 * Created by m.jankus on 2016-02-21.
 */
public class RawDecoderDataParser  {
    private ParserStrategy parser;

    public RawDecoderDataParser(ParserStrategy parser) {
        this.parser = parser;
    }

    public ParsedDecoderData parseData(String raw){
        return parser.parseCheckpoint(raw);
    }
}
