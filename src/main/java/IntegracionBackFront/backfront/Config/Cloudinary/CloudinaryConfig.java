package IntegracionBackFront.backfront.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private String cloudName;
    private String apiKey;
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){
        //Crear un objetto .env para ver el .env
        Dotenv dotenv = Dotenv.load();

        //Crear un mapeo para guardar las claves del Cloudinary
        Map<String, String> config = new HashMap<>();
        config.put("cloudName", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        config.put("apiKey", dotenv.get("CLOUDINARY_API_KEY"));
        config.put("apiSecret", dotenv.get("CLOUDINARY_API_KEY"));

        //Retornar con las credenciales ya listas
        return new Cloudinary(config);
    }
}
