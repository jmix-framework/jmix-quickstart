package com.company.jmixpm.unit;

import com.company.jmixpm.JmixPmApplication;
import com.company.jmixpm.app.TaskService;
import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.entity.KeyValueEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = JmixPmApplication.class)
public class TaskUnitTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private Metadata metadata;

    @MockBean
    private DataManager dataManager;

    @Test
    @DisplayName("Checks the computation of the least busy user")
    void checkLeastBusyUser() {
        // test data
        User user1 = metadata.create(User.class);
        user1.setUsername("user1");
        User user2 = metadata.create(User.class);
        user2.setUsername("user2");

        KeyValueEntity entity1 = metadata.create(KeyValueEntity.class);
        entity1.setValue("user", user1);
        entity1.setValue("estimatedEfforts", 10L);
        KeyValueEntity entity2 = metadata.create(KeyValueEntity.class);
        entity2.setValue("user", user2);
        entity2.setValue("estimatedEfforts", 2L);
        List<KeyValueEntity> entities = Arrays.asList(entity1, entity2);

        // mock dataManager method
        Mockito.when(dataManager.loadValues(any(ValueLoadContext.class))).thenReturn(entities);

        // Assertion
        Assertions.assertEquals(taskService.findLeastBusyUser(), user2);
    }
}
