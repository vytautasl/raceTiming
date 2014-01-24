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

    @Override
    public RaceEventListDto getMyRaceEventList(AppUser appUser) {
        RaceEventListDto raceEventListDto = new RaceEventListDto();
        raceEventListDto.setRaceEventList((List<RaceEvent>)sessionFactory.getCurrentSession().createQuery("from RaceEvent re where re.id in (select s.eventId from RaceSession s where s.id in (select su.sessionId from SessionUser su where su.deviceUserName='"+appUser.getPublicId()+"'))").list());
        return raceEventListDto;
    }

    @Override
    public RaceEvent getRaceEvent(Integer id) {
        return (RaceEvent)sessionFactory.getCurrentSession().createQuery("from RaceEvent re where re.id="+id).uniqueResult();
    }
}
