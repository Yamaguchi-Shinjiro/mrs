package mrs.domain.service.room;

import java.util.List;

import mrs.domain.model.MeetingRoom;
import mrs.domain.repository.MeetingRoomRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RoomService {
	private final MeetingRoomRepository meetingRoomRepository;
	
	public MeetingRoom findMeetingRoom(Integer roomId) {
		return meetingRoomRepository.findById(roomId).orElse(null);
	}

	public List<MeetingRoom> findList() {
		return meetingRoomRepository.findAll(Sort.by(Direction.ASC, "roomId"));
	}
}