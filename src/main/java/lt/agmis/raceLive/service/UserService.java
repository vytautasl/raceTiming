package lt.agmis.raceLive.service;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.dto.CreateResult;
import lt.agmis.raceLive.dto.PublicUsernamesDto;

public interface UserService {
    public AppUser login(AppUser appUser);
    public CreateResult createUser(AppUser appUser);
    public AppUser info(Integer userId);

    AppUser getUserByPublicId(String userPublicId);

    PublicUsernamesDto getPublicIdsLike(String criteria);
}
