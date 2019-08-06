package calculator;

import java.time.LocalDateTime;

public class PromotionValidity {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public PromotionValidity(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean checkPromotionValid(LocalDateTime promotionDate) {
        return (promotionDate.isAfter(startDate) || promotionDate.isEqual(startDate)) && (promotionDate.isBefore(endDate) || promotionDate.isEqual(endDate));
    }
}
