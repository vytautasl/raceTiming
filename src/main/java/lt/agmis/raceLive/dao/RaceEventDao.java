package lt.agmis.raceLive.dao;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.dto.RaceEventListDto;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/2/14
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RaceEventDao {
    public RaceEventListDto getRaceEventList(AppUser appUser);
    public Integer saveOrUpdateRaceEvent(RaceEvent raceEvent);

    RaceEventListDto getMyRaceEventList(AppUser appUser);

    RaceEvent getRaceEvent(Integer id);

    RaceEventListDto getRaceEventListInDays(Integer days);
}
