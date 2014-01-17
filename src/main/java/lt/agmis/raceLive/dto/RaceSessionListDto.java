package lt.agmis.raceLive.dto;

import lt.agmis.raceLive.domain.RaceSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/3/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class RaceSessionListDto {
    List<RaceSession> raceSessionList;

    public List<RaceSession> getRaceSessionList() {
        return raceSessionList;
    }

    public void setRaceSessionList(List<RaceSession> raceSessionList) {
        this.raceSessionList = raceSessionList;
    }
}
