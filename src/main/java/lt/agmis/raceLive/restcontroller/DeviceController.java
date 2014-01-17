package lt.agmis.raceLive.restcontroller;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.service.DeviceService;
import lt.agmis.raceLive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

@RequestMapping(value = {"/devices"})
@Controller
@ManagedBean
@SessionScoped
public class DeviceController implements Serializable {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/my", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Device> getMyDevices() {
        return deviceService.getDevices(new AppUser());
    }

    @RequestMapping(value = "/add/{serialNumber}/{defaultName}/{defaultNumber}", produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public CreateResult addDevice(HttpServletRequest request, HttpServletResponse response, @PathVariable String serialNumber, @PathVariable String defaultName, @PathVariable String defaultNumber) {
        Device device = new Device();
        device.setDefaultName(defaultName);
        device.setDefaultNumber(defaultNumber);
        device.setSerialNumber(serialNumber);
        device.setDeviceOwner(-1);
        Integer saved = deviceService.saveDevice(device);
        CreateResult result = new CreateResult();
        if (saved!=null)
        {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

}
