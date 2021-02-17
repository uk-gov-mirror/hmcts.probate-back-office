package uk.gov.hmcts.probate.transformer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.probate.model.ccd.raw.request.CaseData;
import uk.gov.hmcts.probate.model.ccd.raw.response.ResponseCaseData;
import uk.gov.hmcts.probate.model.fee.FeesResponse;
import uk.gov.hmcts.probate.service.fee.FeeService;

import static uk.gov.hmcts.probate.model.Constants.NO;
import static uk.gov.hmcts.probate.model.Constants.YES;

@RequiredArgsConstructor
@Service
public class SolicitorPBAPaymentDefaulter {
    private final FeeService feeService;

    public void defaultPageFlowForPayments(CaseData data,
                                           ResponseCaseData.ResponseCaseDataBuilder<?, ?> responseCaseDataBuilder) {

        FeesResponse feesResponse = feeService.getAllFeesData(
            data.getIhtNetValue(),
            data.getExtraCopiesOfGrant(),
            data.getOutsideUKGrantCopies());

        responseCaseDataBuilder.solsNeedsPBAPayment(feesResponse.getTotalAmount().doubleValue() == 0 
            ? NO : YES);
    }
}
