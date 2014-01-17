package lt.agmis.raceLive.restcontroller;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.ParticipantsDto;
import lt.agmis.raceLive.dto.RaceSessionListDto;
import lt.agmis.raceLive.service.DeviceService;
import lt.agmis.raceLive.service.RaceService;
import lt.agmis.raceLive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@RequestMapping(value = {"/session"})
@Controller
@ManagedBean
@SessionScoped
public class SessionController implements Serializable {
    @Autowired
    private RaceService raceService;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/add", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public CreateResult addSession(@RequestBody RaceSession raceSession) {
        raceService.addRaceSession(raceSession);
        CreateResult result = new CreateResult();
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/get/{raceEventId}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public RaceSessionListDto getSessions(@PathVariable Integer raceEventId) {
        return raceService.getSessions(raceEventId);
    }

    @RequestMapping(value = "/getById/{sessionId}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public RaceSession getSession(@PathVariable Integer sessionId) {
        return raceService.getSession(sessionId);
    }

    @RequestMapping(value = "/getParticipants/{sessionId}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public ParticipantsDto getParticipants(@PathVariable Integer sessionId) {
        return raceService.getParticipants(sessionId);
    }

    @RequestMapping(value = "/setParticipants/{sessionId}", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public CreateResult setParticipants(@PathVariable Integer sessionId, @RequestBody ParticipantsDto participantsDto) {
        raceService.setParticipants(sessionId, participantsDto);
        CreateResult createResult = new CreateResult();
        createResult.setSuccess(true);
        return createResult;
    }
}
