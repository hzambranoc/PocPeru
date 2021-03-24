package com.everis.archivado.customer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * Customer
 */
@Validated

public class Customer {
    @JsonProperty("name")
    private String name = null;

    @JsonProperty("phone_number")
    private String phoneNumber = null;

    @JsonProperty("customer_id")
    private String customerId = null;

    @JsonProperty("creation_date")
    private DateTime creationDate = null;

    @JsonProperty("status_date")
    private DateTime statusDate = null;

    /**
     * Gets or Sets customerStatus
     */
    public enum CustomerStatusEnum {
        OPEN("open"),

        CLOSE("close"),
    	
        ABIERTO("Abierto"),

        CANCELADO("Cancelado");

        private String value;

        CustomerStatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static CustomerStatusEnum fromValue(String text) {
            for (CustomerStatusEnum b : CustomerStatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("customer_status")
    private CustomerStatusEnum customerStatus = null;

    @JsonProperty("id_document")
    private IdDocument idDocument = null;

    @JsonProperty("account_items")
    private AccountItems accountItems = null;

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Customer customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get phoneNumber
     * @return phoneNumber
     **/
    @ApiModelProperty(value = "")


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer creationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * Get creationDate
     *
     * @return creationDate
     **/
    @ApiModelProperty(value = "")

    @Valid

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Customer statusDate(DateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    /**
     * Get statusDate
     *
     * @return statusDate
     **/
    @ApiModelProperty(value = "")

    @Valid

    public DateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(DateTime statusDate) {
        this.statusDate = statusDate;
    }

    public Customer customerStatus(CustomerStatusEnum customerStatus) {
        this.customerStatus = customerStatus;
        return this;
    }

    /**
     * Get customerStatus
     *
     * @return customerStatus
     **/
    @ApiModelProperty(value = "")


    public CustomerStatusEnum getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatusEnum customerStatus) {
        this.customerStatus = customerStatus;
    }

    public Customer idDocument(IdDocument idDocument) {
        this.idDocument = idDocument;
        return this;
    }

    /**
     * Get idDocument
     *
     * @return idDocument
     **/
    @ApiModelProperty(value = "")

    @Valid

    public IdDocument getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(IdDocument idDocument) {
        this.idDocument = idDocument;
    }

    public Customer accountItems(AccountItems accountItems) {
        this.accountItems = accountItems;
        return this;
    }

    /**
     * Get accountItems
     *
     * @return accountItems
     **/
    @ApiModelProperty(value = "")

    @Valid

    public AccountItems getAccountItems() {
        return accountItems;
    }

    public void setAccountItems(AccountItems accountItems) {
        this.accountItems = accountItems;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(this.name, customer.name) &&
                Objects.equals(this.phoneNumber, customer.phoneNumber) &&
                Objects.equals(this.customerId, customer.customerId) &&
                Objects.equals(this.creationDate, customer.creationDate) &&
                Objects.equals(this.statusDate, customer.statusDate) &&
                Objects.equals(this.customerStatus, customer.customerStatus) &&
                Objects.equals(this.idDocument, customer.idDocument) &&
                Objects.equals(this.accountItems, customer.accountItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,phoneNumber, customerId, creationDate, statusDate, customerStatus, idDocument, accountItems);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Customer {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
        sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
        sb.append("    statusDate: ").append(toIndentedString(statusDate)).append("\n");
        sb.append("    customerStatus: ").append(toIndentedString(customerStatus)).append("\n");
        sb.append("    idDocument: ").append(toIndentedString(idDocument)).append("\n");
        sb.append("    accountItems: ").append(toIndentedString(accountItems)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

