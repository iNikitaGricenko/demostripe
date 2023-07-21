package com.inikitagricenko.demo.stripe.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.model.dto.CustomerRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class ProductPage extends RestPage<ProductResponseDTO> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductPage(@JsonProperty("content") List<ProductResponseDTO> content,
                       @JsonProperty("number") int page,
                       @JsonProperty("size") int size,
                       @JsonProperty("totalElements") long total) {
        super(content, page, size, total);
    }

    public ProductPage(Page<ProductResponseDTO> page) {
        super(page);
    }
}
