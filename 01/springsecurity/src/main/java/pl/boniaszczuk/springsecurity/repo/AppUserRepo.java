package pl.boniaszczuk.springsecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.boniaszczuk.springsecurity.entity.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser findAllByUsername(String username);
}
