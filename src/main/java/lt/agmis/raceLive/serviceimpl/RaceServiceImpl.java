package lt.agmis.raceLive.serviceimpl;

import lt.agmis.raceLive.dao.*;
import lt.agmis.raceLive.domain.*;
import lt.agmis.raceLive.dto.ParticipantsDto;
import lt.agmis.raceLive.dto.RaceEventListDto;
import lt.agmis.raceLive.dto.RaceSessionListDto;
import lt.agmis.raceLive.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RaceServiceImpl implements RaceService{
    @Autowired
    private CheckpointDao checkpointDao;
    @Autowired
    private RaceSessionDao raceSessionDao;
    @Autowired
    private RaceEventDao raceEventDao;
    @Autowired
    private AppUserDao appUserDao;
    @Autowired
    private SessionUserDao sessionUserDao;

    @Override
    public void addCheckpoint(Device device, Double time) {
        Checkpoint inputData = new Checkpoint();
        inputData.setDeviceId(device.getId());
        inputData.setTime(time);
        AppUser appUser = appUserDao.getOwner(device);
        if (appUser!=null)
        {
            RaceSession raceSession = raceSessionDao.getActiveRaceSession(appUser);
            inputData.setSessionId(raceSession.getId());
        } else {
            inputData.setSessionId(-1);
        }
        checkpointDao.createCheckpoint(inputData);
    }

    @Override
    public void addRaceSession(RaceSession raceSession) {
        raceSessionDao.saveOrUpdateRaceSession(raceSession);
    }

    @Override
    public void addRaceEvent(RaceEvent raceEvent) {
        raceEventDao.saveOrUpdateRaceEvent(raceEvent);
    }

    @Override
    public RaceEventListDto getEvents(Integer userId) {
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        return raceEventDao.getRaceEventList(appUser);
    }

    @Override
    public RaceSessionListDto getSessions(Integer raceEventId) {
        RaceEvent raceEvent = new RaceEvent();
        raceEvent.setId(raceEventId);
        RaceSessionListDto raceSessionList = new RaceSessionListDto();
        raceSessionList.setRaceSessionList(raceSessionDao.getRaceSessions(raceEvent));
        return raceSessionList;
    }

    @Override
    public RaceSession getSession(Integer sessionId) {
        return raceSessionDao.getSession(sessionId);
    }

    @Override
    public ParticipantsDto getParticipants(Integer sessionId) {
        ParticipantsDto participantsDto = raceSessionDao.getParticipants(sessionId);
        return participantsDto;
    }

    @Override
    public void setParticipants(Integer sessionId, ParticipantsDto participantsDto) {
        if (participantsDto!=null)
        {
            for (SessionUser sessionUser:participantsDto.getParticipants())
            {
                sessionUserDao.save(sessionUser);
            }
        }
    }

    @Override
    public RaceSession getActiveSession(AppUser appUser) {
        return raceSessionDao.getActiveRaceSession(appUser);
    }

    @Override
    public RaceEventListDto getMyEvents(Integer userId) {
        AppUser appUser = appUserDao.getInfo(userId);
        return raceEventDao.getMyRaceEventList(appUser);
    }

    @Override
    public RaceEvent getEvent(Integer id) {
        return raceEventDao.getRaceEvent(id);
    }

    @Override
    public RaceEventListDto getEventsInDays(Integer days) {
        return raceEventDao.getRaceEventListInDays(days);
    }

}
