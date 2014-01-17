package lt.agmis.raceLive.dao;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.domain.RaceSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/2/14
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceDao {
    public List<Device> getDevices(AppUser appUser);
    public List<Device> getDevices(RaceSession raceSession);
    public Integer saveDevice(Device device);
    Device getDevice(String serialNumber);
}
