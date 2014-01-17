package lt.agmis.raceLive.domain;

import javax.persistence.*;

@Entity
@Table(name="device")
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=true, unique=false)
    private String defaultName;

    @Column(nullable=false, unique=false)
    private String defaultNumber;

    @Column(nullable=false, unique=false)
    private Integer deviceOwner;

    @Column(nullable=false, unique=true)
    private String serialNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDefaultNumber() {
        return defaultNumber;
    }

    public void setDefaultNumber(String defaultNumber) {
        this.defaultNumber = defaultNumber;
    }

    public Integer getDeviceOwner() {
        return deviceOwner;
    }

    public void setDeviceOwner(Integer deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
