package com.company.jmixpm.screen.task;

import com.company.jmixpm.entity.Task;
import io.jmix.core.FileRef;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected Downloader downloader;

    @Install(to = "tasksTable.attachment", subject = "columnGenerator")
    private Component tasksTableAttachmentColumnGenerator(Task task) {
        FileRef fileRef = task.getAttachment();
        if (fileRef != null) {
            LinkButton linkButton = uiComponents.create(LinkButton.class);
            linkButton.setAction(new BaseAction("download")
                    .withCaption(fileRef.getFileName())
                    .withHandler(e -> downloader.download(fileRef)));
            return linkButton;
        } else {
            return null;
        }
    }
}