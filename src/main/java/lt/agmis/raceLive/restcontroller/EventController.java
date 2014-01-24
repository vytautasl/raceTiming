package lt.agmis.raceLive.restcontroller;

import lt.agmis.raceLive.domain.*;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.RaceEventListDto;
import lt.agmis.raceLive.service.DeviceService;
import lt.agmis.raceLive.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@RequestMapping(value = {"/event"})
@Controller
@ManagedBean
@SessionScoped
public class EventController implements Serializable {
    @Autowired
    private RaceService raceService;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/add", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public CreateResult addEvent(@RequestBody RaceEvent raceEvent) {
        raceService.addRaceEvent(raceEvent);
        CreateResult result = new CreateResult();
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/get/{userId}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public RaceEventListDto getEvents(@PathVariable Integer userId) {
        return raceService.getEvents(userId);
    }

    @RequestMapping(value = "/getById/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public RaceEvent getEvent(@PathVariable Integer id) {
        return raceService.getEvent(id);
    }

    @RequestMapping(value = "/getMy/{userId}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public RaceEventListDto getMyEvents(@PathVariable Integer userId) {
        return raceService.getMyEvents(userId);
    }

}
