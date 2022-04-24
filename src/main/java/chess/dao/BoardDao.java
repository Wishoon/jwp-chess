package chess.dao;

import static chess.dao.Connector.getConnection;

import chess.dto.SquareDto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    public List<SquareDto> findAll() {
        final String sql = "select position, symbol, color from board";
        final List<SquareDto> boardDtos = new ArrayList<>();

        try (final PreparedStatement statement = getConnection().prepareStatement(sql)) {
            loadBoardFromDB(boardDtos, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return boardDtos;
    }

    private void loadBoardFromDB(final List<SquareDto> boardDtos, final PreparedStatement statement)
            throws SQLException {
        try (final ResultSet resultSet = statement.executeQuery()) {
            loadPieceFromDB(boardDtos, resultSet);
        }
    }

    private void loadPieceFromDB(final List<SquareDto> boardDtos, final ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            boardDtos.add(new SquareDto(
                    resultSet.getString("position"),
                    resultSet.getString("symbol"),
                    resultSet.getString("color")));
        }
    }

    public void save(final List<SquareDto> boardDtos, final int gameId) {
        final String sql = "insert into board (position, symbol, color, game_id) values (?, ?, ?, ?)";

        try (final PreparedStatement statement = getConnection().prepareStatement(sql)) {
            convertPieceToBoard(boardDtos, gameId, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void convertPieceToBoard(final List<SquareDto> boardDtos, final int gameId,
                                     final PreparedStatement statement) throws SQLException {
        for (final SquareDto boardDto : boardDtos) {
            statement.setString(1, boardDto.getPosition());
            statement.setString(2, boardDto.getSymbol());
            statement.setString(3, boardDto.getColor());
            statement.setInt(4, gameId);
            statement.executeUpdate();
        }
    }

    public void update(final SquareDto boardDto) {
        final String sql = "update board set symbol = (?), color = (?) where position = (?)";

        try (final PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, boardDto.getSymbol());
            statement.setString(2, boardDto.getColor());
            statement.setString(3, boardDto.getPosition());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
