package lt.agmis.raceLive.util.parsers.interfaces;

import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.models.ParsedDecoderData;

/**
 * Created by m.jankus on 2016-02-21.
 */
public interface ParserStrategy {
    ParsedDecoderData parseCheckpoint(String raw);
}
