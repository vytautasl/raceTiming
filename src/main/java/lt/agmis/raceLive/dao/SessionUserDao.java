package lt.agmis.raceLive.dao;

import lt.agmis.raceLive.domain.SessionUser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/17/14
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SessionUserDao {
    void save(SessionUser sessionUser);
    List<SessionUser> getUsersOfSession(Integer sessionId);
}
