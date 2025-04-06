package com.timposulabs.springboot.pagination.service;

import com.timposulabs.springboot.pagination.dto.ProductDTO;
import com.timposulabs.springboot.pagination.exception.ProductNotFoundException;
import com.timposulabs.springboot.pagination.model.Product;
import com.timposulabs.springboot.pagination.repository.ProductRepository;
import com.timposulabs.springboot.pagination.util.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Page<ProductDTO> findAll(final Pageable pageable) {
        final Page<Product> page = productRepository.findAll(pageable);
        return new PageImpl<>(page.getContent()
                .stream()
                .map(product -> productMapper.toDTO(product))
                .collect(Collectors.toList()),
                pageable, page.getTotalElements());
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new ProductNotFoundException("Product by Id not found " + id));
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product by Id not found " + id));

        existingProduct.setName(productDTO.name());
        existingProduct.setDescription(productDTO.description());
        existingProduct.setPrice(productDTO.price());

        return productMapper.toDTO(productRepository.save(existingProduct));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found by id " + id);
        }
        productRepository.deleteById(id);
    }

}
