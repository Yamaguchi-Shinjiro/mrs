package mrs.domain.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mrs.domain.model.MeetingRoom;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {
}