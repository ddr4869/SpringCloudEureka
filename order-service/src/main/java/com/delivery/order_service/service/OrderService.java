package com.delivery.order_service.service;

import com.delivery.order_service.dto.FindMenuByNameResponse;
import com.delivery.order_service.dto.FindStoreByNameResponse;
import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.dto.response.CreateOrderResponse;
import com.delivery.order_service.dto.OrderResponse;
import com.delivery.order_service.entity.OrderItems;
import com.delivery.order_service.entity.Orders;
import com.delivery.order_service.feign.UserServiceFeignClient;
import com.delivery.order_service.global.success.CommonResponse;
import com.delivery.order_service.repository.OrdersRepository;
import com.delivery.order_service.webclient.MenuServiceClient;
import com.delivery.order_service.webclient.StoreServiceClient;
import com.delivery.order_service.webclient.UserServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {
    //private final OrdersRepository orderRepository;
    private final UserServiceFeignClient userServiceFeignClient;
    private final WebClient webClient;
    private final UserServiceClient userServiceClient;
    private final StoreServiceClient storeServiceClient;
    private final MenuServiceClient menuServiceClient;
    private final OrdersRepository ordersRepository;


    public CreateOrderResponse createOrder() {
        log.info("Before call user microservice");

        CommonResponse<FindUserByNameResponse> feignResponse = userServiceFeignClient.getUserByUsername("tom");
        log.info("after call user microservice");
        return CreateOrderResponse.builder()
                .orderId(1L)
                .build();
    }

//    public Mono<Object> createOrderWebClient() {
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = attr.getRequest();
//        String authorizationHeader = request.getHeader("Authorization");
//
//        // WebClient로 유저, 스토어, 메뉴를 비동기로 가져옴
//        Mono<CommonResponse<FindUserByNameResponse>> userMono = userServiceClient.getUserByUsernameClient(authorizationHeader, "tom");
//        Mono<CommonResponse<FindStoreByNameResponse>> storeMono = storeServiceClient.getStoreByNameClient(authorizationHeader, "TEST");
//        Mono<CommonResponse<FindMenuByNameResponse>> menuMono = menuServiceClient.getMenuServiceByNameClient(authorizationHeader, "TEST");
//
//        // Mono.zip을 사용해 1~3의 작업을 동시에 실행하고, 결과를 처리
//        return Mono.zip(userMono, storeMono, menuMono)
//                .flatMap(tuple -> {
//                    // 1. 동기적으로 데이터를 처리 (map을 사용해도 괜찮음)
//                    FindUserByNameResponse user = tuple.getT1().getData();
//                    FindStoreByNameResponse store = tuple.getT2().getData();
//                    FindMenuByNameResponse menu = tuple.getT3().getData();
//
//                    if (user == null || store == null || menu == null) {
//                        return Mono.error(new RuntimeException("Invalid data: User, Store, or Menu not found."));
//                    }
//
//                    if (!store.getStatus().equals(FindStoreByNameResponse.StoreStatus.OPEN)) {
//                        return Mono.error(new RuntimeException("Store is not OPEN."));
//                    }
//
//                    // 2. 주문 생성
//                    Orders order = Orders.builder()
//                            .userId(user.getUserId())
//                            .storeId(store.getId())
//                            .menuId(menu.getItemId())
//                            .totalPrice(menu.getPrice())
//                            .orderStatus(Orders.OrderStatus.PLACED)
//                            .build();
//
//
//                    ordersRepository.save(order);
//
//                    // 3. 비동기로 DB에 저장 (ordersRepository.save는 비동기 작업이므로 flatMap 사용)
//                    return OrderResponse.builder()
//                            .orderId(order.getOrderId())
//                            .userId(order.getUserId())
//                            .storeId(order.getStoreId())
//                            .menuId(order.getMenuId())
//                            .totalPrice(order.getTotalPrice())
//                            .orderStatus(Orders.OrderStatus.PLACED)
//                            .build();
//                });
//    }
}
