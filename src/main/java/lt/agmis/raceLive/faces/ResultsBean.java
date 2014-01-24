package lt.agmis.raceLive.faces;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.dto.*;
import lt.agmis.raceLive.faces.utils.CallUtils;
import lt.agmis.raceLive.faces.utils.Messages;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@SessionScoped
public class ResultsBean {
    List<BestLapRowDto> bestLapsDto;
    List<BestLapRowDto> raceResultsDto;
    List<String> lapList = new ArrayList<String>();
    String selectedLap="999";
    String refreshRate="10";
    RaceSession raceSession;
    RaceEvent raceEvent;
    String sessionStatusMsg;
    String sessionStatus;
    String duration;
    Integer sessionId;
    ParticipantsDto participantsDto;
    boolean owner;

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isOwner()
    {
        AppUser appUser = Messages.getAppUser();
        if ((getRaceEvent()==null)||(getRaceEvent().getId()!=Messages.getSessionAttribute("race-event-id")))
        {
            setRaceEvent((RaceEvent) CallUtils.getCall("event/getById/" + Messages.getSessionAttribute("race-event-id"), RaceEvent.class, new HashMap()));
        }
        return appUser.getId().equals(getRaceEvent().getRaceOwner());
    }

    public void loadParticipants()
    {
        setParticipantsDto((ParticipantsDto) CallUtils.getCall("session/getParticipants/" + sessionId, ParticipantsDto.class, new HashMap()));
    }

    public void saveParticipants()
    {
        Integer sessionId = (Integer)Messages.getSessionAttribute("race-session-id");
        CallUtils.postCall("session/setParticipants/"+sessionId, getParticipantsDto(), CreateResult.class, new HashMap());
        refreshBestLaps();
    }

    public List<BestLapRowDto> getBestLapsDto() {
        Integer sessionId = (Integer)Messages.getSessionAttribute("race-session-id");
        if (getSessionId()!=sessionId)
        {
            setSessionId(sessionId);
            refreshBestLaps();
        }
        return bestLapsDto;
    }

    private void generateLapList(Integer lastLap)
    {
        lapList = new ArrayList<String>();
        lapList.add(String.valueOf("999"));
        for (int i=lastLap; i>0; i--)
        {
            lapList.add(String.valueOf(i));
        }
    }

    private String formatDuration(long duration)
    {
        String strDuration="";
        Long minutes = Math.round(Math.floor(duration/1000/60));
        Long seconds = Math.round(Math.floor(duration/1000%60));
        strDuration = minutes + ":" + seconds;
        return strDuration;
    }

    public void refreshBestLaps() {
        setRaceSession((RaceSession)Messages.getSessionAttribute("race-session"));
        setSessionId(getRaceSession().getId());

        loadParticipants();
        String lapLimit = getSelectedLap();
        if ((lapLimit==null)||(getLapList()==null)||(String.valueOf(getLapList().size()).equals(getSelectedLap())))
        {
            lapLimit = "9999";
        }

        if (getRaceSession()!=null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (getRaceSession().getExecutionDate().compareTo(new Date())>0)
            {
                setSessionStatusMsg("Session has not started yet. It will start at " + sdf.format(getRaceSession().getExecutionDate()));
            }
            else if ((getRaceSession().getEndDate()==null)||(getRaceSession().getEndDate().compareTo(new Date())>0))
            {
                Date now = new Date();
                setDuration(formatDuration(now.getTime()-getRaceSession().getExecutionDate().getTime()));
                setSessionStatusMsg("Session is running "+getDuration()+". Started at: " + sdf.format(getRaceSession().getExecutionDate()));
            } else {
                setDuration(formatDuration(getRaceSession().getEndDate().getTime()-getRaceSession().getExecutionDate().getTime()));
                setSessionStatusMsg("Session has ended at: " + sdf.format(getRaceSession().getEndDate()) + ". Lasted " + getDuration());
            }

            BestLapsDto result = (BestLapsDto)CallUtils.getCall("results/getFinalBestLaps/"+getRaceSession().getId()+"/"+lapLimit, BestLapsDto.class, new HashMap());
            bestLapsDto = result.getBestLapRowDtoList();

            result = (BestLapsDto)CallUtils.getCall("results/getFinalRaceResults/"+getRaceSession().getId()+"/"+lapLimit, BestLapsDto.class, new HashMap());
            raceResultsDto = result.getBestLapRowDtoList();

            if (raceResultsDto.size()>0)
            {
                BestLapRowDto bestLapRow = raceResultsDto.get(0);
                if (getLapList()!=null)
                {
                    if (getLapList().size()!=bestLapRow.getTotalLaps())
                    {
                        generateLapList(bestLapRow.getTotalLaps());
                    }
                }
            }
        }

    }

