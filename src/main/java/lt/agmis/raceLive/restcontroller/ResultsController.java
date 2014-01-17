package lt.agmis.raceLive.restcontroller;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.dto.BestLapsDto;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.service.DeviceService;
import lt.agmis.raceLive.service.RaceService;
import lt.agmis.raceLive.service.ResultsService;
import lt.agmis.raceLive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@RequestMapping(value = {"/results"})
@Controller
@ManagedBean
@SessionScoped
public class ResultsController implements Serializable {
    @Autowired
    private ResultsService resultsService;

    @RequestMapping(value = "/getFinalBestLaps/{sessionId}/{selectedLap}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public BestLapsDto getFinalBestlaps(@PathVariable Integer sessionId, @PathVariable Integer selectedLap) {
        return resultsService.getFinalBestLaps(sessionId, selectedLap);
    }

    @RequestMapping(value = "/getFinalRaceResults/{sessionId}/{selectedLap}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public BestLapsDto getFinalRaceResults(@PathVariable Integer sessionId, @PathVariable Integer selectedLap) {
        return resultsService.getFinalRaceResults(sessionId, selectedLap);
    }
}
