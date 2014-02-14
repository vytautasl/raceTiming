package lt.agmis.raceLive.faces;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceEvent;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.RaceEventListDto;
import lt.agmis.raceLive.faces.utils.CallUtils;
import lt.agmis.raceLive.faces.utils.Messages;
import org.primefaces.component.fileupload.FileUploadRenderer;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SlideEndEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@SessionScoped
public class EventBean {
    Date beginDate;
    Date endDate;
    String name;
    List<RaceEvent> eventList;
    List<RaceEvent> browsingList;
    List<RaceEvent> participatedEventList;
    AppUser owner;
    AppUser participant;
    double userCenterLat;
    double userCenterLng;
    Integer dayLimit=7;
    Integer eventHost;
    Double eventLat;
    Double eventLng;
    private MapModel emptyModel;
    Double browsingLat=0.0;
    Double browsingLng=0.0;
    Integer mapLevel=8;
    private Marker marker;

    public List<RaceEvent> getBrowsingList() {
        return browsingList;
    }

    public void setBrowsingList(List<RaceEvent> browsingList) {
        this.browsingList = browsingList;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Facebook
    com.restfb.types.User me;
    public void postFB()
    {
        String response = (String)CallUtils.fbCall("https://graph.facebook.com//oauth/access_token?client_id=127978363901965&client_secret=9d9770ffa6c0e0b683526caf91bd1ac2&grant_type=client_credentials");
        String accessToken = response.split("=")[1];
        response = (String)CallUtils.fbCall("https://graph.facebook.com/me");
        System.out.println(response);
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        Map markerData = (Map)marker.getData();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, (String)markerData.get("host"), (String)markerData.get("begin")));
    }

    public void onStateChange(StateChangeEvent event) {
        setBrowsingLat(event.getCenter().getLat());
        setBrowsingLng(event.getCenter().getLng());
        setMapLevel(event.getZoomLevel());
    }

    public Integer getMapLevel() {
        return mapLevel;
    }

    public void setMapLevel(Integer mapLevel) {
        this.mapLevel = mapLevel;
    }

    public Double getBrowsingLat() {
        if ((browsingLat==null)||(browsingLat==0.0))
        {
            return getUserCenterLat();
        }
        return browsingLat;
    }

    public void setBrowsingLat(Double browsingLat) {
        this.browsingLat = browsingLat;
    }

    public Double getBrowsingLng() {
        if ((browsingLng==null)||(browsingLng==0.0))
        {
            return getUserCenterLng();
        }
        return browsingLng;
    }

    public void setBrowsingLng(Double browsingLng) {
        this.browsingLng = browsingLng;
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    public EventBean()
    {
        emptyModel = new DefaultMapModel();
    }

    public Integer getEventHost() {
        return eventHost;
    }

    public void setEventHost(Integer eventHost) {
        this.eventHost = eventHost;
    }

    public Double getEventLat() {
        return eventLat;
    }

    public void setEventLat(Double eventLat) {
        this.eventLat = eventLat;
    }

    public Double getEventLng() {
        return eventLng;
    }

    public void setEventLng(Double eventLng) {
        this.eventLng = eventLng;
    }

    public void onSlideEnd(SlideEndEvent event)
    {
        if (event!=null)
        {
            setDayLimit(event.getValue());
        }
        RaceEventListDto eventList = (RaceEventListDto) CallUtils.getCall("event/getInDays/" + getDayLimit(), RaceEventListDto.class, new HashMap());
        emptyModel.getMarkers().clear();
        for (RaceEvent raceEvent:eventList.getRaceEventList())
        {
            Marker marker = new Marker(new LatLng(raceEvent.getLat(), raceEvent.getLng()));
            //marker.setTitle(raceEvent.getRaceName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //marker.setShadow(sdf.format(raceEvent.getBeginDate()) + "" + sdf.format(raceEvent.getEndDate()));
            Map markerData = new LinkedHashMap();
            markerData.put("host", raceEvent.getRaceName());
            markerData.put("begin", sdf.format(raceEvent.getBeginDate()));
            markerData.put("end", sdf.format(raceEvent.getEndDate()));
            marker.setData(markerData);
            emptyModel.addOverlay(marker);
        }
        setBrowsingList(eventList.getRaceEventList());
    }

    public Integer getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Integer dayLimit) {
        this.dayLimit = dayLimit;
    }

    public void refreshMap()
    {
        onSlideEnd(null);
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
        if (getEventHost()!=null)
        {

        } else {
            setEventLat(appUser.getHostLat());
            setEventLng(appUser.getHostLng());
            setEventHost(appUser.getId());
        }
        raceEvent.setHost(getEventHost());
        raceEvent.setLat(getEventLat());
        raceEvent.setLng(getEventLng());

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
}
