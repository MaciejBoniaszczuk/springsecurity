package pl.boniaszczuk.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.boniaszczuk.springsecurity.entity.AppUser;
import pl.boniaszczuk.springsecurity.repo.AppUserRepo;


@Component
public class Start {

    private PasswordEncoder passwordEncoder;

    private AppUserRepo appUserRepo;

    @Autowired
    public Start(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;

        AppUser appUser = new AppUser();
        appUser.setUsername("Maciek");
        appUser.setPassword(passwordEncoder.encode("123"));
        appUserRepo.save(appUser);
    }
}
