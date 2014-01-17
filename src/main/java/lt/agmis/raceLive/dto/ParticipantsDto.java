package lt.agmis.raceLive.dto;

import lt.agmis.raceLive.domain.SessionUser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 1/17/14
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParticipantsDto {
    List<SessionUser> participants;

    public List<SessionUser> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SessionUser> participants) {
        this.participants = participants;
    }
}
