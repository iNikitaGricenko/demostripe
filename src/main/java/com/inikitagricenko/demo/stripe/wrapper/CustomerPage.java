package com.inikitagricenko.demo.stripe.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.model.dto.CustomerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class CustomerPage extends RestPage<CustomerResponseDTO> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CustomerPage(@JsonProperty("content") List<CustomerResponseDTO> content,
                        @JsonProperty("number") int page,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") long total) {
        super(content, page, size, total);
    }

    public CustomerPage(Page<CustomerResponseDTO> page) {
        super(page);
    }
}
