/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.everis.archivado.invoice.api;

import com.everis.archivado.invoice.model.Invoice;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(value = "accounts", description = "the accounts API")
public interface AccountsApi {

    @ApiOperation(value = "Search invoices by account number", nickname = "retrieveAccountInvoices", notes = "", response = Invoice.class, responseContainer = "List", tags = {"invoices",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Invoice.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/accounts/{accountId}/invoices",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Invoice>> retrieveAccountInvoices(@ApiParam(value = "Account number", required = true) @PathVariable("accountId") String accountId, @ApiParam(value = "Return only invoices issued after this value (billDate set after this value)") @Valid @RequestParam(value = "startBillDate", required = false) String startBillDate, @ApiParam(value = "Return only invoices issued before this value (billDate set before this value)") @Valid @RequestParam(value = "endBillDate", required = false) String endBillDate, @ApiParam(value = "Return only invoices for an specific status") @Valid @RequestParam(value = "status", required = false) String status) throws Exception;

}
