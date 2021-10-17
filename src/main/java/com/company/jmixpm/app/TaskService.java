package com.company.jmixpm.app;

import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.entity.KeyValueEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class TaskService {

    private final DataManager dataManager;

    public TaskService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public User findLeastBusyUser() {
        ValueLoadContext valueLoadContext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery("select u, sum(t.estimatedEfforts) " +
                        "from User u left outer join Task_ t " +
                        "on u = t.assignee " +
                        "group by u"))
                .addProperty("user")
                .addProperty("estimatedEfforts");

        return dataManager.loadValues(valueLoadContext).stream()
                .sorted(Comparator.comparing((KeyValueEntity e) -> (Long) e.getValue("estimatedEfforts"),
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .map(e -> e.<User>getValue("user"))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}