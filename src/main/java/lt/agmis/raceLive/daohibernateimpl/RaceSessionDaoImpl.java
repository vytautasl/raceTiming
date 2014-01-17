package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.RaceSessionDao;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.domain.SessionUser;
import lt.agmis.raceLive.dto.ParticipantsDto;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RaceSessionDaoImpl implements RaceSessionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<RaceSession> getRaceSessions(RaceEvent raceEvent) {
        return (List<RaceSession>)sessionFactory.getCurrentSession().createQuery("from RaceSession rs where rs.eventId="+raceEvent.getId() + " order by rs.executionDate desc").list();
    }

    @Override
    public Integer saveOrUpdateRaceSession(RaceSession raceSession) {
        sessionFactory.getCurrentSession().saveOrUpdate(raceSession);
        return raceSession.getId();
    }

    @Override
    public RaceSession getActiveRaceSession(AppUser appUser) {
        return (RaceSession)sessionFactory.getCurrentSession().createQuery("from RaceSession rs where active=true and rs.id in (select rss.id from RaceSession rss, RaceEvent re where rss.eventId=re.id and re.raceOwner="+appUser.getId()+") order by rs.executionDate").uniqueResult();
    }

    @Override
    public RaceSession getSession(Integer sessionId) {
        return (RaceSession)sessionFactory.getCurrentSession().createQuery("from RaceSession rs where rs.id="+sessionId).uniqueResult();
    }

    @Override
    public ParticipantsDto getParticipants(Integer sessionId) {
        String sqlQuery = "select null as sessionUserId, d.id as deviceId, d.defaultName as displayName, d.defaultNumber as defaultNumber, d.defaultNumber as displayNumber, null as deviceUser, null as deviceUserName, d.serialNumber as deviceSerialNumber, d.defaultNumber as kartNumber from device d " +
                                  " where " +
                                        " d.id in (select deviceId from checkpoint c1 where c1.sessionId="+sessionId+" group by c1.deviceId) and "+
                                        " d.id not in (select deviceId from session_user su1 where su1.sessionId="+sessionId+" group by su1.deviceId) "+
                           " union all " +
                           " select su.id as sessionUserId, su.deviceId as deviceId, su.sessionDisplayName as displayName, d.defaultNumber as defaultNumber, su.sessionDisplayKart as displayNumber, su.deviceUserId as deviceUser, au.email as deviceUserName, d.serialNumber as deviceSerialNumber, su.sessionDisplayKart as kartNumber from session_user su left outer join app_user au on au.id=su.deviceUserId left outer join device d on d.id=su.deviceId where su.sessionId="+sessionId
                ;
        ArrayList<HashMap> result = (ArrayList<HashMap>)sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        ParticipantsDto participantsDto = new ParticipantsDto();
        List<SessionUser> sessionUsers = new ArrayList<SessionUser>();
        for (HashMap row: result)
        {
            SessionUser sessionUser = new SessionUser();
            sessionUser.setId((Integer)row.get("sessionUserId"));
            sessionUser.setDeviceId((Integer)row.get("deviceId"));
            sessionUser.setSessionDisplayName((String) row.get("displayName"));
            sessionUser.setSessionDisplayKart((String) row.get("displayNumber"));
            sessionUser.setDeviceUserId((Integer) row.get("deviceUser"));
            sessionUser.setDeviceUserName((String) row.get("deviceUserName"));
            sessionUser.setDeviceSerialNumber((String) row.get("deviceSerialNumber"));
            sessionUser.setDeviceDefaultKartNumber((String) row.get("defaultNumber"));
            sessionUser.setSessionId(sessionId);
            sessionUsers.add(sessionUser);
        }
        participantsDto.setParticipants(sessionUsers);

        return participantsDto;
    }

}
