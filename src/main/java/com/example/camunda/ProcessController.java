package com.example.camunda;

import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
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

  @PostMapping("/{applicationId}/status-updated")
  Map<String, Object> statusUpdated(@PathVariable String applicationId) {
    var res = client.newPublishMessageCommand()
            .messageName("Msg_ApplicationStatusUpdated")
            .correlationKey(applicationId)
            .timeToLive(Duration.ofMinutes(1))   // phủ khoảng token đang ở "Ghi nhận nộp lại"
            .send().join();
    return Map.of("messageKey", res.getMessageKey());
  }
}
