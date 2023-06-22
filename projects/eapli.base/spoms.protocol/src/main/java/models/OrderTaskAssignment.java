package models;
/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
/**
 * Order task assignment DTO odel
 */
public class OrderTaskAssignment extends ErrorResponse {
    private Long agvId;
    private Long orderId;
    public List<ProductDTO> products;

    /**
     * Initializes a new instance of OrderTaskAssignment
     */
    public OrderTaskAssignment(){ //For jackson
        super(false, "");
    }

    /**
     * Initializes a new instance of OrderTaskAssignment with error message
     * @param message Error message
     */
    public OrderTaskAssignment(String message) {
        super(true, message);
    }

    /**
     * Initializes a new instance of OrderTaskAssignment
     * @param orderId OrderId
     */
    public OrderTaskAssignment(Long orderId){
        super(false, null);
        this.orderId = orderId;
        this.products = new ArrayList<>();
    }

    /**
     * Initializes a new instance of OrderTaskAssignment
     * @param orderId OrderId
     * @param products List of products in order
     */
    public OrderTaskAssignment(Long orderId, List<ProductDTO> products) {
        super(false, null);
        this.orderId = orderId;
        this.products = products;
    }

    /**
     * Initializes a new instance of OrderTaskAssignment
     * @param orderId OrderId
     * @param agvId AGVId
     * @param products Products
     */
    public OrderTaskAssignment(Long orderId, Long agvId, List<ProductDTO> products) {
        super(false, null);
        this.orderId = orderId;
        this.agvId = agvId;
        this.products = products;
    }

    /**
     * Returns the id of the order to be done
     * @return Long
     */
    public Long OrderToDo(){ return orderId; }

    /**
     * Returns the AGV id of the order to be done
     * @return Long
     */
    public Long AgvId(){ return agvId; }
}
