package lt.agmis.raceLive.service;

import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.dto.BestLapsDto;

import java.util.List;

public interface ResultsService {
    BestLapsDto getFinalBestLaps(Integer sessionId, Integer selectedLap);
    BestLapsDto getFinalRaceResults(Integer sessionId, Integer selectedLap);
}
