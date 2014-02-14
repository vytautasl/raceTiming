package lt.agmis.raceLive.service;

import lt.agmis.raceLive.domain.*;
import lt.agmis.raceLive.dto.ParticipantsDto;
import lt.agmis.raceLive.dto.RaceEventListDto;
import lt.agmis.raceLive.dto.RaceSessionListDto;

public interface RaceService {
    void addCheckpoint(Device device, Double time);
    void addRaceSession(RaceSession raceSession);
    void addRaceEvent(RaceEvent raceEvent);
    RaceEventListDto getEvents(Integer userId);
    RaceSessionListDto getSessions(Integer raceEventId);
    RaceSession getSession(Integer sessionId);
    ParticipantsDto getParticipants(Integer sessionId);
    void setParticipants(Integer sessionId, ParticipantsDto participantsDto);

    RaceSession getActiveSession(AppUser appUser);

    RaceEventListDto getMyEvents(Integer userId);

    RaceEvent getEvent(Integer id);

    RaceEventListDto getEventsInDays(Integer days);
}
