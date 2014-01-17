package lt.agmis.raceLive.dao;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.dto.ParticipantsDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/2/14
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RaceSessionDao {
    public List<RaceSession> getRaceSessions(RaceEvent raceEvent);
    public Integer saveOrUpdateRaceSession(RaceSession raceSession);
    public RaceSession getActiveRaceSession(AppUser appUser);
    RaceSession getSession(Integer sessionId);
    ParticipantsDto getParticipants(Integer sessionId);
}
