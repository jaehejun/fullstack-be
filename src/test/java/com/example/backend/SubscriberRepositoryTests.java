package com.example.backend;

import com.example.backend.entity.Subscriber;
import com.example.backend.entity.SubscriberRole;
import com.example.backend.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class SubscriberRepositoryTests {

    @Autowired
    private SubscriberRepository subscriberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertSubscriber() {
        for (int i = 0; i < 10; i++) {
            Subscriber subscriber = Subscriber.builder()
                    .email("user" + i + "@aaa.com")
                    .pwd(passwordEncoder.encode("1111"))
                    .nickname("USER" + i)
                    .build();
            subscriber.addRole(SubscriberRole.USER);
            if (i >= 5)
                subscriber.addRole(SubscriberRole.MANAGER);
            if (i >= 8)
                subscriber.addRole(SubscriberRole.ADMIN);
        subscriberRepository.save(subscriber);
        }
    }

    @Test
    public void testRead() {
        String email = "user9@aaa.com";
        Subscriber member = subscriberRepository.getWithRoles(email);
        log.info("------------------------------------------");
        log.info(member);
    }
}
