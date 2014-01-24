package lt.agmis.raceLive.domain;

import javax.persistence.*;

@Entity
@Table(name="app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=false, unique=true)
    private String userName;

    @Column(nullable=false, unique=false)
    private String userPass;

    @Column(nullable=true, unique=true)
    private String email;

    @Column(nullable=true, unique=false)
    private String name;

    @Column(nullable=true, unique=true)
    private String publicId;

    @Column(nullable=true, unique=false)
    private boolean host;

    @Column(nullable=true, unique=false)
    private String fbId;

    @Column(nullable=true, unique=true)
    private String profilePhotoLocation;

    @Column(nullable=true, unique=true)
    private String logoPhotoLocation;

    @Column(nullable=true, unique=true)
    private String trackSchemePhotoLocation;

    @Column(nullable=true, unique=false)
    private boolean confirmed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getProfilePhotoLocation() {
        return profilePhotoLocation;
    }

    public void setProfilePhotoLocation(String profilePhotoLocation) {
        this.profilePhotoLocation = profilePhotoLocation;
    }

    public String getLogoPhotoLocation() {
        return logoPhotoLocation;
    }

    public void setLogoPhotoLocation(String logoPhotoLocation) {
        this.logoPhotoLocation = logoPhotoLocation;
    }

    public String getTrackSchemePhotoLocation() {
        return trackSchemePhotoLocation;
    }

    public void setTrackSchemePhotoLocation(String trackSchemePhotoLocation) {
        this.trackSchemePhotoLocation = trackSchemePhotoLocation;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
