package lt.agmis.raceLive.faces;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.faces.utils.CallUtils;
import lt.agmis.raceLive.faces.utils.Messages;
import org.primefaces.component.fileupload.FileUploadRenderer;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.xml.sax.XMLReader;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
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
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
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
    private Double newLat=0.0;
    private Double newLng=0.0;
    private String newCountry;
    private String newCity;
    private String newAddress;

    private Double userCenterLat=0.0;
    private Double userCenterLng=0.0;
    String fbAppId="";

    public void test()
    {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
//            spf.setNamespaceAware(true);
//            SAXParser saxParser = spf.newSAXParser();
//            XMLReader xmlReader = saxParser.getXMLReader();
//            File outputFile1 = new File("C:\\temp\\output1.xml");
//            xmlReader.setContentHandler(new SaxMindaugas(outputFile1));
//            //xmlReader.parse(OrtecSaxParserUtil.convertToFileURL(routeFileName));
//            xmlReader.parse("C:\\temp\\input.xml");
        } catch (Exception e)
        {

        }
    }

    public String getFbAppId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
        AppUser appUser = (AppUser)session.getAttribute("appUser");
        return appUser.getFbId();
    }

    public void setFbAppId(String fbAppId) {
        this.fbAppId = fbAppId;
    }

    public Double getUserCenterLat() {
        return userCenterLat;
    }

    public void setUserCenterLat(Double userCenterLat) {
        this.userCenterLat = userCenterLat;
    }

    public Double getUserCenterLng() {
        return userCenterLng;
    }

    public void setUserCenterLng(Double userCenterLng) {
        this.userCenterLng = userCenterLng;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        Marker marker = (Marker) event.getOverlay();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
    }

    public UserBean()
    {
        emptyModel = new DefaultMapModel();
    }

    public void addMarker() {
        if ((newLat!=null)&&(newLng!=null))
        {
            Marker marker = new Marker(new LatLng(newLat, newLng), newPublicName);
            emptyModel.addOverlay(marker);
        }
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        emptyModel.getMarkers().clear();
        Marker marker = new Marker(new LatLng(latlng.getLat(), latlng.getLng()));
        emptyModel.addOverlay(marker);
        setNewLat(latlng.getLat());
        setNewLng(latlng.getLng());
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

    public void loadFields()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
        AppUser appUser = (AppUser)session.getAttribute("appUser");
        setNewUserName(appUser.getUserName());
        setNewEmail(appUser.getEmail());
        setNewFBId(appUser.getFbId());
        setNewHost(appUser.isHost());
        setNewPublicId(appUser.getPublicId());
        setNewPublicName(appUser.getName());
        setNewLat(appUser.getHostLat());
        setNewLng(appUser.getHostLng());
    }

    public void refreshMap()
    {
        setNewLat(getUserCenterLat());
        setNewLng(getUserCenterLng());
        emptyModel.getMarkers().clear();
        Marker marker = new Marker(new LatLng(getNewLat(), getNewLng()));
        emptyModel.addOverlay(marker);
    }

    public void loadProfile()
    {
        loadFields();
        addMarker();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
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

    public void backToLogin()
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            System.out.println("Faces context not found");
        }
    }

    public void updateProfile()
    {
        if (getNewPassword().equals(getNewRepeatPassword()))
        {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
            AppUser appUser = (AppUser)session.getAttribute("appUser");
            appUser.setUserName(getNewUserName());
            appUser.setUserPass(getNewPassword());
            appUser.setEmail(getNewEmail());
            appUser.setConfirmed(false);
            appUser.setFbId(getNewFBId());
            appUser.setHost(isNewHost());
            appUser.setPublicId(getNewPublicId());
            appUser.setName(getNewPublicName());
            appUser.setHostLat(getNewLat());
            appUser.setHostLng(getNewLng());
            CreateResult result = (CreateResult)CallUtils.postCall("user/create", appUser, CreateResult.class, new HashMap());
            if (result.isSuccess())
            {
                try {
                    //sendHTMLEmail(appUser.getEmail(), "Signup comfirmation", "<html><body><b>Hello</b></body></html>");
                    AppUser userLoggedIn = (AppUser)CallUtils.postCall("user/login", appUser, AppUser.class, new HashMap());
                    if (userLoggedIn!=null)
                    {
                        collectDataOfUser(userLoggedIn);
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

    public Double getNewLat() {
        if ((newLat==null)||(newLat==0.0)) newLat=52.5;
        return newLat;
    }

    public void setNewLat(Double newLat) {
        this.newLat = newLat;
    }

    public Double getNewLng() {
        if ((newLng==null)||(newLng==0.0)) newLng=13.5;
        return newLng;
    }

    public void setNewLng(Double newLng) {
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
