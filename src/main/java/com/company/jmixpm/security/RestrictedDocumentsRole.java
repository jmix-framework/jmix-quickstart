package com.company.jmixpm.security;

import com.company.jmixpm.entity.Document;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Restrict documents by user", code = RestrictedDocumentsRole.CODE)
public interface RestrictedDocumentsRole {
    String CODE = "restricted-documents";

    @JpqlRowLevelPolicy(entityClass = Document.class,
            join = "join {E}.project proj",
            where = "proj.manager.id = :current_user_id " +
                    "or {E}.createdBy = :current_user_username"
    )
    void restrictDocumentByProjectManager();
}
