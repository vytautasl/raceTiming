package lt.agmis.raceLive.dto;

import lt.agmis.raceLive.domain.RaceEvent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/3/14
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class RaceEventListDto {
    List<RaceEvent> raceEventList;

    public List<RaceEvent> getRaceEventList() {
        return raceEventList;
    }

    public void setRaceEventList(List<RaceEvent> raceEventList) {
        this.raceEventList = raceEventList;
    }
}
