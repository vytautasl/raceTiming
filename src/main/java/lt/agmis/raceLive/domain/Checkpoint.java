package lt.agmis.raceLive.domain;

import javax.persistence.*;

@Entity
@Table(name="checkpoint")
public class Checkpoint {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=false, unique=false)
    private Integer deviceId;

    @Column(nullable=false, unique=false)
    private Integer sessionId;

    @Column(nullable=false, unique=false)
    private Double time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
}
