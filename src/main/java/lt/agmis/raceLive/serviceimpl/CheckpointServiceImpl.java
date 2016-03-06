package lt.agmis.raceLive.serviceimpl;

import lt.agmis.raceLive.dao.CheckpointDao;
import lt.agmis.raceLive.dao.DeviceDao;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.models.ParsedDecoderData;
import lt.agmis.raceLive.service.CheckpointService;
import lt.agmis.raceLive.service.RaceService;
import lt.agmis.raceLive.util.parsers.ParserFactory;
import lt.agmis.raceLive.util.parsers.RawDecoderDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by m.jankus on 2016-02-21.
 */
@Service
@Transactional
public class CheckpointServiceImpl implements CheckpointService {

    @Autowired
    CheckpointDao checkpointDao;
    @Autowired
    RaceService raceService;
    @Autowired
    DeviceDao deviceDao;

    @Override
    public void createCheckpoint(Device device, String rawData, String decoderSerialNumber) {
        ParserFactory parserFactory = new ParserFactory();

        RawDecoderDataParser parser = parserFactory.CreateParser(decoderSerialNumber);
        ParsedDecoderData parsedDecoderData = parser.parseData(rawData);

        raceService.addCheckpoint(device, parsedDecoderData.getTime());
    }


}
