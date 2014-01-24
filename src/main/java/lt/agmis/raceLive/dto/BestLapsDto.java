package lt.agmis.raceLive.dto;

import java.util.List;

public class BestLapsDto {
    List<BestLapRowDto> bestLapRowDtoList;
    String eventName;
    String sessionName;
    String updateTime;

    public List<BestLapRowDto> getBestLapRowDtoList() {
        return bestLapRowDtoList;
    }

    public void setBestLapRowDtoList(List<BestLapRowDto> bestLapRowDtoList) {
        this.bestLapRowDtoList = bestLapRowDtoList;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
