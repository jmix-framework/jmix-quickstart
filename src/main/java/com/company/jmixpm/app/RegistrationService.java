package com.company.jmixpm.app;

import com.company.jmixpm.entity.User;
import com.company.jmixpm.security.CombinedManagerRole;
import com.company.jmixpm.security.RestrictedDocumentsRole;
import com.company.jmixpm.security.RestrictedProjectsRole;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.email.EmailException;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RegistrationService {

    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;
    @Autowired
    private Emailer emailer;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @return true if user with this email (or login) already exists.
     */
    public boolean checkUserAlreadyExist(String email) {
        List<User> users = unconstrainedDataManager.load(User.class)
                .query("select e from User e where e.username = :email or e.email = :email")
                .parameter("email", email)
                .list();
        return !users.isEmpty();
    }

    public User registerNewUser(String email, String firstName, String lastName) {
        User user = unconstrainedDataManager.create(User.class);
        user.setEmail(email);
        user.setUsername(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        user.setNeedsActivation(true);
        user.setActive(false);

        User savedUser = unconstrainedDataManager.save(user);
        return savedUser;
    }

    public String generateRandomActivationToken() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        ThreadLocalRandom current = ThreadLocalRandom.current();
        String generatedString = current.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public void saveActivationToken(User user, String activationToken) {
        User freshUser = unconstrainedDataManager.load(User.class)
                .id(user.getId())
                .one();
        freshUser.setActivationToken(activationToken);
        unconstrainedDataManager.save(freshUser);
    }

    public void sendActivationEmail(User user) throws EmailException {
        user = unconstrainedDataManager.load(User.class)
                .id(user.getId())
                .one();

        String activationLink = "http://localhost:8080/#activate?token=" + user.getActivationToken();
        String body = String.format("Hello, %s %s.\nYour Jmix PM activation link is: %s\nClick it to finish your registration.",
                user.getFirstName(),
                user.getLastName(),
                activationLink
        );

        EmailInfo email = EmailInfoBuilder.create()
                .setSubject("Jmix PM registration")
                .setBody(body)
                .setAddresses(user.getEmail())
                .build();

        emailer.sendEmail(email);
    }

    @Nullable
    public User loadUserByActivationToken(String token) {
        return unconstrainedDataManager.load(User.class)
                .query("select u from User u where u.activationToken = :token and u.needsActivation = true")
                .parameter("token", token)
                .optional()
                .orElse(null);
    }

    public void activateUser(User user, String password) {
        user = unconstrainedDataManager.load(User.class)
                .id(user.getId())
                .one();

        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setActivationToken(null);
        user.setNeedsActivation(false);

        List<Object> toSave = new ArrayList<>();
        toSave.add(user);
        toSave.add(createRoleAssignment(user, CombinedManagerRole.CODE, RoleAssignmentRoleType.RESOURCE));
        toSave.add(createRoleAssignment(user, RestrictedProjectsRole.CODE, RoleAssignmentRoleType.ROW_LEVEL));
        toSave.add(createRoleAssignment(user, RestrictedDocumentsRole.CODE, RoleAssignmentRoleType.ROW_LEVEL));

        unconstrainedDataManager.save(toSave.toArray());
    }

    private RoleAssignmentEntity createRoleAssignment(User user, String roleCode, String roleType) {
        RoleAssignmentEntity roleAssignmentEntity = unconstrainedDataManager.create(RoleAssignmentEntity.class);
        roleAssignmentEntity.setRoleCode(roleCode);
        roleAssignmentEntity.setUsername(user.getUsername());
        roleAssignmentEntity.setRoleType(roleType);
        return roleAssignmentEntity;
    }
}