    public void setBestLapsDto(List<BestLapRowDto> bestLapsDto) {
        this.bestLapsDto = bestLapsDto;
    }

    public List<BestLapRowDto> getRaceResultsDto() {
        Integer sessionId = (Integer)Messages.getSessionAttribute("race-session-id");
        if (getSessionId()!=sessionId)
        {
            setSessionId(sessionId);
            refreshBestLaps();
        }
        return raceResultsDto;
    }

    public void setRaceResultsDto(List<BestLapRowDto> raceResultsDto) {
        this.raceResultsDto = raceResultsDto;
    }

    public List<String> getLapList() {
        Integer sessionId = (Integer)Messages.getSessionAttribute("race-session-id");
        if (getSessionId()!=sessionId)
        {
            setSessionId(sessionId);
            refreshBestLaps();
        }
        return lapList;
    }

    public void setLapList(List<String> lapList) {
        this.lapList = lapList;
    }

    public String getSelectedLap() {
        return selectedLap;
    }

    public void setSelectedLap(String selectedLap) {
        this.selectedLap = selectedLap;
    }

    public void changeSelectedLap(String resultRow)
    {
        setSelectedLap(resultRow);
        refreshBestLaps();
    }

    public String getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(String refreshRate) {
        this.refreshRate = refreshRate;
    }

    public RaceSession getRaceSession() {
        return (RaceSession)Messages.getSessionAttribute("race-session");
    }

    public void setRaceSession(RaceSession raceSession) {
        this.raceSession = raceSession;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void restartSession()
    {
        raceSession.setExecutionDate(new Date());
        raceSession.setActive(true);
        CreateResult result = (CreateResult)CallUtils.postCall("session/add/", raceSession, CreateResult.class, new HashMap());
        refreshBestLaps();
    }

    public void stopSession()
    {
        raceSession.setEndDate(new Date());
        CreateResult result = (CreateResult)CallUtils.postCall("session/add/", raceSession, CreateResult.class, new HashMap());
        refreshBestLaps();
    }

    public void navigateToMySessions()
    {
        try {
            Messages.setSessionAttribute("race-session-id", null);
            participantsDto = new ParticipantsDto();
            FacesContext.getCurrentInstance().getExternalContext().redirect("mySessions.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToSessions()
    {
        try {
            Messages.setSessionAttribute("race-session-id", null);
            participantsDto = new ParticipantsDto();
            FacesContext.getCurrentInstance().getExternalContext().redirect("sessions.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionStatusMsg() {
        return sessionStatusMsg;
    }

    public void setSessionStatusMsg(String sessionStatusMsg) {
        this.sessionStatusMsg = sessionStatusMsg;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public ParticipantsDto getParticipantsDto() {
        return participantsDto;
    }

    public void setParticipantsDto(ParticipantsDto participantsDto) {
        this.participantsDto = participantsDto;
    }

    public List<String> complete(String query) {
        List<String> results = new ArrayList<String>();

        PublicUsernamesDto userNames = (PublicUsernamesDto)CallUtils.getCall("user/publicIDs/"+query, PublicUsernamesDto.class, new HashMap());
        if (userNames!=null)
        {
            results = userNames.getUserNames();
        }
        return results;
    }

    public RaceEvent getRaceEvent() {
        return raceEvent;
    }

    public void setRaceEvent(RaceEvent raceEvent) {
        this.raceEvent = raceEvent;
    }
}
