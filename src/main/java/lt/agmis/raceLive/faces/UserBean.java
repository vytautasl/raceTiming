package lt.agmis.raceLive.faces;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.faces.utils.CallUtils;
import lt.agmis.raceLive.faces.utils.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@ManagedBean
@SessionScoped
public class UserBean {
    public String userName;
    public String userPass;
    public String publicName;
    public String copyright;

    public String newUserName;
    public String newPassword;
    public String newRepeatPassword;
    public String newEmail;
    public String newPublicId;
    public String newPublicName;
    public String newFBId;
    public boolean newHost;

    private MapModel emptyModel;
    private double newLat;
    private double newLng;
    private String newCountry;
    private String newCity;
    private String newAddress;


    public void onMarkerSelect(OverlaySelectEvent event) {
        Marker marker = (Marker) event.getOverlay();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
    }

    public UserBean()
    {
        emptyModel = new DefaultMapModel();
    }

    public void addMarker(ActionEvent actionEvent) {
        Marker marker = new Marker(new LatLng(newLat, newLng), newPublicName);
        emptyModel.addOverlay(marker);
    }

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

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewRepeatPassword() {
        return newRepeatPassword;
    }

    public void setNewRepeatPassword(String newRepeatPassword) {
        this.newRepeatPassword = newRepeatPassword;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewPublicId() {
        return newPublicId;
    }

    public void setNewPublicId(String newPublicId) {
        this.newPublicId = newPublicId;
    }

    public String getNewFBId() {
        return newFBId;
    }

    public void setNewFBId(String newFBId) {
        this.newFBId = newFBId;
    }

    public boolean isNewHost() {
        return newHost;
    }

    public void setNewHost(boolean newHost) {
        this.newHost = newHost;
    }

    public String getNewPublicName() {
        return newPublicName;
    }

    public void setNewPublicName(String newPublicName) {
        this.newPublicName = newPublicName;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
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
        AppUser result = (AppUser)CallUtils.postCall("user/login", appUser, AppUser.class, new HashMap());
        if (result!=null)
        {
            collectDataOfUser(result);
            session.setAttribute("appUser", result);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
            } catch (IOException e) {
                System.out.println("Faces context not found");
            }
        }
        return "loginFailed";
    }

    private void collectDataOfUser(AppUser result) {
        setUserName(result.getUserName());
        setPublicName(result.getName());
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

    public String getCopyright() {
        return "© 2014 Jaunimo sporto rėmimo asociacija. All rights reserved.";
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleLogoUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleSchemeUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void register()
    {
        if (getNewPassword().equals(getNewRepeatPassword()))
        {
            AppUser appUser = new AppUser();
            appUser.setUserName(getNewUserName());
            appUser.setUserPass(getNewPassword());
            appUser.setEmail(getNewEmail());
            appUser.setConfirmed(false);
            appUser.setFbId(getNewFBId());
            appUser.setHost(isNewHost());
            appUser.setPublicId(getNewPublicId());
            appUser.setName(getNewPublicName());
            CreateResult result = (CreateResult)CallUtils.postCall("user/create", appUser, CreateResult.class, new HashMap());
            if (result.isSuccess())
            {
                try {
                    //sendHTMLEmail(appUser.getEmail(), "Signup comfirmation", "<html><body><b>Hello</b></body></html>");
                    AppUser userLoggedIn = (AppUser)CallUtils.postCall("user/login", appUser, AppUser.class, new HashMap());
                    if (userLoggedIn!=null)
                    {
                        collectDataOfUser(userLoggedIn);
                        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
                        session.setAttribute("appUser", userLoggedIn);
                        try {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
                        } catch (IOException e) {
                            System.out.println("Faces context not found");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Faces context not found. " + e.getMessage());
                }
            }
        }
    }


    public void sendHTMLEmail(String to, String subject, String html) throws Exception {

        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        Session session = (Session) envCtx.lookup("mail/Session");
        MimeMessage message = new MimeMessage(session);
        Address toAddress = new InternetAddress(to);
        message.setRecipient(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);

        message.setContent(html, "text/html; charset=UTF-8");

        message.saveChanges();

        Transport tr = session.getTransport();
        tr.connect();
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    public double getNewLat() {
        return newLat;
    }

    public void setNewLat(double newLat) {
        this.newLat = newLat;
    }

    public double getNewLng() {
        return newLng;
    }

    public void setNewLng(double newLng) {
        this.newLng = newLng;
    }

    public String getNewCountry() {
        return newCountry;
    }

    public void setNewCountry(String newCountry) {
        this.newCountry = newCountry;
    }

    public String getNewCity() {
        return newCity;
    }

    public void setNewCity(String newCity) {
        this.newCity = newCity;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
}
