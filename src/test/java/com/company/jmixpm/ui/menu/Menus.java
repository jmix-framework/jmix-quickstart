/*
 * Copyright (c) 2008-2013 Haulmont. All rights reserved.
 */

package com.company.jmixpm.ui.menu;

import com.company.jmixpm.ui.screen.ProjectBrowse;
import io.jmix.masquerade.component.SideMenu;

public final class Menus {
    public static final SideMenu.Menu<ProjectBrowse> PROJECT_BROWSE =
            new SideMenu.Menu<>(ProjectBrowse.class, "application", "Project.browse");
}
