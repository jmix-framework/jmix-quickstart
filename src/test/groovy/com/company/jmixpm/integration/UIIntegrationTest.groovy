package com.company.jmixpm.integration

import com.company.jmixpm.JmixPmApplication
import com.company.jmixpm.entity.Task
import com.company.jmixpm.entity.User
import com.company.jmixpm.screen.main.MainScreen
import com.company.jmixpm.screen.task.TaskEdit
import io.jmix.core.DataManager
import io.jmix.core.ValueLoadContext
import io.jmix.core.entity.KeyValueEntity
import io.jmix.ui.ScreenBuilders
import io.jmix.ui.screen.OpenMode
import io.jmix.ui.testassist.spec.UiTestAssistSpecification
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = JmixPmApplication)
class UIIntegrationTest extends UiTestAssistSpecification {

    @Autowired
    ScreenBuilders screenBuilders

    @SpringBean
    DataManager dataManager = Stub()

    def "Check the computation of the least busy user"() {
        given: "Create a test data"

        def user1 = metadata.create(User)
        user1.username = "user1"
        def user2 = metadata.create(User)
        user2.username = "user2"

        def entity1 = metadata.create(KeyValueEntity)
        entity1.setValue("user", user1)
        entity1.setValue("estimatedEfforts", 10L)
        def entity2 = metadata.create(KeyValueEntity)
        entity2.setValue("user", user2)
        entity2.setValue("estimatedEfforts", 2L)
        def entities = [entity1, entity2]

        and: "Stub for dataManager"
        dataManager.loadValues(_ as ValueLoadContext) >> entities

        and: "Open the main screen - it is the entry point of the application"
        def mainScreen = vaadinUi.screens.create(MainScreen, OpenMode.ROOT)
        vaadinUi.screens.show(mainScreen)

        and: "Create a TaskEdit screen for new Task"
        def taskEdit = screenBuilders.editor(Task, mainScreen)
                .withScreenClass(TaskEdit)
                .newEntity()
                .show()

        when: "Get assigned user from TaskEdit screen"
        def assignee = taskEdit.getEditedEntity().assignee

        then: "Check with user2"
        assignee == user2
    }
}
