package lt.agmis.raceLive.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ManagedBean
@SessionScoped
public class NavigationBean {

    public void home()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void events()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("events.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void profile()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void logout()
    {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
            session.invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void signup()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("newUser.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }


    public void myEvents()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("myEvents.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void browseEvents()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("browseEvents.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }
}
