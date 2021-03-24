package com.everis.archivado.invoice.api;

import com.everis.archivado.config.ElasticConfig;
import com.everis.archivado.invoice.model.ChargeItems;
import com.everis.archivado.invoice.model.Invoice;
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

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class InvoicesApiController implements InvoicesApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoicesApiController.class);
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
    public InvoicesApiController(@Value("${archivado.gmtcountry}") int gmtCountry) throws UnknownHostException {
        this.gmtCountry = gmtCountry;
    }

    public ResponseEntity<ChargeItems> appliedCharges(@ApiParam(value = "Id of the invoice to fetch", required = true) @PathVariable("invoiceId") String invoiceId) throws Exception {

        DateTimeFormatter elasticDateFormat = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        QueryBuilder query;
        MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
        ChargeItems listCharge = new ChargeItems();
        List<Invoice> listInvoice = new ArrayList<Invoice>();
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
                    .filter(QueryBuilders.matchQuery("legal_invoice_no", invoiceId));


            ////////----------crear llamada api Rest
            SearchRequest searchRequest = new SearchRequest("invoices_view")
                    .types("eventinvoices")
                    .source(new SearchSourceBuilder()
                            .query(query)
                            .sort(new FieldSortBuilder("creation_date").order(SortOrder.DESC))
                    );
            /////llamada al RESTHighClient/////
            b = System.currentTimeMillis();
            SearchResponse response = client.client().search(searchRequest, RequestOptions.DEFAULT);
            c = System.currentTimeMillis();
            /////procesado Response RESTHighClient
            listInvoice = (List<Invoice>) (Object) getObjectMapper(response.getHits().getHits(), Invoice.class);
            if (listInvoice.size() <= 0) {
                LOGGER.error("index: invoices_view, id: " + invoiceId + " does not exists.");
                throw new ErrorException(CTE_INVALID_NOT_FOUND, "index: invoices_view, id: " + invoiceId + " does not exists.");
            }
            //listCharge = getChargeItems(listInvoices);
        } catch (Exception e) {
            if (e instanceof ErrorException) {
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
            if (request.getHeader("x-token-info") != null) {
                MDC.put("appUser", request.getHeader("x-token-info"));
            } else {
                MDC.put("appUser", "Not_Informed");
            }
            MDC.put("apiName", "Invoices");
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
            responseHeaders.add("x-total-count", String.valueOf(listCharge.size()));
            if (!bError) {
                LOGGER.trace("resultCode: " + resultCode + ", result : " + result);
                MDC.clear();
            }
        }

        return new ResponseEntity<ChargeItems>(listInvoice.get(0).getChargeItems(), responseHeaders, HttpStatus.OK);
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
