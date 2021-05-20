
package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.ServerModel;
import server.repository.StatsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerModelService {
    @Autowired
    StatsRepository repository;

    public void putModels(ServerModel... models){
        if (models != null) {
            repository.putModelsInBD(models);
        }
    }

    public List<ServerModel> getModelByUserId(Long id, Integer type){
        if (id != null && type != null){
            return repository.getModelsByUserId(id, type);
        } else {
            return null;
        }
    }

    public List<ServerModel> getModel( Integer type) {
        if ( type != null){
            return repository.getModels( type);
        } else {
            return null;
        }
    }

    public List<ServerModel> getAllModelsByMainName(String mainName) {
        if (mainName != null){
            return repository.getAllModelsByMainName(mainName);
        } else {
            return null;
        }
    }

    public List<ServerModel> getStatsByID(Long id) {
        if (id != null){
            return repository.getStatsByID(id);
        } else {
            return new ArrayList<>();
        }
    }
}
