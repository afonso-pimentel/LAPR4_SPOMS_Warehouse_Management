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

package enums;

/**
 * Enumeration for the available Message Codes
 */
public enum MessageCode {
    /**
     * Communication Test
     */
    COMMTEST,

    /**
     * Disconnect
     */
    DISCONN,

    /**
     * Acknowledgment
     */
    ACK,

    /**
     * Status Request
     */
    STATUS_REQUEST,

    /**
     * Status Response
     */
    STATUS_RESPONSE,

    /**
     * AGV Status Report Request
     */
    AGV_STATUS_REPORT_REQUEST,

    /**
     * AGV Status Report Response
     */
    AGV_STATUS_REPORT_RESPONSE,

    /**
     * Request to obtain ALL AGV Status Reports
     */
    ALL_AGV_STATUS_REPORT_REQUEST,

    /**
     * Response to request of obtaining ALL AGV Status Reports
     */
    ALL_AGV_STATUS_REPORT_RESPONSE,

    /**
     * Ask for new Task Request
     */
    NEW_TASK_REQUEST,

    /**
     * Ask for new Task Response
     */
    NEW_TASK_RESPONSE,

    /**
     * Product Catalog message request
     */
    PRODUCT_CATALOG_REQUEST,

    /**
     * Product Catalog message response
     */
    PRODUCT_CATALOG_RESPONSE,

    /**
     * Order Status message request
     */
    ORDER_STATUS_REQUEST,

    /**
     * Order Status message response
     */
    ORDER_STATUS_RESPONSE,
    
    /**
     * Request to obtain all the customer surveys
     */
    CUSTOMER_SURVEYS_REQUEST,

    /**
     * Response to request to obtain all the customer surveys
     */
    CUSTOMER_SURVEYS_RESPONSE,

    /**
     * Request to obtain the questionnaire for a specific survey
     */
    QUESTIONNAIRE_REQUEST,

    /**
     * Response to request to obtain the questionnaire for a specific survey
     */
    QUESTIONNAIRE_RESPONSE,

    /**
     * Request to send the answer for a specific survey from a customer
     */
    ANSWER_REQUEST,

    /**
     * Response to send to obtain the answer for a specific survey from a customer
     */
    ANSWER_RESPONSE,

    /**
     * Get World positions request
     */
    WORLD_POSITIONS_REQUEST,

    /**
     * Get World positions response
     */
    WORLD_POSITIONS_RESPONSE,
    /**
     * Get Agv digital twin basic information request
     */
    AGV_INFO_REQUEST,

    /**
     * Get Agv digital twin basic information response
     */
    AGV_INFO_RESPONSE,

    /**
     * Message for a new order assigment
     */
    ORDER_ASSIGNMENT,

    /**
     * Response message for ORDER assignment
     */
    ORDER_ASSIGNMENT_RESPONSE,

    /**
     * Completed Order
     */
    ORDER_COMPLETED,
}
