package lt.agmis.raceLive.domain;

import lt.agmis.raceLive.faces.utils.Messages;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="race_event")
public class RaceEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=true, unique=false)
    private String raceName;

    @Column(nullable=true, unique=false)
    private Date beginDate;

    @Column(nullable=true, unique=false)
    private Date endDate;

    @Column(nullable=true, unique=false)
    private Integer host;

    @Column(nullable=true, unique=false)
    private Double lng;

    @Column(nullable=true, unique=false)
    private Double lat;

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getHost() {
        return host;
    }

    public void setHost(Integer host) {
        this.host = host;
    }

    public Integer getRaceOwner() {
        return raceOwner;
    }

    public void setRaceOwner(Integer raceOwner) {
        this.raceOwner = raceOwner;
    }

    @Column(nullable=true, unique=false)
    private Integer raceOwner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
