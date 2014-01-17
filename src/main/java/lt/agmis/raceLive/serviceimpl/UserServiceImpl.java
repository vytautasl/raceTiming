package lt.agmis.raceLive.serviceimpl;

import lt.agmis.raceLive.dao.AppUserDao;
import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private AppUserDao appUserDao;

    @Override
    public CreateResult createUser(AppUser appUser) {
        Integer resultId = appUserDao.saveOrUpdateUser(appUser);
        CreateResult result = new CreateResult();
        if (resultId!=null)
        {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public AppUser info(Integer userId) {
        return appUserDao.getInfo(userId);
    }

    @Override
    public CreateResult login(AppUser appUser) {
        AppUser result = appUserDao.login(appUser);
        CreateResult response = new CreateResult();
        if (result!=null)
        {
            response.setSuccess(true);
            response.setDescription("Welcome " + appUser.getUserName());
            response.setId(result.getId());
        } else {
            response.setSuccess(false);
            response.setDescription("User not found");
        }

        return response;
    }

}
