package com.example.camunda;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HelloWorker {

  // autoComplete=true (default): return Map -> merge vào process variables
  @JobWorker(type = "say-hello")
  public Map<String, Object> sayHello(@Variable String name) {

    return Map.of("greeting", "Xin chào " + (name == null ? "bạn" : name));
  }

  @JobWorker(type = "los.detect-missing-docs")
  public Map<String, Object> missdoc(@Variable String name) {
    return Map.of("greeting", "Xin chào " + (name == null ? "bạn" : name));
  }


}
