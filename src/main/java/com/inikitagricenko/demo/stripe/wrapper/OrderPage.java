package com.inikitagricenko.demo.stripe.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class OrderPage extends RestPage<CustomerOrderResponseDTO> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OrderPage(@JsonProperty("content") List<CustomerOrderResponseDTO> content,
                     @JsonProperty("number") int page,
                     @JsonProperty("size") int size,
                     @JsonProperty("totalElements") long total) {
        super(content, page, size, total);
    }

    public OrderPage(Page<CustomerOrderResponseDTO> page) {
        super(page);
    }
}
