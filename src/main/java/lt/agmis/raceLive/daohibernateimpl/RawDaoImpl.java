package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.RawDao;
import lt.agmis.raceLive.domain.Raw;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/14/14
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RawDaoImpl implements RawDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveRaw(Raw rawObject) {
        sessionFactory.getCurrentSession().saveOrUpdate(rawObject);
    }
}
