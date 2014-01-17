package lt.agmis.raceLive.serviceimpl;

import lt.agmis.raceLive.dao.DeviceDao;
import lt.agmis.raceLive.dao.RawDao;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.domain.Raw;
import lt.agmis.raceLive.service.RawParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RawParseServiceImpl implements RawParseService {
    @Autowired
    private RawDao rawDao;

    @Override
    public void saveRaw(Device device, Double time, String raw) {
        Raw rawObject = new Raw();
        rawObject.setDeviceId(device.getId());
        rawObject.setTime(time);
        rawObject.setRaw(raw);
        rawDao.saveRaw(rawObject);
    }
}
