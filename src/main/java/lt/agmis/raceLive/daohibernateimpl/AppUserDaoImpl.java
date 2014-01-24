package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.AppUserDao;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.PublicUsernamesDto;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AppUserDaoImpl implements AppUserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public AppUser login(AppUser appUser) {
        AppUser loggedIn = null;

        try
        {
            loggedIn =(AppUser)sessionFactory.getCurrentSession().createQuery("from AppUser au where au.userName='"+appUser.getUserName()+"' and au.userPass='"+appUser.getUserPass()+"'").uniqueResult();
        } catch (Exception e)
        {
            System.out.println("User not found: " + e.getMessage());
        }
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

    @Override
    public AppUser getUserByPublicId(String publicId) {
        AppUser appUser = (AppUser)sessionFactory.getCurrentSession().createQuery("from AppUser au where au.publicId='"+publicId+"'").uniqueResult();
        return appUser;
    }

    @Override
    public PublicUsernamesDto getPublicUserNamesLike(String criteria) {
        PublicUsernamesDto resultList = new PublicUsernamesDto();
        String sqlQuery = "select publicId from app_user where publicId like '%"+criteria+"%'";
        ArrayList<HashMap> result = (ArrayList<HashMap>)sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<String> resultSet = new ArrayList<String>();
        for (HashMap row: result)
        {
            resultSet.add((String)row.get("publicId"));
        }
        resultList.setUserNames(resultSet);
        return resultList;
    }
}
