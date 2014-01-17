package lt.agmis.raceLive.service;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.dto.CreateResult;

public interface UserService {
    public CreateResult login(AppUser appUser);
    public CreateResult createUser(AppUser appUser);
    public AppUser info(Integer userId);
}
