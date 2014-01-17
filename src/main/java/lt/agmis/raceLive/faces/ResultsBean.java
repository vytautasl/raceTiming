package lt.agmis.raceLive.faces;

import lt.agmis.raceLive.domain.AppUser;
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
    String sessionStatusMsg;
    String sessionStatus;
    String duration;
    Integer sessionId;
    ParticipantsDto participantsDto;

    public void loadParticipants()
    {
        Integer sessionId = (Integer)Messages.getSessionAttribute("race-session-id");
        setParticipantsDto((ParticipantsDto) CallUtils.getCall("session/getParticipants/" + sessionId, ParticipantsDto.class, new HashMap()));
    }

    public void saveParticipants()
    {
        Integer sessionId = (Integer)Messages.getSessionAttribute("race-session-id");
        CreateResult result = (CreateResult)CallUtils.postCall("session/setParticipants/"+sessionId, getParticipantsDto(), CreateResult.class, new HashMap());
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
        Integer sessionId = (Integer)Messages.getSessionAttribute("race-session-id");
        loadParticipants();
        String lapLimit = getSelectedLap();
        if ((lapLimit==null)||(getLapList()==null)||(String.valueOf(getLapList().size()).equals(getSelectedLap())))
        {
            lapLimit = "9999";
        }

        RaceSession rsResult = (RaceSession)CallUtils.getCall("session/getById/"+sessionId, RaceSession.class, new HashMap());
        setRaceSession(rsResult);

        if (rsResult!=null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (rsResult.getExecutionDate().compareTo(new Date())>0)
            {
                setSessionStatusMsg("Session has not started yet. It will start at " + sdf.format(rsResult.getExecutionDate()));
            }
            else if ((rsResult.getEndDate()==null)||(rsResult.getEndDate().compareTo(new Date())>0))
            {
                Date now = new Date();
                setDuration(formatDuration(now.getTime()-rsResult.getExecutionDate().getTime()));
                setSessionStatusMsg("Session is running "+getDuration()+". Started at: " + sdf.format(rsResult.getExecutionDate()));
            } else {
                setDuration(formatDuration(rsResult.getEndDate().getTime()-rsResult.getExecutionDate().getTime()));
                setSessionStatusMsg("Session has ended at: " + sdf.format(rsResult.getEndDate()) + ". Lasted " + getDuration());
            }
        }


        BestLapsDto result = (BestLapsDto)CallUtils.getCall("results/getFinalBestLaps/"+sessionId+"/"+lapLimit, BestLapsDto.class, new HashMap());
        bestLapsDto = result.getBestLapRowDtoList();

        result = (BestLapsDto)CallUtils.getCall("results/getFinalRaceResults/"+sessionId+"/"+lapLimit, BestLapsDto.class, new HashMap());
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

    public void setBestLapsDto(List<BestLapRowDto> bestLapsDto) {
        this.bestLapsDto = bestLapsDto;
    }

    public List<BestLapRowDto> getRaceResultsDto() {
        return raceResultsDto;
    }

    public void setRaceResultsDto(List<BestLapRowDto> raceResultsDto) {
        this.raceResultsDto = raceResultsDto;
    }

    public List<String> getLapList() {
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
        return raceSession;
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
}
