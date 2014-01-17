package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.SessionUserDao;
import lt.agmis.raceLive.domain.SessionUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/17/14
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SessionUserDaoImpl implements SessionUserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(SessionUser sessionUser) {
        sessionFactory.getCurrentSession().saveOrUpdate(sessionUser);
    }

    @Override
    public List<SessionUser> getUsersOfSession(Integer sessionId) {
        return sessionFactory.getCurrentSession().createQuery("from SessionUser su where su.sessionId="+sessionId).list();
    }
}
