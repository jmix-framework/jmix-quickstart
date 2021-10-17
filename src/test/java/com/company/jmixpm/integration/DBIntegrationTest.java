package com.company.jmixpm.integration;

import com.company.jmixpm.JmixPmApplication;
import com.company.jmixpm.app.TaskService;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.integration.extension.PostgreSqlExtension;
import com.company.jmixpm.integration.initializer.TestContextInitializer;
import io.jmix.core.Metadata;
import io.jmix.core.UnconstrainedDataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({PostgreSqlExtension.class, SpringExtension.class})
@ContextConfiguration(classes = JmixPmApplication.class, initializers = TestContextInitializer.class)
public class DBIntegrationTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UnconstrainedDataManager dataManager;
    @Autowired
    private Metadata metadata;

    @Test
    @DisplayName("Checks the computation of the least busy user")
    void checkLeastBusyUser() {
        // create test data
        User user1 = metadata.create(User.class);
        user1.setUsername("user1");

        User user2 = metadata.create(User.class);
        user2.setUsername("user2");

        Project project = metadata.create(Project.class);
        project.setName("DB Integration test");
        project.setManager(user1);

        Task task1 = metadata.create(Task.class);
        task1.setName("Write integration test");
        task1.setAssignee(user1);
        task1.setProject(project);
        task1.setEstimatedEfforts(10);

        Task task2 = metadata.create(Task.class);
        task2.setName("Write documentation");
        task2.setAssignee(user2);
        task2.setProject(project);
        task2.setEstimatedEfforts(20);

        // save test data
        dataManager.save(user1, user2, project, task1, task2);

        // assertion
        Assertions.assertEquals(taskService.findLeastBusyUser(), user1);
    }
}
