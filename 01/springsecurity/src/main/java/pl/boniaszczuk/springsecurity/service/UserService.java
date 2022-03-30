package pl.boniaszczuk.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.boniaszczuk.springsecurity.entity.AppUser;
import pl.boniaszczuk.springsecurity.entity.VerificationToken;
import pl.boniaszczuk.springsecurity.repo.AppUserRepo;
import pl.boniaszczuk.springsecurity.repo.VerificationTokenRepo;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepo verificationTokenRepo;
    private MailSenderService mailSenderService;

    @Autowired
    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder,
                       VerificationTokenRepo verificationTokenRepo, MailSenderService mailSenderService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailSenderService = mailSenderService;
    }

    public void addNewUser(AppUser user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepo.save(user);

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationTokenRepo.save(verificationToken);

        String url = "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify-token?token="+token;

//        try {
//            mailSenderService.sendMail(user.getUsername(), "Verification Token", url, false);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        System.out.println(url);
    }

    public void verifyToken(String token) {
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
    }


}

