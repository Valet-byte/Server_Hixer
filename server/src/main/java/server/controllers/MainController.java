package server.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.model.Person;
import server.model.ServerModel;
import server.service.ServerModelService;
import server.service.UploadService;
import server.service.UsersUtilService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UsersUtilService usersUtilService;
    @Autowired
    private ServerModelService serverModelService;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    @RequestMapping("/findUser")
    @ResponseBody
    public Person findUser(@RequestParam("name") String name, @RequestParam("pass") String pass){
        return usersUtilService.findUser(name, pass);
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public Person addUser(@RequestParam("name") String name, @RequestParam("pass") String pass){

        return usersUtilService.addUser(name, pass);
    }

    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public Integer upload(@RequestParam("description") String s,
                          @RequestParam(value = "models") String[] models,
                          @RequestParam MultipartFile... file){
        System.out.println(s);
        try {
            ServerModel[] models1 = new ServerModel[models.length];
            for (int i = 0; i < models.length; i++) {
                models1[i] = gson.fromJson(models[i], ServerModel.class);
            }
            serverModelService.putModels(models1);

        } catch (Exception e){

        }
        uploadService.upload(file);
        return 1;
    }

    @RequestMapping(value = "/getModelByUserID")
    @ResponseBody
    public List<ServerModel> getModelByUserID(@RequestParam("ID") Long id,
                                              @RequestParam("type") Integer type){
        System.out.println(id);
        System.out.println(type);
        return serverModelService.getModelByUserId(id, type);
    }
    @RequestMapping(value = "/getModel")
    @ResponseBody
    public List<ServerModel> getModel(@RequestParam("type") Integer type){
        return serverModelService.getModel( type);
    }


    @RequestMapping(value = "/getPhoto")
    @ResponseBody
    public Resource getPhoto(@RequestParam("name") String name){
        Path path = Path.of("C:\\Users\\User\\Desktop\\" + name);
        UrlResource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return resource;
    }

    @RequestMapping(value = "getAllModelsByMainName")
    @ResponseBody
    public List<ServerModel> getAllModelsByMainName(@RequestParam("mainName") String mainName){
        return serverModelService.getAllModelsByMainName(mainName);
    }
    
}
