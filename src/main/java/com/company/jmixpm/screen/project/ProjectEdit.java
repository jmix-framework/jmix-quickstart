package com.company.jmixpm.screen.project;

import com.company.jmixpm.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Project> event) {
        event.getEntity().setManager((User) currentAuthentication.getUser());
    }
}