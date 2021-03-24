package com.everis.archivado.invoice.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Invoice
 */
//@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {
    @JsonProperty("id_factura")
    private String idFactura = null;

    @JsonProperty("invoice_type")
    private String invoiceType = null;

    @JsonProperty("customer_id")
    private String customerId = null;

    @JsonProperty("charge_items_length")
    private BigDecimal chargeItemsLength = null;

    @JsonProperty("creation_date")
    private String creationDate = null;

    @JsonProperty("legal_invoice_no")
    private String legalInvoiceNo = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("account_id")
    private String accountId = null;

    @JsonProperty("total_amount")
    private TotalAmount totalAmount = null;

    @JsonProperty("billingPeriod")
    private BillingPeriod billingPeriod = null;

    @JsonProperty("bill_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Lima", locale="es-PE")
    private Date billDate = null;

    @JsonProperty("due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Lima", locale="es-PE")
    private Date dueDate = null;

    @JsonProperty("statement_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Lima", locale="es-PE")
    private Date statementDate = null;

    /**
     * Gets or Sets paymentStatus
     */
    public enum PaymentStatusEnum {
        OPEN("open"),

        CLOSE("close");

        private String value;

        PaymentStatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static PaymentStatusEnum fromValue(String text) {
            for (PaymentStatusEnum b : PaymentStatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("payment_status")
    private PaymentStatusEnum paymentStatus = null;

    @JsonProperty("customer")
    private Customer customer = null;

    @JsonProperty("charge_items")
    private ChargeItems chargeItems = null;

    public Invoice idFactura(String idFactura) {
        this.idFactura = idFactura;
        return this;
    }

    /**
     * Get idFactura
     *
     * @return idFactura
     **/
    @ApiModelProperty(value = "")


    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }
    public Invoice invoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    /**
     * Get invoiceType
     * @return invoiceType
     **/
    @ApiModelProperty(value = "")


    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
    public Invoice customerId(String customerId) {
        this.customerId = customerId;
        return this;
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

    public Invoice chargeItemsLength(BigDecimal chargeItemsLength) {
        this.chargeItemsLength = chargeItemsLength;
        return this;
    }

    /**
     * Get chargeItemsLength
     *
     * @return chargeItemsLength
     **/
    @ApiModelProperty(value = "")

    @Valid

    public BigDecimal getChargeItemsLength() {
        return chargeItemsLength;
    }

    public void setChargeItemsLength(BigDecimal chargeItemsLength) {
        this.chargeItemsLength = chargeItemsLength;
    }

    public Invoice creationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * Get creationDate
     *
     * @return creationDate
     **/
    @ApiModelProperty(value = "")


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Invoice legalInvoiceNo(String legalInvoiceNo) {
        this.legalInvoiceNo = legalInvoiceNo;
        return this;
    }

    /**
     * Get legalInvoiceNo
     *
     * @return legalInvoiceNo
     **/
    @ApiModelProperty(value = "")


    public String getLegalInvoiceNo() {
        return legalInvoiceNo;
    }

    public void setLegalInvoiceNo(String legalInvoiceNo) {
        this.legalInvoiceNo = legalInvoiceNo;
    }

    public Invoice href(String href) {
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

    public Invoice accountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * Get accountId
     *
     * @return accountId
     **/
    @ApiModelProperty(value = "")


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Invoice totalAmount(TotalAmount totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    /**
     * Get totalAmount
     *
     * @return totalAmount
     **/
    @ApiModelProperty(value = "")

    @Valid

    public TotalAmount getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(TotalAmount totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Invoice billingPeriod(BillingPeriod billingPeriod) {
        this.billingPeriod = billingPeriod;
        return this;
    }

    /**
     * Get billingPeriod
     *
     * @return billingPeriod
     **/
    @ApiModelProperty(value = "")

    @Valid

    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(BillingPeriod billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public Invoice billDate(Date billDate) {
        this.billDate = billDate;
        return this;
    }

    /**
     * Get billDate
     *
     * @return billDate
     **/
    @ApiModelProperty(value = "")

    @Valid

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Invoice dueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * Get dueDate
     *
     * @return dueDate
     **/
    @ApiModelProperty(value = "")

    @Valid

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Invoice statementDate(Date statementDate) {
        this.statementDate = statementDate;
        return this;
    }

    /**
     * Get statementDate
     *
     * @return statementDate
     **/
    @ApiModelProperty(value = "")

    @Valid

    public Date getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(Date statementDate) {
        this.statementDate = statementDate;
    }

    public Invoice paymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    /**
     * Get paymentStatus
     *
     * @return paymentStatus
     **/
    @ApiModelProperty(value = "")


    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Invoice customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    /**
     * Get customer
     *
     * @return customer
     **/
    @ApiModelProperty(value = "")

    @Valid

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Invoice chargeItems(ChargeItems chargeItems) {
        this.chargeItems = chargeItems;
        return this;
    }

    /**
     * Get chargeItems
     * @return chargeItems
     **/
    @ApiModelProperty(value = "")

    @Valid

    public ChargeItems getChargeItems() {
        return chargeItems;
    }

    public void setChargeItems(ChargeItems chargeItems) {
        this.chargeItems = chargeItems;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        return Objects.equals(this.idFactura, invoice.idFactura) &&
                Objects.equals(this.customerId, invoice.customerId) &&
                Objects.equals(this.invoiceType, invoice.invoiceType) &&
                Objects.equals(this.chargeItemsLength, invoice.chargeItemsLength) &&
                Objects.equals(this.creationDate, invoice.creationDate) &&
                Objects.equals(this.legalInvoiceNo, invoice.legalInvoiceNo) &&
                Objects.equals(this.href, invoice.href) &&
                Objects.equals(this.accountId, invoice.accountId) &&
                Objects.equals(this.totalAmount, invoice.totalAmount) &&
                Objects.equals(this.billingPeriod, invoice.billingPeriod) &&
                Objects.equals(this.billDate, invoice.billDate) &&
                Objects.equals(this.dueDate, invoice.dueDate) &&
                Objects.equals(this.statementDate, invoice.statementDate) &&
                Objects.equals(this.paymentStatus, invoice.paymentStatus) &&
                Objects.equals(this.customer, invoice.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactura, customerId, chargeItemsLength, creationDate, legalInvoiceNo, href, accountId, totalAmount, billingPeriod, billDate, dueDate, statementDate, paymentStatus, customer);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Invoice {\n");

        sb.append("    idFactura: ").append(toIndentedString(idFactura)).append("\n");
        sb.append("    invoiceType: ").append(toIndentedString(invoiceType)).append("\n");
        sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    chargeItemsLength: ").append(toIndentedString(chargeItemsLength)).append("\n");
        sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
        sb.append("    legalInvoiceNo: ").append(toIndentedString(legalInvoiceNo)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
        sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
        sb.append("    billingPeriod: ").append(toIndentedString(billingPeriod)).append("\n");
        sb.append("    billDate: ").append(toIndentedString(billDate)).append("\n");
        sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
        sb.append("    statementDate: ").append(toIndentedString(statementDate)).append("\n");
        sb.append("    paymentStatus: ").append(toIndentedString(paymentStatus)).append("\n");
        sb.append("    customer: ").append(toIndentedString(customer)).append("\n");
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

