package org.danielmesquita.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {
  @NotNull(message = "Order date cannot be null")
  private LocalDateTime date;

  @NotNull(message = "Product ID list cannot be null")
  private List<Long> productIdList;
}
