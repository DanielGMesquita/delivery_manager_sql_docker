package org.danielmesquita.dto;

import java.util.List;
import lombok.Data;

@Data
public class CategoryDTO {
  private String name;
  private List<Long> productIdList;
}
