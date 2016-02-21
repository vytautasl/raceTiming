package lt.agmis.raceLive.service;

import lt.agmis.raceLive.domain.Device;

/**
 * Created by m.jankus on 2016-02-21.
 */
public interface CheckpointService {
    void createCheckpoint(Device device, String rawData, String decoderType);
}
