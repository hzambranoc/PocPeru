package com.everis.archivado.invoice.model;

import java.util.Objects;
import com.everis.archivado.invoice.model.IdDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Customer
 */
//@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("id_document")
  private IdDocument idDocument = null;

  public Customer name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Customer idDocument(IdDocument idDocument) {
    this.idDocument = idDocument;
    return this;
  }

  /**
   * Get idDocument
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
        Objects.equals(this.idDocument, customer.idDocument);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, idDocument);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Customer {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    idDocument: ").append(toIndentedString(idDocument)).append("\n");
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

