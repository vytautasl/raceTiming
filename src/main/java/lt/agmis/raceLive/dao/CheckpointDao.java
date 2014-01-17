package lt.agmis.raceLive.dao;

import lt.agmis.raceLive.domain.Checkpoint;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/2/14
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CheckpointDao {
    public List<Checkpoint> getCheckpoints(Integer sessionId);
    public Integer createCheckpoint(Checkpoint checkpoint);

}
