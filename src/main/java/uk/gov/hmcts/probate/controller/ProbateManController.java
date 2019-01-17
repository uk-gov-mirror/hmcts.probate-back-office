package uk.gov.hmcts.probate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.probate.model.CaseType;
import uk.gov.hmcts.probate.model.ccd.CaseMatch;
import uk.gov.hmcts.probate.model.ccd.raw.CollectionMember;
import uk.gov.hmcts.probate.model.ccd.raw.request.CallbackRequest;
import uk.gov.hmcts.probate.model.ccd.raw.request.CaseData;
import uk.gov.hmcts.probate.model.ccd.raw.response.CallbackResponse;
import uk.gov.hmcts.probate.model.ccd.raw.response.ResponseCaseData;
import uk.gov.hmcts.probate.model.probateman.ProbateManModel;
import uk.gov.hmcts.probate.model.probateman.ProbateManType;
import uk.gov.hmcts.probate.service.LegacySearchService;
import uk.gov.hmcts.probate.service.ProbateManService;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uk.gov.hmcts.probate.model.CaseType.LEGACY;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProbateManController {

    private final ProbateManService probateManService;
    private final LegacySearchService legacySearchService;

    @GetMapping(path = "/probateManTypes/{probateManType}/cases/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProbateManModel> saveGrantApplicationToCcd(@PathVariable("probateManType") ProbateManType probateManType,
                                                                     @PathVariable("id") String id) {
        return ResponseEntity.ok(probateManService.getProbateManModel(Long.parseLong(id), probateManType));
    }

    @GetMapping(path = "/legacy/search", consumes = APPLICATION_JSON_UTF8_VALUE, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<CallbackResponse> legacySearch(@RequestBody CallbackRequest callbackRequest,
                                                         HttpServletRequest request) {
        log.info("Performing legacy case search");
        List<CollectionMember<CaseMatch>> caseMatchesList = legacySearchService.findLegacyCaseMatches(callbackRequest.getCaseDetails());

        ResponseCaseData responseCaseData = ResponseCaseData.builder()
                .legacySearchResultRows(caseMatchesList)
                .build();

        CallbackResponse callbackResponse = CallbackResponse.builder()
                .data(responseCaseData)
                .build();
        return ResponseEntity.ok(callbackResponse);
    }

    @PostMapping(path = "/legacy/doImport", consumes = APPLICATION_JSON_UTF8_VALUE, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<CallbackResponse> doImport(@RequestBody CallbackRequest callbackRequest,
                                                     HttpServletRequest request) {

        log.info("Performing legacy case import");
        List<CollectionMember<CaseMatch>> rows = legacySearchService.importLegacyRows(callbackRequest.getCaseDetails().getData());

        ResponseCaseData responseCaseData = ResponseCaseData.builder()
                .legacySearchResultRows(rows)
                .build();

        CallbackResponse callbackResponse = CallbackResponse.builder()
                .data(responseCaseData)
                .build();
        return ResponseEntity.ok(callbackResponse);
    }

}
