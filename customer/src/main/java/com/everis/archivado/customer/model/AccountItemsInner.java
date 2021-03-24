package com.everis.archivado.customer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * AccountItemsInner
 */
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountItemsInner {
    @JsonProperty("customer_no")
    private String customerNo = null;

    @JsonProperty("entity_id")
    private String entityId = null;

    @JsonProperty("entity_type")
    private String entityType = null;

    @JsonProperty("creation_date")
    private DateTime creationDate = null;

    @JsonProperty("effective_date")
    private DateTime effectiveDate = null;

    @JsonProperty("external_id")
    private String externalId = null;

    @JsonProperty("balance")
    private BigDecimal balance = null;

    /**
     * Gets or Sets status
     */
    public enum StatusEnum {
        OPEN("open"),

        CLOSE("close"),
    	
        ABIERTO("Abierto"),

        CANCELADO("Cancelado");
    	
        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("status")
    private StatusEnum status = null;

    @JsonProperty("href")
    private String href = null;

    public AccountItemsInner customerNo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }

    /**
     * Get customerNo
     *
     * @return customerNo
     **/
    @ApiModelProperty(value = "")


    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public AccountItemsInner entityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    /**
     * Get entityId
     *
     * @return entityId
     **/
    @ApiModelProperty(value = "")


    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public AccountItemsInner entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * Get entityType
     *
     * @return entityType
     **/
    @ApiModelProperty(value = "")


    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public AccountItemsInner creationDate(DateTime creationDate) {
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

    public AccountItemsInner effectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    /**
     * Get effectiveDate
     *
     * @return effectiveDate
     **/
    @ApiModelProperty(value = "")

    @Valid

    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public AccountItemsInner externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * Get externalId
     *
     * @return externalId
     **/
    @ApiModelProperty(value = "")


    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public AccountItemsInner balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Get balance
     *
     * @return balance
     **/
    @ApiModelProperty(value = "")

    @Valid

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountItemsInner status(StatusEnum status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(value = "")


    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public AccountItemsInner href(String href) {
        this.href = href;
        return this;
    }

    /**
     * Get href
     *
     * @return href
     **/
    @ApiModelProperty(value = "")


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountItemsInner accountItemsInner = (AccountItemsInner) o;
        return Objects.equals(this.customerNo, accountItemsInner.customerNo) &&
                Objects.equals(this.entityId, accountItemsInner.entityId) &&
                Objects.equals(this.entityType, accountItemsInner.entityType) &&
                Objects.equals(this.creationDate, accountItemsInner.creationDate) &&
                Objects.equals(this.effectiveDate, accountItemsInner.effectiveDate) &&
                Objects.equals(this.externalId, accountItemsInner.externalId) &&
                Objects.equals(this.balance, accountItemsInner.balance) &&
                Objects.equals(this.status, accountItemsInner.status) &&
                Objects.equals(this.href, accountItemsInner.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNo, entityId, entityType, creationDate, effectiveDate, externalId, balance, status, href);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AccountItemsInner {\n");

        sb.append("    customerNo: ").append(toIndentedString(customerNo)).append("\n");
        sb.append("    entityId: ").append(toIndentedString(entityId)).append("\n");
        sb.append("    entityType: ").append(toIndentedString(entityType)).append("\n");
        sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
        sb.append("    effectiveDate: ").append(toIndentedString(effectiveDate)).append("\n");
        sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
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

