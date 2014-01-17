package lt.agmis.raceLive.restcontroller;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

@RequestMapping(value = {"/user"})
@Controller
@ManagedBean
@SessionScoped
public class UserController implements Serializable {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public CreateResult createUser(@RequestBody AppUser appUser) {
        return userService.createUser(appUser);
    }

    @RequestMapping(value = "/login", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public CreateResult login(@RequestBody AppUser appUser, HttpServletRequest request, HttpServletResponse response) {
        CreateResult result = userService.login(appUser);
        if(result.isSuccess())
        {
            request.getSession().setAttribute("appUserId", result.getId());
        }
        return result;
    }

    @RequestMapping(value = "/info", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public AppUser info(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = (Integer)request.getSession().getAttribute("appUserId");
        AppUser appUser = null;
        if (userId!=null)
        {
            appUser = userService.info(userId);
        } else {
            appUser = new AppUser();
            appUser.setId(-1);
        }
        return appUser;
    }

}
