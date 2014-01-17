package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.CheckpointDao;
import lt.agmis.raceLive.domain.Checkpoint;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/2/14
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CheckpointDaoImpl implements CheckpointDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Checkpoint> getCheckpoints(Integer sessionId) {
        return (List<Checkpoint>) sessionFactory.getCurrentSession().createQuery("from Checkpoint c where c.sessionId=" + sessionId + " order by c.deviceId, c.time asc").list();
    }

    @Override
    public Integer createCheckpoint(Checkpoint checkpoint) {
        sessionFactory.getCurrentSession().save(checkpoint);
        return checkpoint.getId();
    }


}
