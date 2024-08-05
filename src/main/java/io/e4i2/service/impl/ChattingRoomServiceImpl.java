package io.e4i2.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.e4i2.create.ChattingRoomCreate;
import io.e4i2.dto.ChattingRoomDTO;
import io.e4i2.entity.ChattingRoom;
import io.e4i2.entity.Device;
import io.e4i2.entity.QDevice;
import io.e4i2.repository.ChattingRoomRepository;
import io.e4i2.service.ChattingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingRoomServiceImpl implements ChattingRoomService {
    
    private final ChattingRoomRepository chattingRoomRepository;
    private final JPAQueryFactory queryFactory;
    
    @Override
    public ChattingRoomDTO.Response createChattingRoom(ChattingRoomCreate chattingRoomCreate) {
        
        QDevice device = QDevice.device;
        Device findDevice = queryFactory
                .select(device)
                .from(device)
                .where(device.deviceId.eq(chattingRoomCreate.getDeviceName()))
                .fetchOne();
        
        
        ChattingRoom chattingRoom = new ChattingRoom(findDevice);
        ChattingRoom savedChattingRoom = chattingRoomRepository.save(chattingRoom);
        
        ChattingRoomDTO chattingRoomDTO = new ChattingRoomDTO(savedChattingRoom.getChattingId(), savedChattingRoom.getCreatedAt());
        
        ChattingRoomDTO.Result result = new ChattingRoomDTO.Result(200, "SUCCESS", "success");
        
        return new ChattingRoomDTO.Response(result, chattingRoomDTO);
        
        
    }
}
