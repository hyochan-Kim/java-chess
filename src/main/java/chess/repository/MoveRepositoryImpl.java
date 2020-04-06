package chess.repository;

import chess.domain.coordinate.Coordinate;
import chess.dto.MoveDto;
import chess.result.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static chess.utils.dbConnector.getConnection;

public class MoveRepositoryImpl implements MoveRepository {

    @Override
    public Result add(MoveDto moveDto) throws SQLException {
        String query = "INSERT INTO move VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setInt(1, moveDto.getMoveId());
        pstmt.setInt(2, moveDto.getRoomId());
        pstmt.setString(3, moveDto.getSource().toString());
        pstmt.setString(4, moveDto.getTarget().toString());
        pstmt.executeUpdate();
        return new Result(true, null);
    }

    @Override
    public Result findById(int moveId) throws SQLException {
        String query = "SELECT * FROM move WHERE id = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setInt(1, moveId);
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) {
            return new Result(false, null);
        }

        return new Result(true,
                new MoveDto(rs.getInt("id"),
                        Coordinate.of(rs.getString("source")),
                        Coordinate.of(rs.getString("target"))));
    }

    @Override
    public Result findByRoomId(int roomId) throws SQLException {
        String query = "SELECT * FROM move WHERE room_id = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setInt(1, roomId);
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) {
            return new Result(false, null);
        }
        List<MoveDto> moveDtos = new ArrayList<>();
        while (rs.next()) {
            moveDtos.add(new MoveDto(rs.getInt("id"),
                    Coordinate.of(rs.getString("source")),
                    Coordinate.of(rs.getString("target"))));
        }
        return new Result(true, moveDtos);
    }


    @Override
    public Result deleteById(int moveId) throws SQLException {
        String query = "DELETE FROM move WHERE id = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setInt(1, moveId);
        pstmt.executeUpdate();
        return new Result(true, null);
    }

    @Override
    public Result deleteByRoomId(int roomId) throws SQLException {
        String query = "DELETE FROM move WHERE room_id = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setInt(1, roomId);
        pstmt.executeUpdate();
        return new Result(true, null);
    }

    @Override
    public Result deleteAll() throws SQLException {
        String query = "DELETE FROM move";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.executeUpdate();
        return new Result(true, null);
    }
}
