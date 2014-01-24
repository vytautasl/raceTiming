package lt.agmis.raceLive.faces;

import antlr.debug.MessageAdapter;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.RaceEventListDto;
import lt.agmis.raceLive.dto.RaceSessionListDto;
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
public class SessionBean {
    String name;
    Date executionDate;
    Integer lapLimit;
    Integer timeLimit;
    List<RaceSession> raceSessionList;
    boolean active;
    Integer eventId;
    Integer sessionType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public Integer getLapLimit() {
        return lapLimit;
    }

    public void setLapLimit(Integer lapLimit) {
        this.lapLimit = lapLimit;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void add()
    {
        Integer eventId = (Integer)Messages.getSessionAttribute("race-event-id");
        RaceSession raceSession = new RaceSession();
        raceSession.setName(getName());
        raceSession.setExecutionDate(getExecutionDate());
        raceSession.setLapLimit(getLapLimit());
        raceSession.setTimeLimit(getTimeLimit());
        raceSession.setActive(isActive());
        raceSession.setEventId(eventId);
        raceSession.setSessionType(getSessionType());
        CreateResult result = (CreateResult)CallUtils.postCall("session/add", raceSession, CreateResult.class, new HashMap());
        refreshSessionList();
        Messages.addMessage(result);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("sessions.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public List<RaceSession> getRaceSessionList() {
        if (getEventId()!=Messages.getSessionAttribute("race-event-id"))
        {
            refreshSessionList();
            setEventId((Integer)Messages.getSessionAttribute("race-event-id"));
        }
        return raceSessionList;
    }

    public void setRaceSessionList(List<RaceSession> raceSessionList) {
        this.raceSessionList = raceSessionList;
    }

    public void updateRow(RowEditEvent event)
    {
        RaceSession raceEvent = (RaceSession)event.getObject();
        CreateResult result = (CreateResult) CallUtils.postCall("session/add", raceEvent, CreateResult.class, new HashMap());
        refreshSessionList();
        Messages.addMessage(result);
    }

    public void refreshSessionList() {
        Integer eventId = (Integer)Messages.getSessionAttribute("race-event-id");
        RaceSessionListDto raceEventListDto = (RaceSessionListDto)CallUtils.getCall("session/get/"+eventId, RaceSessionListDto.class, new HashMap());
        raceSessionList = raceEventListDto.getRaceSessionList();
    }

    public void newSession()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("newSession.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void allEvents()
    {
        Messages.setSessionAttribute("race-event-id", null);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("events.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void showResults(Integer sessionId)
    {
        try {
            Messages.setSessionAttribute("race-session-id", sessionId);
            RaceSession rsResult = (RaceSession)CallUtils.getCall("session/getById/"+sessionId, RaceSession.class, new HashMap());
            Messages.setSessionAttribute("race-session", rsResult);
            FacesContext.getCurrentInstance().getExternalContext().redirect("results.xhtml");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void showMyResults(Integer sessionId)
    {
        try {
            Messages.setSessionAttribute("race-session-id", sessionId);
            RaceSession rsResult = (RaceSession)CallUtils.getCall("session/getById/"+sessionId, RaceSession.class, new HashMap());
            Messages.setSessionAttribute("race-session", rsResult);
            FacesContext.getCurrentInstance().getExternalContext().redirect("myResults.xhtml");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Integer getSessionType() {
        return sessionType;
    }

    public void setSessionType(Integer sessionType) {
        this.sessionType = sessionType;
    }
}
