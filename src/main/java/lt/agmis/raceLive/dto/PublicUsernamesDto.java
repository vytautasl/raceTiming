package lt.agmis.raceLive.dto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/22/14
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class PublicUsernamesDto {
    List<String> userNames;

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }
}
