package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.AppUserDao;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.dto.CreateResult;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppUserDaoImpl implements AppUserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public AppUser login(AppUser appUser) {
        AppUser loggedIn = (AppUser)sessionFactory.getCurrentSession().createQuery("from AppUser au where au.userName='"+appUser.getUserName()+"' and au.userPass='"+appUser.getUserPass()+"'").uniqueResult();
        return loggedIn;
    }

    @Override
    public Integer saveOrUpdateUser(AppUser appUser) {
        sessionFactory.getCurrentSession().saveOrUpdate(appUser);
        return appUser.getId();
    }

    @Override
    public AppUser getOwner(Device device) {
        AppUser owner = (AppUser)sessionFactory.getCurrentSession().createQuery("from AppUser au where au.id in (select d.deviceOwner from Device d where d.serialNumber='"+device.getSerialNumber()+"')").uniqueResult();
        return owner;
    }

    @Override
    public AppUser getInfo(Integer userId) {
        AppUser appUser = (AppUser)sessionFactory.getCurrentSession().createQuery("from AppUser au where au.id='"+userId+"'").uniqueResult();
        return appUser;
    }
}
