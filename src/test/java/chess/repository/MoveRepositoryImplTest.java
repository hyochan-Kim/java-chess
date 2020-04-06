package chess.repository;

import chess.domain.coordinate.Coordinate;
import chess.dto.MoveDto;
import chess.dto.RoomDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class MoveRepositoryImplTest {
    private MoveRepository moveRepository;
    private RoomRepository roomRepository;

    @BeforeEach
    void setup() {
        moveRepository = new MoveRepositoryImpl();
        roomRepository = new RoomRepositoryImpl();
    }

    @AfterEach
    void tearDown() throws SQLException {
        moveRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    void add() throws SQLException {
        RoomDto roomDto = new RoomDto();
        roomRepository.create(roomDto);
        MoveDto moveDto = new MoveDto(-1, Coordinate.of("a1"), Coordinate.of("b2"));
        moveDto.setMoveId(1);
        moveRepository.add(moveDto);
        assertThat(moveRepository.findById(1).isSuccess()).isTrue();
    }
}