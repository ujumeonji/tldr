package run.cd80.tldr.api.base

abstract class WorkflowScenario<REQUEST, RESPONSE> {

    abstract fun execute(command: REQUEST): RESPONSE
}
