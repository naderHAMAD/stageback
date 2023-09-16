package com.test.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

@RestController
@RequestMapping("/workflow")
public class WorkflowCamundaController {

    @Autowired
    private ProcessEngine processEngine;

    @PostMapping("/startprocess/{process}")
    public String startProcess(@PathVariable String process) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(process);
        return processInstance.getId();
    }

    @GetMapping("/tasks/{processInstanceId}")
    public String getTasks(@PathVariable String processInstanceId) {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(processInstanceId);
        List<Task> tasks = taskQuery.list();

        StringBuilder taskIds = new StringBuilder();
        for (Task task : tasks) {
            taskIds.append(task.getId()).append(",");
        }

        return taskIds.toString();
    }

    @PutMapping("/completetask/{taskId}")
    public void completeTask(@PathVariable String taskId) {
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(taskId);
    }

    // ... Ajoutez d'autres méthodes selon vos besoins

}
