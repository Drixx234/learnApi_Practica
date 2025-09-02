package IntegracionBackFront.backfront.Repositories.UserType;

import IntegracionBackFront.backfront.Entities.Users.UserTypeEntity;
import IntegracionBackFront.backfront.Repositories.Users.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
}
