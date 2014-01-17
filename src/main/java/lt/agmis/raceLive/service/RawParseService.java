package lt.agmis.raceLive.service;

import lt.agmis.raceLive.domain.Device;

public interface RawParseService {
    void saveRaw(Device device, Double time, String raw);
}
