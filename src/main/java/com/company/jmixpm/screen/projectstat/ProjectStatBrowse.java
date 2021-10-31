package com.company.jmixpm.screen.projectstat;

import com.company.jmixpm.app.ProjectStatsService;
import io.jmix.core.LoadContext;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.ProjectStat;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("ProjectStat.browse")
@UiDescriptor("project-stat-browse.xml")
@LookupComponent("projectStatsTable")
public class ProjectStatBrowse extends StandardLookup<ProjectStat> {
    @Autowired
    private ProjectStatsService projectStatsService;

    @Install(to = "projectStatsDL", target = Target.DATA_LOADER)
    private List<ProjectStat> projectStatsDLLoadDelegate(LoadContext<ProjectStat> loadContext) {
        return projectStatsService.fetchProjectsStatistics();
    }
}