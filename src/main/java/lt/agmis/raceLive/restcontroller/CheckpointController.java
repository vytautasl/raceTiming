package lt.agmis.raceLive.restcontroller;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Checkpoint;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.RawDataDto;
import lt.agmis.raceLive.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@RequestMapping(value = {"/cp"})
@Controller
@ManagedBean
@SessionScoped
public class CheckpointController implements Serializable {
    @Autowired
    private RaceService raceService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    RawParseService rawParseService;

    @Autowired
    CheckpointService checkpointService;

    @RequestMapping(value = "/add/{serialNumber}/{time}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public CreateResult addCheckpoint(@PathVariable String serialNumber, @PathVariable Double time) {
        Device device = deviceService.getDevice(serialNumber);
        if (device==null)
        {
            device = new Device();
            device.setSerialNumber(serialNumber);
            device.setDefaultNumber("unrecKart#" + serialNumber);
            device.setDefaultName("unrecKartName" + serialNumber);
            device.setDeviceOwner(-1);
            deviceService.saveDevice(device);
        }
        raceService.addCheckpoint(device, time);
        CreateResult result = new CreateResult();
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/postRaw/deviceId/{serialNumber}/time/{time}", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public CreateResult postRaw(@PathVariable String serialNumber, @PathVariable Double time, @RequestBody RawDataDto raw) {
        Device device = deviceService.getDevice(serialNumber);
        if (device==null)
        {
            device = new Device();
            device.setSerialNumber(serialNumber);
            device.setDefaultNumber("unrecKart#" + serialNumber);
            device.setDefaultName("unrecKartName" + serialNumber);
            device.setDeviceOwner(-1);
            deviceService.saveDevice(device);
        }
        checkpointService.createCheckpoint(device, raw.getRaw(), "device serial here");
        rawParseService.saveRaw(device, time, raw.getRaw());
        CreateResult result = new CreateResult();
        result.setSuccess(true);
        return result;
    }

}
