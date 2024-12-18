package vn.hoidanit.jobhunter.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResAddToCart {
    private String message;
    private int totalQuantity;

    public ResAddToCart(String message, int totalQuantity) {
        this.message = message;
        this.totalQuantity = totalQuantity;
    }
}
