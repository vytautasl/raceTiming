package lt.agmis.raceLive.serviceimpl;

import lt.agmis.raceLive.dao.DeviceDao;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceDao deviceDao;

    @Override
    public List<Device> getDevices(AppUser appUser) {
        return deviceDao.getDevices(appUser);
    }

    @Override
    public List<Device> getDevices(RaceSession raceSession) {
        return deviceDao.getDevices(raceSession);
    }

    @Override
    public Integer saveDevice(Device device) {
        return deviceDao.saveDevice(device);
    }

    @Override
    public Device getDevice(String serialNumber) {
        return deviceDao.getDevice(serialNumber);
    }
}
