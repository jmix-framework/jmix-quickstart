package com.company.jmixpm.api;

import com.company.jmixpm.app.TaskService;
import io.jmix.graphql.service.UserInfo;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@GraphQLApi
@Service
public class TaskGraphQLService {

    private final TaskService taskService;

    public TaskGraphQLService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GraphQLQuery(name = "leastBusyUser")
    @Transactional
    public UserInfo findLeastBusyUser() {
        return new UserInfo(taskService.findLeastBusyUser());
    }
}
