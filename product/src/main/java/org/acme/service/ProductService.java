package org.acme.service;

import org.acme.dto.ProductDTO;
import org.acme.entity.ProductEntity;
import org.acme.repository.ProductRepositoy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepositoy productRepositoy;

    public List<ProductDTO> getAllProducts(){

        List<ProductDTO> products = new ArrayList<>();

        productRepositoy.findAll().stream().forEach(item -> {
            products.add(mapProductEntityToDTO(item));
        });

        return products;
    }

    public void createNewProduct(ProductDTO product){

        productRepositoy.persist(mapProductDTOToEntity(product));

    }

    public void changeProduct(Long id, ProductDTO product){

        ProductEntity productEntity = productRepositoy.findById(id);

        productEntity.setName(product.getName());
        productEntity.setCategory(product.getCategory());
        productEntity.setPrice(product.getPrice());
        productEntity.setModel(product.getModel());
        productEntity.setDescription(product.getDescription());

        productRepositoy.persist(productEntity);

    }


    public void delete(Long id){

        productRepositoy.deleteById(id);
    }


    private ProductDTO mapProductEntityToDTO(ProductEntity productEntity){

        ProductDTO product = new ProductDTO();

        product.setName(productEntity.getName());
        product.setCategory(productEntity.getCategory());
        product.setPrice(productEntity.getPrice());
        product.setModel(productEntity.getModel());
        product.setDescription(productEntity.getDescription());

        return product;
    }

    private ProductEntity mapProductDTOToEntity(ProductDTO productDTO){

        ProductEntity product = new ProductEntity();

        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setModel(productDTO.getModel());
        product.setDescription(productDTO.getDescription());

        return product;
    }


}
