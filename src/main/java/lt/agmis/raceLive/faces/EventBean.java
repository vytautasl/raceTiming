package lt.agmis.raceLive.faces;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.RaceEventListDto;
import lt.agmis.raceLive.faces.utils.CallUtils;
import lt.agmis.raceLive.faces.utils.Messages;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ManagedBean
@SessionScoped
public class EventBean {
    Date beginDate;
    Date endDate;
    String name;
    List<RaceEvent> eventList;
    List<RaceEvent> participatedEventList;
    AppUser owner;
    AppUser participant;
    private int distance;
    double userCenterLat;
    double userCenterLng;

    public void refreshMap()
    {

    }

    public double getUserCenterLat() {
        return userCenterLat;
    }

    public void setUserCenterLat(double userCenterLat) {
        this.userCenterLat = userCenterLat;
    }

    public double getUserCenterLng() {
        return userCenterLng;
    }

    public void setUserCenterLng(double userCenterLng) {
        this.userCenterLng = userCenterLng;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add()
    {
        RaceEvent raceEvent = new RaceEvent();
        AppUser appUser = (AppUser)Messages.getSessionAttribute("appUser");
        raceEvent.setBeginDate(getBeginDate());
        raceEvent.setEndDate(getEndDate());
        raceEvent.setRaceName(getName());
        raceEvent.setRaceOwner(appUser.getId());
        CreateResult result = (CreateResult) CallUtils.postCall("event/add", raceEvent, CreateResult.class, new HashMap());
        refreshEventList();
        Messages.addMessage(result);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("events.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void newEvent()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("newEvent.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public List<RaceEvent> getEventList() {
        if ((getOwner()==null)||(Messages.getAppUser()==null)||(getOwner().getId()!=Messages.getAppUser().getId()))
        {
            refreshEventList();
            setOwner(Messages.getAppUser());
        }

        return eventList;
    }

    public void setEventList(List<RaceEvent> eventList) {
        this.eventList = eventList;
    }

    public void updateRow(RowEditEvent event)
    {
        RaceEvent raceEvent = (RaceEvent)event.getObject();
        CreateResult result = (CreateResult) CallUtils.postCall("event/add", raceEvent, CreateResult.class, new HashMap());
        refreshEventList();
        Messages.addMessage(result);
    }

    public void showSessions(Integer eventId)
    {
        Messages.setSessionAttribute("race-event-id", eventId);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("sessions.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void showMySessions(Integer eventId)
    {
        Messages.setSessionAttribute("race-event-id", eventId);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("mySessions.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    private void refreshEventList() {
        AppUser appUser = Messages.getAppUser();
        RaceEventListDto raceEventListDto = (RaceEventListDto)CallUtils.getCall("event/get/"+appUser.getId(), RaceEventListDto.class, new HashMap());
        eventList = raceEventListDto.getRaceEventList();
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public List<RaceEvent> getParticipatedEventList() {
        if ((getParticipant()==null)||(Messages.getAppUser()==null)||(getParticipant().getId()!=Messages.getAppUser().getId()))
        {
            setParticipant(Messages.getAppUser());
            RaceEventListDto raceEventListDto = (RaceEventListDto)CallUtils.getCall("event/getMy/"+getParticipant().getId(), RaceEventListDto.class, new HashMap());
            participatedEventList = raceEventListDto.getRaceEventList();
        }
        return participatedEventList;
    }

    public void setParticipatedEventList(List<RaceEvent> participatedEventList) {
        this.participatedEventList = participatedEventList;
    }

    public AppUser getParticipant() {
        return participant;
    }

    public void setParticipant(AppUser participant) {
        this.participant = participant;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
