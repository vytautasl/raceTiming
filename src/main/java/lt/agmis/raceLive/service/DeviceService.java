package lt.agmis.raceLive.service;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.domain.RaceSession;

import java.util.List;

public interface DeviceService {
    public List<Device> getDevices(AppUser appUser);
    public List<Device> getDevices(RaceSession raceSession);
    public Integer saveDevice(Device device);
    Device getDevice(String serialNumber);
    Device createDefaultDevice(String serialNumber);
}
