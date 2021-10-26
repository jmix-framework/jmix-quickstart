package com.company.jmixpm.screen.document;

import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Document;

@UiController("Document.edit")
@UiDescriptor("document-edit.xml")
@EditedEntityContainer("documentDc")
public class DocumentEdit extends StandardEditor<Document> {
}