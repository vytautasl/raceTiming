package lt.agmis.raceLive.domain;

import javax.persistence.*;

@Entity
@Table(name="session_user")
public class SessionUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=false, unique=false)
    private Integer sessionId;

    @Column(nullable=false, unique=false)
    private Integer deviceId;

    @Column(nullable=true, unique=false)
    private String sessionDisplayName;

    @Column(nullable=true, unique=false)
    private String sessionDisplayKart;

    @Column(nullable=true, unique=false)
    private Integer deviceUserId;

    private String deviceUserName;
    private String deviceDefaultKartNumber;
    private String deviceSerialNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getSessionDisplayName() {
        return sessionDisplayName;
    }

    public void setSessionDisplayName(String sessionDisplayName) {
        this.sessionDisplayName = sessionDisplayName;
    }

    public String getSessionDisplayKart() {
        return sessionDisplayKart;
    }

    public void setSessionDisplayKart(String sessionDisplayKart) {
        this.sessionDisplayKart = sessionDisplayKart;
    }

    public Integer getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(Integer deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName;
    }

    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public String getDeviceDefaultKartNumber() {
        return deviceDefaultKartNumber;
    }

    public void setDeviceDefaultKartNumber(String deviceDefaultKartNumber) {
        this.deviceDefaultKartNumber = deviceDefaultKartNumber;
    }
}
