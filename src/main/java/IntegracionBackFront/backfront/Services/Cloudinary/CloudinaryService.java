package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    //Constante para que el archivo enviado no pase de 5 mb
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //Constante para definir el tipo de archivo
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    //Cliente de Cloudinary inyectado con dependencias
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public String uploadImage(MultipartFile file) throws IOException{
        //1. Validar imagen
        validateImage(file);

        //Sube el archivo con configuraciones basicas
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));

        //Retornar la URL de la imagen
        return (String) uploadResult.get("secure_url");
    }


    public String uploadImage(MultipartFile file, String folder) throws IOException{
        //1. Validar imagen
        validateImage(file);

        //Generar un nombre unico para cada imagen
        //Conservar la extension original
        //Agregar un UUID para evitar coliciones

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = "img..." + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,
                "public_id", uniqueFileName,
                "use_filename", false,
                "unique_filename", false,
                "overwrite", false,
                "resource_type", "auto",
                "quality", "auto.good"
        );

        //Subir archivo
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file){
        //1. Verificar si el archivo esta vacio
        if (file.isEmpty()){
            throw new IllegalArgumentException("El archivo no puede estar vacio");
        }

        //2. Verificar tamaño maximo
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El archivo no puede ser mayor a 5MB");
        }

        //3. Obtener y validar el nombre original del archivo
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null){
            throw new IllegalArgumentException("Nombre de archivo invalido");
        }

        //4. Extraer y Validar la extension
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException("Solo le permiten archivos JPG, JPEG, y PNG");
        }

        //Verifica que el tipo de MIME sea imagen
        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe ser una imagen valida");

        }
    }
}
