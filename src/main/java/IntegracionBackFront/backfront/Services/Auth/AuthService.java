package IntegracionBackFront.backfront.Services.Auth;

import IntegracionBackFront.backfront.Config.Crypto.Argon2Password;
import IntegracionBackFront.backfront.Entities.Users.UserEntity;
import IntegracionBackFront.backfront.Repositories.Users.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    public Boolean Login(String correo, String contrasena){
       //Crear objeto de tipo argon
        Argon2Password objHash = new Argon2Password();

        Optional<UserEntity> list  = repo.findByCorreo(correo).stream().findFirst();
        if (list.isPresent()){
            UserEntity usuario = list.get();
            String nombreTipoUsuario = usuario.getTipoUsuario().getNombreTipo();
            System.out.println("Usuario encontrado ID: " + usuario.getId() +
                                ", email: " + usuario.getCorreo() +
                                "rol: " + nombreTipoUsuario);
            String HashBD = usuario.getContrasena();
            return objHash.VerifyPassword(HashBD, contrasena);
        }
        return false;
    }
}
