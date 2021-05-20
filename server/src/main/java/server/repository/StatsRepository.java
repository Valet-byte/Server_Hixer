package server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import server.mapper.ServerModelMapper;
import server.model.ServerModel;

import java.util.LinkedList;
import java.util.List;

@Repository
public class StatsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer putModelsInBD(ServerModel... models) {
        jdbcTemplate.execute("USE content");
        for (ServerModel model : models) {
            jdbcTemplate.update("INSERT INTO stats (authorID, photo, name, info, position, type, mainName) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)", model.getAuthorID(), model.getPhoto(), model.getName(), model.getInfo(), model.getPosition(),
                    model.getType(), models[0].getName());
        }

        return 1;
    }

    public List<ServerModel> getModelsByUserId(Long id, Integer type) {
        jdbcTemplate.execute("USE content");
        List<ServerModel> list;
        try {
            list = jdbcTemplate.query("SELECT * FROM stats WHERE authorID = ? AND type = ? AND position = ?",
                    new ServerModelMapper(), id, type, 0);
        }catch (Exception ignore){
            list = new LinkedList<>();
        }

        return list;
    }

    public List<ServerModel> getModels( Integer type) {
        jdbcTemplate.execute("USE content");
        List<ServerModel> list;
        try {
            list = jdbcTemplate.query("SELECT * FROM stats WHERE type = ?  AND position = ? ORDER BY rand() LIMIT 10",
                    new ServerModelMapper(),  type, 0);
        }catch (Exception ignore){
            list = new LinkedList<>();
        }

        return list;
    }

    public List<ServerModel> getAllModelsByMainName(String mainName) {
        jdbcTemplate.execute("USE content");
        return jdbcTemplate.query("SELECT * FROM stats WHERE mainName = ?",
                new ServerModelMapper(),  mainName);
    }

    public List<ServerModel> getStatsByID(Long id) {
        jdbcTemplate.execute("USE content");
        return jdbcTemplate.query("SELECT * FROM stats WHERE id = ? AND position = ?",
                new ServerModelMapper(),  id, 0);
    }
}
