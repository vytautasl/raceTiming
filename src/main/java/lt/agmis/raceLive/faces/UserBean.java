package lt.agmis.raceLive.faces;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.faces.utils.CallUtils;
import lt.agmis.raceLive.faces.utils.Messages;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@ManagedBean
@SessionScoped
public class UserBean {
    public String userName;
    public String userPass;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @PostConstruct
    public void init()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int replaceIndex = request.getRequestURL().indexOf(request.getRequestURI());
        Messages.setSessionAttribute("api-call-path", request.getRequestURL().replace(replaceIndex, replaceIndex + request.getRequestURL().length(), "").toString() + request.getContextPath() + "/api/");
    }

    public String login()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
        AppUser appUser = new AppUser();
        appUser.setUserName(getUserName());
        appUser.setUserPass(getUserPass());
        CreateResult result = (CreateResult)CallUtils.postCall("user/login", appUser, CreateResult.class, new HashMap());
        appUser.setId((Integer)result.getId());
        if (result.isSuccess())
        {
            session.setAttribute("appUser", appUser);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("events.xhtml");
            } catch (IOException e) {
                System.out.println("Faces context not found");
                return "loginError";
            }
        }
        Messages.addMessage(result);
        return "loginFailed";
    }

    public void info()
    {
        AppUser appUser = Messages.getAppUser();
        CreateResult result = new CreateResult();
        if (appUser!=null)
        {
            result.setSuccess(true);
            result.setDescription("Welcome " + appUser.getUserName());
        }
        Messages.addMessage(result);
    }

}
