package com.everis.archivado.invoice.api;

import com.everis.archivado.config.ElasticConfig;
import com.everis.archivado.config.Util;
import com.everis.archivado.invoice.model.Invoice;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import io.swagger.annotations.ApiParam;
import io.swagger.api.erroradvisor.ErrorException;
import io.swagger.api.erroradvisor.Messages;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Controller
public class AccountsApiController implements AccountsApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsApiController.class);
    private static final String CTE_INVALID_NOT_FOUND = "INVALID_NOT_FOUND";
    private static final String CTE_INVALID_ARGUMENT = "INVALID_ARGUMENT";
    private static final String CTE_INVALID_ARGUMENT_STARTDATE_GT_ENDDATE = "INVALID_ARGUMENT_STARTDATE_GT_ENDDATE";
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
    Messages messages;

    @Autowired
    public AccountsApiController(@Value("${archivado.gmtcountry}") int gmtCountry) throws UnknownHostException {
        this.gmtCountry = gmtCountry;
    }

    public ResponseEntity<List<Invoice>> retrieveAccountInvoices(@ApiParam(value = "Account number", required = true) @PathVariable("accountId") String accountId, @ApiParam(value = "Return only invoices issued after this value (billDate set after this value)") @Valid @RequestParam(value = "startBillDate", required = false) String startBillDate, @ApiParam(value = "Return only invoices issued before this value (billDate set before this value)") @Valid @RequestParam(value = "endBillDate", required = false) String endBillDate, @ApiParam(value = "Return only invoices for an specific status") @Valid @RequestParam(value = "status", required = false) String status) throws Exception {
        List<Invoice> listInvoicesResponse=new ArrayList<Invoice>();

        MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
        result = "OK";
        resultCode = "200";
        Long a = new Long(0);
        Long b = new Long(0);
        Long c = new Long(0);
        Long d = new Long(0);
        boolean bError = false;
        HashMap<String, String> mapStringDates = new HashMap<String, String>();
        HashMap<String, DateTime> mapDateTimeDates = new HashMap<String, DateTime>();
        if(startBillDate!=null) {
            mapStringDates.put("startBillDate", startBillDate);
        }
        if(endBillDate!=null) {
            mapStringDates.put("endBillDate", endBillDate);
        }
        try {
            mapDateTimeDates = Util.validateDatesAndGetDates(mapStringDates);
        } catch (Exception e1) {
            throw new ErrorException(CTE_INVALID_ARGUMENT, e1.getMessage());
        }
        DateTime startDate = mapDateTimeDates.get("startBillDate");
        DateTime endDate = mapDateTimeDates.get("endBillDate");
        try {
            a = System.currentTimeMillis();
            b = System.currentTimeMillis();
            List<Invoice> listInvoices = invoiceList(accountId, startDate, endDate);
            c = System.currentTimeMillis();
            listInvoicesResponse = getListInvoices(listInvoices);

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
            MDC.put("nameServer", localHost.getHostName());
            MDC.put("ip", localHost.getHostAddress());
            MDC.put("originSystem", "originSystem");
            if (request.getHeader("x-token-info")!=null) {
                MDC.put("appUser", request.getHeader("x-token-info"));
            }else {
                MDC.put("appUser", "Not_Informed");
            }
            MDC.put("apiName", "Invoices");
            if (request.getQueryString() != null)
                MDC.put("serviceName", request.getMethod().concat(request.getServletPath()).concat("?").concat(request.getQueryString()));
            else MDC.put("serviceName", request.getMethod().concat(request.getServletPath()));
            if (request.getHeader("x-correlator") != null) {
                responseHeaders.add("x-correlator", request.getHeader("x-correlator"));
                MDC.put("ejecId", request.getHeader("x-correlator"));
            } else {
                MDC.put("ejecId", UUID.randomUUID().toString());
            }
            MDC.put("timeServiceResponse", String.valueOf(d - a));
            MDC.put("timeBackendResponse", String.valueOf(c - b));
            MDC.put("result", result);
            MDC.put("resultCode", resultCode);
            if (!bError) {
                LOGGER.trace("resultCode: " + resultCode + ", result : " + result);
                MDC.clear();
            }
        }

        return new ResponseEntity<List<Invoice>>(listInvoicesResponse, responseHeaders, HttpStatus.OK);
    }

    private List<Invoice> invoiceList(String accountId , DateTime startDate, DateTime endDate) throws Exception {
        DateTimeFormatter elasticDateFormat = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        QueryBuilder query = null;
        RangeQueryBuilder rangeQueryBuilder = null;
        if (startDate == null && (endDate == null)) {

            query = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termQuery("account_id", accountId));

        } else if ((startDate != null) && (endDate != null)) {
            startDate = startDate.minusHours(gmtCountry);
            endDate = endDate.minusHours(gmtCountry);
            LOGGER.debug("StartDate Local: " + elasticDateFormat.print(startDate) + " EndDate Local:" + elasticDateFormat.print(endDate) + " with GMTZone: " + gmtCountry);
            if (startDate.getMillis() > endDate.getMillis())
                throw new ErrorException(CTE_INVALID_ARGUMENT, getMessage(CTE_INVALID_ARGUMENT_STARTDATE_GT_ENDDATE));
            rangeQueryBuilder = QueryBuilders.rangeQuery("billingPeriod.startDate")
                    .gte(elasticDateFormat.print(startDate))
                    .lte(elasticDateFormat.print(endDate));

            query = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termQuery("account_id",accountId))
                    .filter(rangeQueryBuilder);

        } else if (startDate == null || endDate == null) {
            if (endDate == null) {
                //QUERY without endDate filter
                rangeQueryBuilder = QueryBuilders.rangeQuery("billingPeriod.startDate")
                        .gte(elasticDateFormat.print(startDate.minusHours(gmtCountry)))
                        .lte(elasticDateFormat.print(startDate.minusHours(gmtCountry).plusMonths(6)));
                query = QueryBuilders.boolQuery()
                        .filter(QueryBuilders.termQuery("account_id",accountId))
                        .filter(rangeQueryBuilder);
            } else if (startDate == null) {
                //QUERY without startDate filter
                rangeQueryBuilder = QueryBuilders.rangeQuery("billingPeriod.startDate")
                        .gte(elasticDateFormat.print(endDate.minusMonths(6)))
                        .lte(elasticDateFormat.print(endDate));
                query = QueryBuilders.boolQuery()
                        .filter(QueryBuilders.termQuery("account_id",accountId))
                        .filter(rangeQueryBuilder);
            }
        }

        ////////----------crear llamada api Rest
        SearchRequest searchRequest = new SearchRequest("invoices_view")
                .routing(accountId)
                .types("eventinvoices")
                .source(new SearchSourceBuilder()
                        .query(query)
                        .size(100)
                        .sort(new FieldSortBuilder("creation_date").order(SortOrder.DESC))
                );
        /////llamada al RESTHighClient/////
        //b = System.currentTimeMillis();
        SearchResponse response = client.client().search(searchRequest, RequestOptions.DEFAULT);
        //c = System.currentTimeMillis();
        /////procesado Response RESTHighClient
        List<Invoice> listInvoices = (List<Invoice>) (Object) getObjectMapper(response.getHits().getHits(), Invoice.class);
        if (listInvoices.size() <= 0) {
            LOGGER.error("index: invoices_view, routing: " + accountId + " does not exists.");
            //throw new ErrorException(CTE_INVALID_NOT_FOUND, "index: invoices_view, routing: " + accountId + " does not exists.");
        }

        return listInvoices;

    }

    private List<Invoice> getListInvoices(List<Invoice> listInvoice) {

        List<Invoice> listObjectInvoce = new ArrayList<>();
        for (Invoice item : listInvoice) {

            if( item.getChargeItemsLength().intValue() > 0) {
                item.setHref("/invoicing/v1/invoices/".concat(item.getLegalInvoiceNo()).concat("/appliedCharges"));
                listObjectInvoce.add(item);
            }

        }
        return listObjectInvoce;
    }

    private String getMessage(String codMessage) {
        return messages.get(codMessage);
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


}
