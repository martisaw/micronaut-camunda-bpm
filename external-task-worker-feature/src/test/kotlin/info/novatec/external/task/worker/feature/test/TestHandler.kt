package info.novatec.external.task.worker.feature.test

import info.novatec.external.task.worker.feature.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import java.util.HashMap
import javax.inject.Singleton
import org.camunda.bpm.client.task.ExternalTaskHandler as ExternalTaskHandler1

@Singleton
@ExternalTaskSubscription(topicName = "my-topic")
class TestHandler: ExternalTaskHandler1 {

    override fun execute(externalTask: ExternalTask?, externalTaskService: ExternalTaskService?) {
        // Put your business logic here

        // Get a process variable

        // Put your business logic here

        // Get a process variable
        val item = externalTask!!.getVariable<Int>("itemNumber")
        val value = externalTask!!.getVariable<Int>("itemValue")

        //Process the task

        //Process the task
        val approved = value <= 500
        val variables: MutableMap<String, Any> = HashMap()
        variables["approved"] = approved


        // Complete the task


        // Complete the task
        externalTaskService!!.complete(externalTask, variables)
        println("Finished item $item with Value $value")
    }
}
