package crynet.testovoe.Domain.DTOs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RecordDto {
    private Long id;
    private Long userId;
    private Long projectId;
    private BigDecimal hoursWorked;
    private Date createdAt;
}
