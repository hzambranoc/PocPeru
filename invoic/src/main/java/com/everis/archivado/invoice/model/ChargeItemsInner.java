package com.everis.archivado.invoice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

/**
 * ChargeItemsInner
 */
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeItemsInner {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("amount")
    private Amount amount = null;

    @JsonProperty("subscription")
    private String subscription = null;

    @JsonProperty("effective_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Lima", locale="es-PE")
    private Date effectiveDate = null;

    @JsonProperty("charge_code")
    private String chargeCode = null;

    @JsonProperty("charge_description")
    private String chargeDescription = null;

    @JsonProperty("offer_name")
    private String offerName = null;

    public ChargeItemsInner id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChargeItemsInner amount(Amount amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Get amount
     *
     * @return amount
     **/
    @ApiModelProperty(value = "")

    @Valid

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public ChargeItemsInner subscription(String subscription) {
        this.subscription = subscription;
        return this;
    }

    /**
     * Get subscription
     *
     * @return subscription
     **/
    @ApiModelProperty(value = "")


    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public ChargeItemsInner effectiveDate(Date effectiveDate) {
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

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ChargeItemsInner chargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
        return this;
    }

    /**
     * Get chargeCode
     *
     * @return chargeCode
     **/
    @ApiModelProperty(value = "")


    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public ChargeItemsInner chargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
        return this;
    }

    /**
     * Get chargeDescription
     *
     * @return chargeDescription
     **/
    @ApiModelProperty(value = "")


    public String getChargeDescription() {
        return chargeDescription;
    }

    public void setChargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
    }

    public ChargeItemsInner offerName(String offerName) {
        this.offerName = offerName;
        return this;
    }

    /**
     * Get offerName
     *
     * @return offerName
     **/
    @ApiModelProperty(value = "")


    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChargeItemsInner chargeItemsInner = (ChargeItemsInner) o;
        return Objects.equals(this.id, chargeItemsInner.id) &&
                Objects.equals(this.amount, chargeItemsInner.amount) &&
                Objects.equals(this.subscription, chargeItemsInner.subscription) &&
                Objects.equals(this.effectiveDate, chargeItemsInner.effectiveDate) &&
                Objects.equals(this.chargeCode, chargeItemsInner.chargeCode) &&
                Objects.equals(this.chargeDescription, chargeItemsInner.chargeDescription) &&
                Objects.equals(this.offerName, chargeItemsInner.offerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, subscription, effectiveDate, chargeCode, chargeDescription, offerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ChargeItemsInner {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
        sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
        sb.append("    effectiveDate: ").append(toIndentedString(effectiveDate)).append("\n");
        sb.append("    chargeCode: ").append(toIndentedString(chargeCode)).append("\n");
        sb.append("    chargeDescription: ").append(toIndentedString(chargeDescription)).append("\n");
        sb.append("    offerName: ").append(toIndentedString(offerName)).append("\n");
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

