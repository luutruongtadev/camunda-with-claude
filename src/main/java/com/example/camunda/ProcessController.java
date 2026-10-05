package com.example.camunda;

import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/process")
public class ProcessController {

  private final CamundaClient client;

  public ProcessController(CamundaClient client) {
    this.client = client;
  }

  @PostMapping("/start")
  public Map<String, Object> start(@RequestBody Map<String, Object> vars) {
    ProcessInstanceEvent pi = client.newCreateInstanceCommand()
        .bpmnProcessId("hello-process")
        .latestVersion()
        .variables(vars)
        .send()
        .join();
    return Map.of(
        "processInstanceKey", pi.getProcessInstanceKey(),
        "version", pi.getVersion());
  }
}
