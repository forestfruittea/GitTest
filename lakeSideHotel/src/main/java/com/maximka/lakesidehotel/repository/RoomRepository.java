package com.maximka.lakesidehotel.repository;

import com.maximka.lakesidehotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
