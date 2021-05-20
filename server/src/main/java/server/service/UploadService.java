package server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {
    public void upload(MultipartFile... file){
        File file1 = new File("/home/Shumakov/IMG");
        if (!file1.exists()) file1.mkdir();
        for (MultipartFile multipartFile : file) {
            try {
                multipartFile.transferTo(new File("/home/Shumakov/IMG/IMG_" + multipartFile.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadIcon(MultipartFile... file) {
        File file1 = new File("/home/Shumakov/ICON");
        if (!file1.exists()) file1.mkdir();
            try {
                file[0].transferTo(new File("/home/Shumakov/ICON/ICON_" + file[0].getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
