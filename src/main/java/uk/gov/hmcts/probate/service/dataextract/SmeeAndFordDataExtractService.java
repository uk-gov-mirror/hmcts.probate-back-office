package uk.gov.hmcts.probate.service.dataextract;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.probate.exception.ClientException;
import uk.gov.hmcts.probate.model.ccd.raw.Document;
import uk.gov.hmcts.probate.model.ccd.raw.request.ReturnedCaseDetails;
import uk.gov.hmcts.probate.service.CaseQueryService;
import uk.gov.hmcts.probate.service.NotificationService;
import uk.gov.service.notify.NotificationClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmeeAndFordDataExtractService {
    private final CaseQueryService caseQueryService;
    private final NotificationService notificationService;


    public Document performSmeeAndFordExtractForDateRange(String fromDate, String toDate) {
        if (fromDate.equals(toDate)) {
            return performSmeeAndFordExtractForDate(fromDate);
        } else {
            log.info("SF data extract initiated from date: {} to {}", fromDate, toDate);
            List<ReturnedCaseDetails> cases = caseQueryService.findCaseStateWithinTimeFrame(fromDate, toDate);
            log.info("Found {} cases with dated document for SF from-to", cases.size());

            return sendSmeeAndFordEmail(cases);
        }


    }

    private Document performSmeeAndFordExtractForDate(String date) {
        log.info("SF data extract initiated for date: {}", date);
        List<ReturnedCaseDetails> cases = caseQueryService.findCasesWithDatedDocument(date);
        log.info("Found {} cases with dated document for SF", cases.size());

        return sendSmeeAndFordEmail(cases);
    }

    private Document sendSmeeAndFordEmail(List<ReturnedCaseDetails> cases) {
        log.info("Sending email to SF for {} filtered cases", cases.size());
        if (!cases.isEmpty()) {
            log.info("Sending email to SF");
            try {
                return notificationService.sendSmeeAndFordEmail(cases);
            } catch (NotificationClientException e) {
                log.warn("NotificationService exception sending email to SF", e);
                throw new ClientException(HttpStatus.BAD_GATEWAY.value(),
                    "Error on NotificationService sending email to SF");
            }
        }

        return null;
    }
}
