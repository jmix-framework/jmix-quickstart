package com.company.jmixpm.screen.project;

import com.company.jmixpm.entity.Project;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {
    private boolean hideArchived;
    @Autowired
    private CollectionLoader<Project> projectsDl;
    @Autowired
    private GroupTable<Project> projectsTable;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (hideArchived) {
            projectsDl.setQuery("select e from Project e where e.archived = false");
        }
    }

    @Subscribe("projectsTable.archive")
    public void onProjectsTableArchive(Action.ActionPerformedEvent event) {
        Project project = projectsTable.getSingleSelected();
        if (project == null) {
            return;
        }
        project.setArchived(true);
        dataManager.save(project);

        projectsDl.load();

        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withDescription("Project " + project.getName() + " has been archived")
                .withHideDelayMs(2000)
                .show();
    }

    public void setHideArchived(boolean hideArchived) {
        this.hideArchived = hideArchived;
    }
}