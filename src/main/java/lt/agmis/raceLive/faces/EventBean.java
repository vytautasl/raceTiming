package lt.agmis.raceLive.faces;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.RaceEventListDto;
import lt.agmis.raceLive.faces.utils.CallUtils;
import lt.agmis.raceLive.faces.utils.Messages;
import org.primefaces.event.RowEditEvent;

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
    AppUser owner;

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
        if (eventList==null)
        {
            refreshEventList();
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

    private void refreshEventList() {
        AppUser appUser = Messages.getAppUser();
        RaceEventListDto raceEventListDto = (RaceEventListDto)CallUtils.getCall("event/get/"+appUser.getId(), RaceEventListDto.class, new HashMap());
        eventList = raceEventListDto.getRaceEventList();
    }

    public void showResults(Integer sessionId)
    {
        try {
            Messages.setSessionAttribute("race-session-id", sessionId);
            FacesContext.getCurrentInstance().getExternalContext().redirect("results.xhtml");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
