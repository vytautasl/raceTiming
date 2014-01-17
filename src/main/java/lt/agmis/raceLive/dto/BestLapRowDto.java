package lt.agmis.raceLive.dto;

public class BestLapRowDto {
    Integer position;
    String kartName;
    String kartNumber;
    Double bestLap;
    String bestLapStr;
    String gap;
    String gapTotal;
    Integer bestLapInLap;
    Integer totalLaps;
    String raceGap;
    String raceGapTotal;
    Double lastLap;
    String racePos;
    Integer deviceId;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getKartName() {
        return kartName;
    }

    public void setKartName(String kartName) {
        this.kartName = kartName;
    }

    public String getKartNumber() {
        return kartNumber;
    }

    public void setKartNumber(String kartNumber) {
        this.kartNumber = kartNumber;
    }

    public Double getBestLap() {
        return bestLap;
    }

    public void setBestLap(Double bestLap) {
        this.bestLap = bestLap;
    }

    public Integer getBestLapInLap() {
        return bestLapInLap;
    }

    public void setBestLapInLap(Integer bestLapInLap) {
        this.bestLapInLap = bestLapInLap;
    }

    public Integer getTotalLaps() {
        return totalLaps;
    }

    public void setTotalLaps(Integer totalLaps) {
        this.totalLaps = totalLaps;
    }

    public String getBestLapStr() {
        return bestLapStr;
    }

    public void setBestLapStr(String bestLapStr) {
        this.bestLapStr = bestLapStr;
    }

    public Double getLastLap() {
        return lastLap;
    }

    public void setLastLap(Double lastLap) {
        this.lastLap = lastLap;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public String getGapTotal() {
        return gapTotal;
    }

    public void setGapTotal(String gapTotal) {
        this.gapTotal = gapTotal;
    }

    public String getRaceGap() {
        return raceGap;
    }

    public void setRaceGap(String raceGap) {
        this.raceGap = raceGap;
    }

    public String getRaceGapTotal() {
        return raceGapTotal;
    }

    public void setRaceGapTotal(String raceGapTotal) {
        this.raceGapTotal = raceGapTotal;
    }

    public String getRacePos() {
        return racePos;
    }

    public void setRacePos(String racePos) {
        this.racePos = racePos;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
}
