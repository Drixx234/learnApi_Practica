package IntegracionBackFront.backfront.Controller.Cloudinary;

import IntegracionBackFront.backfront.Services.Cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageController {

    @Autowired
    private final CloudinaryService cloudinaryService;

    public ImageController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file){
        try{
            String imageURL = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "Url", imageURL
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen" + e.getMessage());
        }
    }

    @PostMapping("/upload-to-folder")
    public ResponseEntity<?> uploadImagetoFolder(
            @RequestParam("image") MultipartFile file,
            @RequestParam String folder
    ){
        try {
            String imageURL = cloudinaryService.uploadImage(file, folder);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "Url", imageURL
            ));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen" + e.getMessage());
        }
    }
}
