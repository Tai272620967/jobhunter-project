package vn.hoidanit.jobhunter.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCartTotalQuantityDTO {
    private int totalQuantity;

    public ResCartTotalQuantityDTO(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
