package com.everis.archivado.invoice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TotalAmount
 */
@Validated

public class TotalAmount   {
  @JsonProperty("value_tax")
  private BigDecimal valueTax = null;

  @JsonProperty("value")
  private BigDecimal value = null;

  @JsonProperty("currency")
  private String currency = null;

  @JsonProperty("tax_included")
  private Boolean taxIncluded = false;

  @JsonProperty("tax")
  private BigDecimal tax = null;

  public TotalAmount valueTax(BigDecimal valueTax) {
    this.valueTax = valueTax;
    return this;
  }

  /**
   * Get valueTax
   * @return valueTax
   **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getValueTax() {
    return valueTax;
  }

  public void setValueTax(BigDecimal valueTax) {
    this.valueTax = valueTax;
  }

  public TotalAmount value(BigDecimal value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public TotalAmount currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   **/
  @ApiModelProperty(value = "")


  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public TotalAmount taxIncluded(Boolean taxIncluded) {
    this.taxIncluded = taxIncluded;
    return this;
  }

  /**
   * Get taxIncluded
   * @return taxIncluded
   **/
  @ApiModelProperty(value = "")


  public Boolean isTaxIncluded() {
    return taxIncluded;
  }

  public void setTaxIncluded(Boolean taxIncluded) {
    this.taxIncluded = taxIncluded;
  }

  public TotalAmount tax(BigDecimal tax) {
    this.tax = tax;
    return this;
  }

  /**
   * Get tax
   * @return tax
   **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTax() {
    return tax;
  }

  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TotalAmount totalAmount = (TotalAmount) o;
    return Objects.equals(this.valueTax, totalAmount.valueTax) &&
            Objects.equals(this.value, totalAmount.value) &&
            Objects.equals(this.currency, totalAmount.currency) &&
            Objects.equals(this.taxIncluded, totalAmount.taxIncluded) &&
            Objects.equals(this.tax, totalAmount.tax);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valueTax, value, currency, taxIncluded, tax);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TotalAmount {\n");

    sb.append("    valueTax: ").append(toIndentedString(valueTax)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    taxIncluded: ").append(toIndentedString(taxIncluded)).append("\n");
    sb.append("    tax: ").append(toIndentedString(tax)).append("\n");
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

