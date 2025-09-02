package IntegracionBackFront.backfront.Controller.Auth;

import IntegracionBackFront.backfront.Models.DTO.Users.UsersDTO;
import IntegracionBackFront.backfront.Services.Auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    private ResponseEntity<String> login(@Valid @RequestBody UsersDTO data, HttpServletResponse response){
        if (data .getCorreo() == null || data.getCorreo().isBlank() || data.getCorreo().isEmpty() || data.getContrasena().isBlank() || data.getContrasena().isEmpty()){
            return ResponseEntity.status(401).body("Error: credenciales incompletas");
        }
        if (service.Login(data.getCorreo(), data.getContrasena())){
            return ResponseEntity.ok("inicio de sesion exitoso");
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }

}
