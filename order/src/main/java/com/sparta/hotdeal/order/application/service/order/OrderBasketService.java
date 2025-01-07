package com.sparta.hotdeal.order.application.service.order;

import com.sparta.hotdeal.order.application.exception.ApplicationException;
import com.sparta.hotdeal.order.application.exception.ErrorCode;
import com.sparta.hotdeal.order.domain.entity.AuditingDate;
import com.sparta.hotdeal.order.domain.entity.basket.Basket;
import com.sparta.hotdeal.order.domain.repository.BasketRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderBasketService {

    private final BasketRepository basketRepository;
    @Transactional(readOnly = true)
    public List<Basket> getBasketList(List<UUID> basketIds) {
        List<Basket> baskets = basketRepository.findByIdIn(basketIds);
        if (baskets.isEmpty()) {
            throw new ApplicationException(ErrorCode.BASKET_NOT_FOUND_EXCEPTION);
        }
        return baskets;
    }

    public void deleteBasketList(List<Basket> basketList) {
        basketList.forEach(basket -> basket.delete("email@email.com"));
    }

    public void recoverBasketList(List<Basket> basketList) {
        basketList.forEach(AuditingDate::recover);
    }
}
