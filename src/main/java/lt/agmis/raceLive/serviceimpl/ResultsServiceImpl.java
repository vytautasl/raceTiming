package lt.agmis.raceLive.serviceimpl;

import lt.agmis.raceLive.dao.*;
import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.domain.RaceSession;
import lt.agmis.raceLive.domain.SessionUser;
import lt.agmis.raceLive.dto.BestLapRowDto;
import lt.agmis.raceLive.dto.BestLapsDto;
import lt.agmis.raceLive.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional
public class ResultsServiceImpl implements ResultsService {
    @Autowired
    private CheckpointDao checkpointDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private RaceSessionDao raceSessionDao;
    @Autowired
    private SessionUserDao sessionUserDao;

    private Double updateTimeLimit(List<Checkpoint> checkpointList, Double timeLimit, Integer lapLimit, List<Device> devices)
    {
        Double newTimeLimit = timeLimit;
        Integer lap=0;
        Device device = null;
        for (Checkpoint checkpoint:checkpointList)
        {
            Device currentLapDevice = getDeviceById(checkpoint.getDeviceId(), devices);
            if (device!=currentLapDevice)
            {
                device=currentLapDevice;
                lap = 0;
            } else {
                lap++;
                if (lap>lapLimit)
                {
                    if (newTimeLimit>checkpoint.getTime())
                    {
                        newTimeLimit = checkpoint.getTime();
                    }
                }
            }
        }
        return newTimeLimit;
    }

    @Override
    public BestLapsDto getFinalBestLaps(Integer sessionId, Integer selectedLap) {
        List<Checkpoint> checkpointList = checkpointDao.getCheckpoints(sessionId);
        List<SessionUser> sessionUsers = sessionUserDao.getUsersOfSession(sessionId);
        LinkedHashMap lastLaps = new LinkedHashMap();
        LinkedHashMap bestLaps = new LinkedHashMap();
        LinkedHashMap lapNumber = new LinkedHashMap();
        LinkedHashMap totalLaps = new LinkedHashMap();

        RaceSession raceSession = raceSessionDao.getSession(sessionId);
        List<Device> devices = deviceDao.getDevices(raceSession);
        Device device = null;
        Integer lap=0;
        Double timeLimit=9999999999.0;
        if (raceSession.getTimeLimit()>0)
        {
            timeLimit = new Double(raceSession.getTimeLimit()*60);
        }
        Integer lapLimit = selectedLap;
        if (raceSession.getLapLimit()<lapLimit)
        {
            lapLimit = raceSession.getLapLimit();
        }

        timeLimit = updateTimeLimit(checkpointList, timeLimit, lapLimit, devices);

        for (Checkpoint checkpoint:checkpointList)
        {
            Device currentLapDevice = getDeviceById(checkpoint.getDeviceId(), devices);
            if (device!=currentLapDevice)
            {
                if (device!=null)
                {
                    totalLaps.put(device.getId(), lap);
                }
                device=currentLapDevice;
                lap = 0;
            }
            Double lastLap = (Double)lastLaps.get(checkpoint.getDeviceId());
            if (lastLap==null)
            {
                lastLap = 0.0;
            }

            if ((lastLap>0)&&(lastLap<timeLimit))
            {
                lap++;
                Double bestLap = (Double)bestLaps.get(checkpoint.getDeviceId());
                if (bestLap==null)
                {
                    bestLaps.put(checkpoint.getDeviceId(), checkpoint.getTime()-lastLap);
                    lapNumber.put(checkpoint.getDeviceId(), lap);
                } else {
                    if (bestLap>(checkpoint.getTime()-lastLap))
                    {
                        bestLaps.put(checkpoint.getDeviceId(), checkpoint.getTime()-lastLap);
                        lapNumber.put(checkpoint.getDeviceId(), lap);
                    }
                }
            }
            lastLaps.put(checkpoint.getDeviceId(), checkpoint.getTime());
        }
        if (device!=null)
        {
            totalLaps.put(device.getId(), lap);
        }


        BestLapsDto bestLapsDto = new BestLapsDto();
        List<BestLapRowDto> bestLapRows = new ArrayList<BestLapRowDto>();
        for (int i=0; i<bestLaps.size(); i++)
        {
            BestLapRowDto bestLapRow = new BestLapRowDto();
            bestLapRow.setBestLap((Double)bestLaps.values().toArray()[i]);
            device = getDeviceById((Integer)(bestLaps.keySet().toArray()[i]), devices);
            if (device!=null)
            {
                bestLapRow.setKartName(device.getDefaultName());
                bestLapRow.setKartNumber(device.getDefaultNumber());
                bestLapRow.setDeviceId(device.getId());
            }
            bestLapRow.setBestLapInLap((Integer)lapNumber.get(device.getId()));
            bestLapRow.setTotalLaps((Integer)totalLaps.get(device.getId()));
            bestLapRow.setLastLap((Double)lastLaps.values().toArray()[i]);
            bestLapRows.add(bestLapRow);
        }

        for (int i=0; i<bestLapRows.size(); i++)
        {
            for (int j=0; j<bestLapRows.size(); j++)
            {
                BestLapRowDto iRow = bestLapRows.get(i);
                BestLapRowDto jRow = bestLapRows.get(j);
                if (iRow.getBestLap().compareTo(jRow.getBestLap())<0)
                {
                    bestLapRows.set(i, jRow);
                    bestLapRows.set(j, iRow);
                }
            }
        }

        Double lastBestLap=0.0;
        for (int i=0; i<bestLapRows.size(); i++)
        {
            BestLapRowDto iRow = bestLapRows.get(i);
            BestLapRowDto bestLap = bestLapRows.get(0);
            if (lastBestLap==0.0)
            {
                lastBestLap=iRow.getBestLap();
                iRow.setGap("+"+String.format("%06.3f", 0.0));
                iRow.setGapTotal("+"+String.format("%06.3f", 0.0));
            } else {
                iRow.setGap("+"+String.format("%06.3f", iRow.getBestLap()-lastBestLap));
                iRow.setGapTotal("+"+String.format("%06.3f", iRow.getBestLap()-bestLap.getBestLap()));
                lastBestLap=iRow.getBestLap();
            }
            iRow.setPosition(i+1);
            Double mins = Math.floor(iRow.getBestLap()/60);
            iRow.setBestLapStr(String.format("%d:%06.3f", mins.intValue(),(iRow.getBestLap()-mins*60)));
            SessionUser sessionUser = findSessionUserByDeviceId(iRow.getDeviceId(), sessionUsers);
            if (sessionUser!=null)
            {
                iRow.setKartName(sessionUser.getSessionDisplayName());
                iRow.setKartNumber(sessionUser.getSessionDisplayKart());
            }
            bestLapRows.set(i, iRow);
        }

        bestLapsDto.setBestLapRowDtoList(bestLapRows);

        return bestLapsDto;
    }

