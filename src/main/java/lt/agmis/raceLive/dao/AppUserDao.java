package lt.agmis.raceLive.dao;

import lt.agmis.raceLive.domain.AppUser;
import lt.agmis.raceLive.domain.Device;
import lt.agmis.raceLive.dto.PublicUsernamesDto;

public interface AppUserDao {
    public AppUser login(AppUser appUser);
    public Integer saveOrUpdateUser(AppUser appUser);
    public AppUser getOwner(Device device);
    AppUser getInfo(Integer userId);
    AppUser getUserByPublicId(String publicId);
    PublicUsernamesDto getPublicUserNamesLike(String criteria);
}
