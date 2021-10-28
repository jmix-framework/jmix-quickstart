package com.company.jmixpm.security;

import io.jmix.core.accesscontext.SpecificOperationAccessContext;

public class ArchiveProjectContext extends SpecificOperationAccessContext {
    public static final String NAME = "pm.projects.archive";

    public ArchiveProjectContext() {
        super(NAME);
    }
}
