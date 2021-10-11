package com.company.jmixpm.unit;

import com.company.jmixpm.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    @DisplayName("Checks the instance name of the user")
    void testInstanceName() {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("ivanov_i");

        // Instance name = firstName lastName [username]
        Assertions.assertEquals(user.getDisplayName(), "Ivan Ivanov [ivanov_i]");
    }
}
