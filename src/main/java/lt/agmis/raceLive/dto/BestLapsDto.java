package lt.agmis.raceLive.dto;

import java.util.List;

public class BestLapsDto {
    List<BestLapRowDto> bestLapRowDtoList;

    public List<BestLapRowDto> getBestLapRowDtoList() {
        return bestLapRowDtoList;
    }

    public void setBestLapRowDtoList(List<BestLapRowDto> bestLapRowDtoList) {
        this.bestLapRowDtoList = bestLapRowDtoList;
    }
}
