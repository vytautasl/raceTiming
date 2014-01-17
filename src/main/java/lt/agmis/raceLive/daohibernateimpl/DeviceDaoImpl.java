package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.DeviceDao;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.domain.RaceSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceDaoImpl implements DeviceDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Device> getDevices(AppUser appUser) {
        return (List<Device>)sessionFactory.getCurrentSession().createQuery("from Device d where d.deviceOwner="+appUser.getId()).list();
    }

    @Override
    public List<Device> getDevices(RaceSession raceSession) {
        return (List<Device>)sessionFactory.getCurrentSession().createQuery("from Device d where d.id in (select c.deviceId from Checkpoint c where c.sessionId=" + raceSession.getId() + ")").list();
    }

    @Override
    public Integer saveDevice(Device device) {
        sessionFactory.getCurrentSession().saveOrUpdate(device);
        return device.getId();
    }

    @Override
    public Device getDevice(String serialNumber) {
        return (Device)sessionFactory.getCurrentSession().createQuery("from Device d where d.serialNumber='"+serialNumber+"'").uniqueResult();
    }

}
