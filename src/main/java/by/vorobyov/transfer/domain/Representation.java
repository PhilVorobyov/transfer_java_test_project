package by.vorobyov.transfer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Representation {

  @JsonProperty
  private String content;

}