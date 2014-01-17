package lt.agmis.raceLive.faces.utils;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.dto.CreateResult;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/3/14
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Messages {

    public static void addMessage(CreateResult result)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
        if (!result.isSuccess())
        {
            severity = FacesMessage.SEVERITY_ERROR;
        }
        if (result.getDetails()==null)
        {
            result.setDetails("");
        }
        context.addMessage(null, new FacesMessage(severity, result.getDescription(), result.getDetails()));
    }

    public static void setSessionAttribute(String attributeName, Object attributeValue)
    {
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
        session.setAttribute(attributeName, attributeValue);
    }

    public static Object getSessionAttribute(String attributeName)
    {
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
        return session.getAttribute(attributeName);
    }

    public static String getView()
    {
        String viewId = (String)getSessionAttribute("view-id");
        if (viewId==null)
        {
            viewId="login";
        }
        return viewId;
    }


    public static AppUser getAppUser()
    {
        AppUser appUser = (AppUser)getSessionAttribute("appUser");
        return appUser;
    }

}
