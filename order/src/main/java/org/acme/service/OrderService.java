package org.acme.service;

import org.acme.client.CustomerClient;
import org.acme.client.ProductClient;
import org.acme.dto.CustomerDTO;
import org.acme.dto.OrderDTO;
import org.acme.dto.ProductDTO;
import org.acme.entity.OrderEntity;
import org.acme.repository.OrderRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    @RestClient
    private CustomerClient customerClient;

    @Inject
    @RestClient
    private ProductClient productClient;

    public List<OrderDTO> getAllOrders(){
        List<OrderDTO> orders = new ArrayList<>();

        orderRepository.findAll().stream().forEach(item -> {
            orders.add(mapEntityToDTO(item));
        });

        return orders;

    }

    public void saveNewOrder(OrderDTO orderDTO){
        CustomerDTO customerDTO = customerClient.getCustomerById(orderDTO.getCustomerId());

        if(customerDTO.getName().equals(orderDTO.getCustomerName())
            && productClient.getProductById(orderDTO.getProductId()) != null){
            orderRepository.persist(mapDTOToEntity(orderDTO));
        } else{
            throw new NotFoundException();
        }
    }

    private OrderDTO mapEntityToDTO(OrderEntity orderEntity){

        OrderDTO order = new OrderDTO();

        order.setOrderValue(orderEntity.getOrderValue());
        order.setCustomerName(orderEntity.getCustomerName());
        order.setCustomerId(orderEntity.getCustomerId());
        order.setProductId(orderEntity.getProductId());

        return order;
    }

    private OrderEntity mapDTOToEntity(OrderDTO orderDTO){

            OrderEntity order = new OrderEntity();

        order.setOrderValue(orderDTO.getOrderValue());
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerId(orderDTO.getCustomerId());
        order.setProductId(orderDTO.getProductId());


        return order;
    }

}
