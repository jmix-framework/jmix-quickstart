package com.company.jmixpm.app;

import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.core.security.SystemAuthenticator;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RegistrationCleaner {
    private static final Logger log = LoggerFactory.getLogger(RegistrationCleaner.class);

    @Autowired
    private DataManager dataManager;
    @Autowired
    private TimeSource timeSource;

    public String deleteOldNotActivatedUsers() {
        Date threshold = DateUtils.addDays(timeSource.currentTimestamp(), -7);
        List<User> oldUsers = dataManager.load(User.class)
                .query("select u from User u where u.createdDate < :threshold and u.needsActivation = true")
                .parameter("threshold", threshold)
                .list();
        for (User u: oldUsers) {
            dataManager.remove(u);
            log.info("Removing old not activated user " + u.getUsername());
        }
        return "Deleted " + oldUsers.size() + " users";
    }
}