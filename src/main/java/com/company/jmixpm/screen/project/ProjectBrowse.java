package com.company.jmixpm.screen.project;

import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {
}