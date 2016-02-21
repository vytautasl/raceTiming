package lt.agmis.raceLive.restcontroller;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.dto.BestLapsDto;
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
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@RequestMapping(value = {"/results"})
@Controller
@ManagedBean
@SessionScoped
public class ResultsController implements Serializable {
    @Autowired
    private ResultsService resultsService;
    @Autowired
    private UserService userService;
    @Autowired
    private RaceService raceService;

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

    @RequestMapping(value = "/liveFeed/{userPublicId}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public BestLapsDto getLiveFeed(HttpServletResponse response, @PathVariable String userPublicId)
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try
        {
            response.getOutputStream().flush();
        } catch (Exception e)
        {
            System.out.println("flush failed");
        }
        AppUser appUser = userService.getUserByPublicId(userPublicId);
        RaceSession session = raceService.getActiveSession(appUser);
        BestLapsDto result;
        if (session.getSessionType()==RaceSession.TYPE_PRACTICE)
        {
            result = resultsService.getFinalBestLaps(session.getId(), 99999);
        } else {
            result = resultsService.getFinalRaceResults(session.getId(), 99999);
        }

        return result;
    }
}
