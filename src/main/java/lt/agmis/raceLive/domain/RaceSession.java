package lt.agmis.raceLive.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="race_session")
public class RaceSession {
    public static final Integer TYPE_PRACTICE = 1;
    public static final Integer TYPE_RACE = 2;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=true, unique=false)
    private String name;

    @Column(nullable=true, unique=false)
    private Date executionDate;

    @Column(nullable=true, unique=false)
    private Date endDate;

    @Column(nullable=true, unique=false)
    private Integer lapLimit;

    @Column(nullable=true, unique=false)
    private Integer timeLimit;

    @Column(nullable=true, unique=false)
    private Integer eventId;

    @Column(nullable=true, unique=false)
    private boolean active;

    @Column(nullable=true, unique=false)
    private Integer sessionType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public Integer getLapLimit() {
        return lapLimit;
    }

    public void setLapLimit(Integer lapLimit) {
        this.lapLimit = lapLimit;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getSessionType() {
        return sessionType;
    }

    public void setSessionType(Integer sessionType) {
        this.sessionType = sessionType;
    }
}
