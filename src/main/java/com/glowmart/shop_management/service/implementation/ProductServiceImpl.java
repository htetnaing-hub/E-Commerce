package com.glowmart.shop_management.service.implementation;

import com.glowmart.shop_management.common.CommonFunction;
import com.glowmart.shop_management.converter.ProductConverter;
import com.glowmart.shop_management.dto.ProductDto;
import com.glowmart.shop_management.repository.ProductRepository;
import com.glowmart.shop_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        if (CommonFunction.isValidPriceAndDiscount(productDto.getProductOriginalPrice(),
                productDto.getProductDiscountPercentage()) == false){
            throw new IllegalArgumentException("Product Original Price and Product Discount Percentage are not valid!");
        }
        productDto.setProductDiscountAmount(CommonFunction.calculateDiscountAmount(productDto.getProductOriginalPrice(),
                productDto.getProductDiscountPercentage()));
        productDto.setProductFinalPrice(productDto.getProductOriginalPrice() - productDto.getProductDiscountAmount());
        productDto.setProductName(productDto.getProductName().toLowerCase());
        productDto.setProductDescription(productDto.getProductDescription().toLowerCase());
        return ProductConverter.convertToProductDto(productRepository.save(ProductConverter.convertToProduct(productDto)));
    }
}
