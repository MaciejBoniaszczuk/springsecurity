package pl.boniaszczuk.springsecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.boniaszczuk.springsecurity.entity.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByValue(String value);
}
