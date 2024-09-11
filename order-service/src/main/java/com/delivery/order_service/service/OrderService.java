package com.delivery.order_service.service;

import com.delivery.order_service.dto.*;
import com.delivery.order_service.dto.request.MenuOrderRequest;
import com.delivery.order_service.dto.request.PlaceOrderRequest;
import com.delivery.order_service.dto.response.OrderResponse;
import com.delivery.order_service.entity.OrderItems;
import com.delivery.order_service.entity.Orders;
import com.delivery.order_service.feign.MenuServiceFeignClient;
import com.delivery.order_service.feign.StoreServiceFeignClient;
import com.delivery.order_service.feign.UserServiceFeignClient;
import com.delivery.order_service.global.success.CommonResponse;
import com.delivery.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional()
public class OrderService {
    private final UserServiceFeignClient userServiceFeignClient;
    private final StoreServiceFeignClient storeServiceFeignClient;
    private final MenuServiceFeignClient menuServiceFeignClient;
    private final OrdersRepository ordersRepository;


    public OrderResponse placeOrder(PlaceOrderRequest request) {
        CommonResponse<FindUserByNameResponse> userResponse = userServiceFeignClient.getUserById(request.getUserId());
        FindUserByNameResponse user = userResponse.data();
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        CommonResponse<FindStoreByNameResponse> storeResponse = storeServiceFeignClient.getStoreById(request.getStoreId());
        FindStoreByNameResponse store = storeResponse.data();
        if (store == null || store.getStatus() != FindStoreByNameResponse.StoreStatus.OPEN) {
            throw new IllegalArgumentException("Store is closed or does not exist.");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItems> orderItemsList = new ArrayList<>();

        for (MenuOrderRequest menuOrder : request.getMenuOrders()) {
            CommonResponse<FindMenuByNameResponse> menuResponse = menuServiceFeignClient.getMenuById(menuOrder.getMenuId());
            FindMenuByNameResponse menu = menuResponse.data();
            if (menu == null) {
                throw new IllegalArgumentException("Menu item not found.");
            }

            BigDecimal itemPrice = menu.getPrice().multiply(BigDecimal.valueOf(menuOrder.getQuantity()));
            totalPrice = totalPrice.add(itemPrice);

            OrderItems orderItem = OrderItems.builder()
                    .menuItem(menuOrder.getMenuId())
                    .quantity(menuOrder.getQuantity())
                    .price(itemPrice)
                    .build();
            orderItemsList.add(orderItem);
        }

        BigDecimal minimumOrderPrice = store.getMinimumOrderPrice();
        if (totalPrice.compareTo(minimumOrderPrice) < 0) {
            throw new IllegalArgumentException("Total price is lower than the store's minimum order price.");
        }

        totalPrice = totalPrice.add(store.getDeliveryFee());

        Orders order = Orders.builder()
                .userId(request.getUserId())
                .storeId(request.getStoreId())
                .orderStatus(Orders.OrderStatus.PLACED)
                .totalPrice(totalPrice)
                .deliveryAddress(request.getDeliveryAddress())
                .orderItems(orderItemsList)
                .build();
        ordersRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .deliveryAddress(order.getDeliveryAddress())
                .userId(order.getUserId())
                .storeId(order.getStoreId())
                .menuOrders(request.getMenuOrders())
                .build();
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<Orders> orders = ordersRepository.findByUserId(userId);
        return orders.stream().map(this::convertToOrderResponse).collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByStore(Long storeId) {
        List<Orders> orders = ordersRepository.findByStoreId(storeId);
        return orders.stream().map(this::convertToOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long orderId, Orders.OrderStatus status) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found."));
        order.setOrderStatus(status);
        ordersRepository.save(order);

        return convertToOrderResponse(order);
    }

    public OrderResponse cancelOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found."));
        if (order.getOrderStatus() != Orders.OrderStatus.COMPLETED && order.getOrderStatus() != Orders.OrderStatus.CANCELLED) {
            order.setOrderStatus(Orders.OrderStatus.CANCELLED);
            ordersRepository.save(order);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order cannot be canceled.");
        }

        return convertToOrderResponse(order);
    }

    private OrderResponse convertToOrderResponse(Orders order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .deliveryAddress(order.getDeliveryAddress())
                .userId(order.getUserId())
                .storeId(order.getStoreId())
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
