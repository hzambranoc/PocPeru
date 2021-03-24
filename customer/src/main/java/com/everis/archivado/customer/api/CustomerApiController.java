package com.everis.archivado.customer.api;

import com.everis.archivado.config.ElasticConfig;
import com.everis.archivado.customer.model.AccountItemsInner;
import com.everis.archivado.customer.model.Customer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import io.swagger.annotations.ApiParam;
import io.swagger.api.erroradvisor.ErrorException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerApiController implements CustomerApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApiController.class);
    private static final String CTE_INVALID_NOT_FOUND = "INVALID_NOT_FOUND";
    private int gmtCountry;
    String result = "";
    String resultCode = "";
    final InetAddress localHost = InetAddress.getLocalHost();
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    HttpServletRequest request;
    @Autowired
    ElasticConfig client;

    @Autowired
    public CustomerApiController(@Value("${archivado.gmtcountry}") int gmtCountry) throws UnknownHostException {
        this.gmtCountry = gmtCountry;
    }

    public ResponseEntity<List<Customer>> retrieveCustomerAccounts(@ApiParam(value = "DNI value of the customer to fetch",required=true) @PathVariable("dniValue") String dniValue,@ApiParam(value = "Return only invoices for an specific status") @Valid @RequestParam(value = "status", required = false) String status) throws Exception {
            String accept = request.getHeader("Accept");

        QueryBuilder query;
        MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
        List<Customer> customerResponse = new ArrayList<>();
        result = "OK";
        resultCode = "200";
        Long a = new Long(0);
        Long b = new Long(0);
        Long c = new Long(0);
        Long d = new Long(0);
        boolean bError = false;

        try {
            a = System.currentTimeMillis();
            query = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termQuery("id_document.value", dniValue));

            SearchRequest searchRequest = new SearchRequest("customer_view")
                    //.routing(customerId)
                    .types("eventcustomer")
                    .source(new SearchSourceBuilder()
                            .query(query)
                            .sort(new FieldSortBuilder("creation_date").order(SortOrder.DESC))
                    );
            b = System.currentTimeMillis();
            SearchResponse response = client.client().search(searchRequest, RequestOptions.DEFAULT);
            c = System.currentTimeMillis();

            List<Customer> listCustomer = (List<Customer>) (Object) getObjectMapper(response.getHits().getHits(), Customer.class);
            if (listCustomer.size() <= 0) {
                LOGGER.error("index: customer_view, dniValue: " + dniValue + " does not exists.");
                throw new ErrorException(CTE_INVALID_NOT_FOUND, "index: customer_view, dniValue: " + dniValue + " does not exists.");
            } else {
                customerResponse = getCustomerAccounts(listCustomer);
            }

        } catch (Exception e) {
            if (e instanceof ErrorException) {
                b = System.currentTimeMillis();
                c = System.currentTimeMillis();
            } else {
                c = System.currentTimeMillis();
            }
            bError = true;
            LOGGER.error("Error trace:", e);
            e.printStackTrace();
            throw e;
        } finally {
            d = System.currentTimeMillis();
            createRecordLog(dniValue, responseHeaders, a, b, c, d, bError);
        }

        return new ResponseEntity<>(customerResponse, responseHeaders, HttpStatus.OK);
    }

    private void createRecordLog(@ApiParam(value = "Id of the customer to fetch", required = true) @PathVariable("customerId") String customerId, MultiValueMap<String, String> responseHeaders, Long a, Long b, Long c, Long d, boolean bError) {
        MDC.put("nameServer", localHost.getHostName());
        MDC.put("ip", localHost.getHostAddress());
        MDC.put("originSystem", "originSystem");
        MDC.put("appUser", customerId);
        MDC.put("apiName", "customer");
        MDC.put("serviceName", request.getMethod().concat(request.getServletPath()));
        if (request.getHeader("x-correlator") != null) {
            responseHeaders.add("x-correlator", request.getHeader("x-correlator"));
            MDC.put("ejecId", request.getHeader("x-correlator"));
        } else {
            MDC.put("ejecId", UUID.randomUUID().toString());
        }
        MDC.put("timeServiceResponse", String.valueOf(d - a));
        MDC.put("timeBackendResponse", String.valueOf(c - b));
        LOGGER.info("total_TimeBackendResponse: " + String.valueOf(c - b));
        MDC.put("result", result);
        MDC.put("resultCode", resultCode);
        if (!bError) {
            LOGGER.trace("resultCode: " + resultCode + ", result : " + result);
            MDC.clear();
        }
    }

    public ResponseEntity<List<Customer>> retrieveCustomerByPhoneNumber(@ApiParam(value = "phoneNumber of the customer to fetch", required = true) @PathVariable("phoneNumber") String phoneNumber, @ApiParam(value = "Return only invoices for an specific status") @Valid @RequestParam(value = "status", required = false) String status) throws Exception {
        QueryBuilder query;
        MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
        List<Customer> customerResponse = new ArrayList<>();
        result = "OK";
        resultCode = "200";
        Long a = new Long(0);
        Long b = new Long(0);
        Long c = new Long(0);
        Long d = new Long(0);
        boolean bError = false;

        try {
            a = System.currentTimeMillis();
            query = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termQuery("phone_number", phoneNumber));

            SearchRequest searchRequest = new SearchRequest("customer_view")
                    //.routing(phoneNumber)
                    .types("eventcustomer")
                    .source(new SearchSourceBuilder()
                            .query(query)
                            .sort(new FieldSortBuilder("creation_date").order(SortOrder.DESC))
                    );
            b = System.currentTimeMillis();
            SearchResponse response = client.client().search(searchRequest, RequestOptions.DEFAULT);
            c = System.currentTimeMillis();

            List<Customer> listCustomer = (List<Customer>) (Object) getObjectMapper(response.getHits().getHits(), Customer.class);
            if (listCustomer.size() <= 0) {
                LOGGER.error("index: customer_view, customerId: " + phoneNumber + " does not exists.");
                throw new ErrorException(CTE_INVALID_NOT_FOUND, "index: customer_view, id: " + phoneNumber + " does not exists.");
            } else {
                customerResponse = getCustomers(listCustomer);
            }

        } catch (Exception e) {
            if (e instanceof ErrorException) {
                b = System.currentTimeMillis();
                c = System.currentTimeMillis();
            } else {
                c = System.currentTimeMillis();
            }
            bError = true;
            LOGGER.error("Error trace:", e);
            e.printStackTrace();
            throw e;
        } finally {
            d = System.currentTimeMillis();
            createRecordLog(phoneNumber, responseHeaders, a, b, c, d, bError);
        }

        return new ResponseEntity<List<Customer>>(customerResponse, responseHeaders, HttpStatus.OK);
    }

    private List<Object> getObjectMapper(SearchHit[] hits, Class valueType) throws java.io.IOException {
        List<Object> listObject = new ArrayList<>();
        objectMapper.registerModule(new JodaModule());
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, true);
        objectMapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, false);
        for (SearchHit hit : hits) {
            Object objectItem = new Object();
            String source = hit.getSourceAsString();
            objectItem = objectMapper.readValue(source, valueType);
            listObject.add(objectItem);
            objectItem = null;

        }
        return listObject;
    }

    private  List<Customer> getCustomerAccounts(List<Customer> listCustomer) {
        //Customer customerResponse = listCustomer.get(0);
    	for(Customer customerResponse : listCustomer) {
            for (AccountItemsInner accountItem : customerResponse.getAccountItems()) {
                accountItem.setHref("/invoicing/v1/accounts/".concat(accountItem.getExternalId()).concat("/invoices"));
            }    		
    	}

        return listCustomer;

    }

    private List<Customer> getCustomers(List<Customer> listCustomer) {
        for (Customer customerItem : listCustomer) {
            for (AccountItemsInner accountItem : customerItem.getAccountItems()) {
                accountItem.setHref("/invoicing/v1/accounts/".concat(accountItem.getExternalId()).concat("/invoices"));
            }
        }
        return listCustomer;
    }

}
