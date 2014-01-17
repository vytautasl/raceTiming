package lt.agmis.raceLive.domain;

import javax.persistence.*;

@Entity
@Table(name="raw_data")
public class Raw {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=false, unique=false)
    private Double time;

    @Column(nullable=false, unique=false)
    private Integer deviceId;

    @Column(nullable=false, unique=false)
    private String raw;

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

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
