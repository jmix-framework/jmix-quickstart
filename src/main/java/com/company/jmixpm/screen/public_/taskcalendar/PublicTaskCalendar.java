package com.company.jmixpm.screen.public_.taskcalendar;

import com.company.jmixpm.entity.Task;
import io.jmix.core.TimeSource;
import io.jmix.ui.component.Calendar;
import io.jmix.ui.event.UIRefreshEvent;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UiController("PublicTaskCalendar")
@UiDescriptor("public-task-calendar.xml")
public class PublicTaskCalendar extends Screen {

    @Autowired
    private Calendar<LocalDateTime> calendar;
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private CollectionLoader<Task> tasksDl;

    @Subscribe
    public void onInit(InitEvent event) {
        resetStartEndDates();
    }

    private void resetStartEndDates() {
        LocalDate today = timeSource.now().toLocalDate();
        calendar.setStartDate(today.withDayOfMonth(1).atStartOfDay());
        calendar.setEndDate(today.withDayOfMonth(today.lengthOfMonth()).atStartOfDay());
    }

    @EventListener
    public void onPageRefresh(UIRefreshEvent event) {
        tasksDl.load();
        resetStartEndDates();
    }
}