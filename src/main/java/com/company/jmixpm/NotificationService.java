package com.company.jmixpm;

import com.company.jmixpm.entity.Notification;
import io.jmix.core.Id;
import io.jmix.core.UnconstrainedDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Component
public class NotificationService {

    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;

    @PersistenceContext
    private EntityManager entityManager;

    public void markAsReadWithUnconstrainedDataManager(UUID notificationId) {
        Notification notification = unconstrainedDataManager.load(Id.of(notificationId, Notification.class))
                .one();
        notification.setIsRead(true);
        unconstrainedDataManager.save(notification);
    }

    @Transactional
    public void markAsReadWithEntityManager(UUID notificationId) {
        Notification notification = entityManager.find(Notification.class, notificationId);
        notification.setIsRead(true);
    }
}