package lt.agmis.raceLive.daohibernateimpl;

import lt.agmis.raceLive.dao.RaceEventDao;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.dto.RaceEventListDto;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RaceEventDaoImpl implements RaceEventDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public RaceEventListDto getRaceEventList(AppUser appUser) {
        RaceEventListDto raceEventListDto = new RaceEventListDto();
        raceEventListDto.setRaceEventList((List<RaceEvent>)sessionFactory.getCurrentSession().createQuery("from RaceEvent re where re.raceOwner="+appUser.getId()+ " order by re.beginDate desc").list());
        return raceEventListDto;
    }

    @Override
    public Integer saveOrUpdateRaceEvent(RaceEvent raceEvent) {
        sessionFactory.getCurrentSession().saveOrUpdate(raceEvent);
        return raceEvent.getId();
    }
}