    @Override
    public BestLapsDto getFinalRaceResults(Integer sessionId, Integer selectedLap) {
        List<Checkpoint> checkpointList = checkpointDao.getCheckpoints(sessionId);
        List<SessionUser> sessionUsers = sessionUserDao.getUsersOfSession(sessionId);
        BestLapsDto raceResults = getFinalBestLaps(sessionId, selectedLap);
        List<BestLapRowDto> bestLapRows = raceResults.getBestLapRowDtoList();
        for (int i=0; i<bestLapRows.size(); i++)
        {
            for (int j=0; j<bestLapRows.size(); j++)
            {
                BestLapRowDto iRow = bestLapRows.get(i);
                BestLapRowDto jRow = bestLapRows.get(j);
                if (iRow.getTotalLaps().compareTo(jRow.getTotalLaps())>0)
                {
                    bestLapRows.set(i, jRow);
                    bestLapRows.set(j, iRow);
                }
                if ((iRow.getTotalLaps().compareTo(jRow.getTotalLaps())==0)&&(iRow.getLastLap().compareTo(jRow.getLastLap())<0))
                {
                    bestLapRows.set(i, jRow);
                    bestLapRows.set(j, iRow);
                }
            }
        }
        if (bestLapRows.size()>0)
        {
            Double lastTime = lastCheckpointOfDevice(checkpointList, bestLapRows.get(0).getDeviceId());
            Integer lastLap = bestLapRows.get(0).getTotalLaps();
            Double totalGap = 0.0;

            for (int i=0; i<bestLapRows.size(); i++)
            {
                BestLapRowDto iRow = bestLapRows.get(i);
                iRow.setRacePos(String.valueOf(i+1));
                Double raceGap = iRow.getLastLap()*(lastLap-iRow.getTotalLaps())+lastCheckpointOfDevice(checkpointList, iRow.getDeviceId())-lastTime;
                iRow.setRaceGap("+"+String.format("%06.3f", raceGap));
                totalGap += raceGap;
                iRow.setRaceGapTotal("+"+String.format("%06.3f", totalGap));
                lastTime = lastCheckpointOfDevice(checkpointList, bestLapRows.get(i).getDeviceId());
                lastLap = iRow.getTotalLaps();
                SessionUser sessionUser = findSessionUserByDeviceId(iRow.getDeviceId(), sessionUsers);
                if (sessionUser!=null)
                {
                    iRow.setKartName(sessionUser.getSessionDisplayName());
                    iRow.setKartNumber(sessionUser.getSessionDisplayKart());
                }
                bestLapRows.set(i, iRow);
            }
        }
        raceResults.setBestLapRowDtoList(bestLapRows);
        return raceResults;
    }

    private SessionUser findSessionUserByDeviceId(Integer deviceId, List<SessionUser> sessionUsers)
    {
        for (SessionUser sessionUser:sessionUsers)
        {
            if (sessionUser.getDeviceId()==deviceId)
            {
                return sessionUser;
            }
        }
        return null;
    }

    private Double lastCheckpointOfDevice(List<Checkpoint> checkpointList, Integer deviceId)
    {
        for (int i=checkpointList.size()-1;i>=0; i--)
        {
            Checkpoint checkpoint=checkpointList.get(i);
            if (checkpoint.getDeviceId().equals(deviceId))
            {
                return checkpoint.getTime();
            }
        }
        return null;
    }

    private Device getDeviceByKartnumber(String kartNumber, List<Device> devices)
    {
        for (Device device:devices)
        {
            if (device.getDefaultNumber().equals(kartNumber))
            {
                return device;
            }
        }
        return null;
    }


    private Device getDeviceById(Integer deviceId, List<Device> devices)
    {
        for (Device device:devices)
        {
            if (device.getId()==deviceId)
            {
                return device;
            }
        }
        return null;
    }
}
