package chess.dao;

import chess.domain.game.ChessGameEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringChessGameDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpringChessGameDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<ChessGameEntity> findByStateIsBlackTurnOrWhiteTurn() {
        String query = "SELECT * FROM chess_game WHERE state in(BlackTurn, WhiteTurn)";
        ChessGameEntity chessGameEntity = jdbcTemplate.queryForObject(query, ChessGameEntity.class);
        return Optional.of(chessGameEntity);
    }

}
