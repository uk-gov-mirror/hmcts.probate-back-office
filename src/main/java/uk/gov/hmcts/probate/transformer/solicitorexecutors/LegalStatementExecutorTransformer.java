package uk.gov.hmcts.probate.transformer.solicitorexecutors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.probate.model.ccd.raw.AdditionalExecutorApplying;
import uk.gov.hmcts.probate.model.ccd.raw.AdditionalExecutorNotApplying;
import uk.gov.hmcts.probate.model.ccd.raw.CollectionMember;
import uk.gov.hmcts.probate.model.ccd.raw.request.CaseData;
import uk.gov.hmcts.probate.service.DateFormatterService;
import uk.gov.hmcts.probate.service.solicitorexecutor.ExecutorListMapperService;
import uk.gov.hmcts.probate.service.solicitorexecutor.FormattingService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class LegalStatementExecutorTransformer extends ExecutorsTransformer {

    private final DateFormatterService dateFormatterService;

    public LegalStatementExecutorTransformer(ExecutorListMapperService executorListMapperService,
                                             DateFormatterService dateFormatterService) {
        super(executorListMapperService);
        this.dateFormatterService = dateFormatterService;
    }

    /**
     * Map all executors into executors applying and executors not applying lists for the solicitor legal statement.
     */
    public void mapSolicitorExecutorFieldsToLegalStatementExecutorFields(CaseData caseData) {

        // Create executor lists
        List<CollectionMember<AdditionalExecutorApplying>> execsApplying = createCaseworkerApplyingList(caseData);
        List<CollectionMember<AdditionalExecutorNotApplying>> execsNotApplying =
                createCaseworkerNotApplyingList(caseData);

        // Add primary applicant to list
        if (caseData.isPrimaryApplicantApplying()) {
            execsApplying.add(executorListMapperService.mapFromPrimaryApplicantToApplyingExecutor(caseData));
        } else if (caseData.isPrimaryApplicantNotApplying()) {
            execsNotApplying.add(executorListMapperService.mapFromPrimaryApplicantToNotApplyingExecutor(caseData));
        }

        caseData.setExecutorsApplyingLegalStatement(execsApplying);
        caseData.setExecutorsNotApplyingLegalStatement(execsNotApplying);
    }

    public void formatFields(CaseData caseData) {
        formatDates(caseData);
        formatNames(caseData);
    }

    public void formatDates(CaseData caseData) {
        // Set dispenseWithNoticeLeaveGivenDate format
        if (caseData.getDispenseWithNoticeLeaveGivenDate() != null) {
            caseData.setDispenseWithNoticeLeaveGivenDateFormatted(
                    dateFormatterService.formatDate(caseData.getDispenseWithNoticeLeaveGivenDate()));
        }

        // Set codicilAddedDate format
        if (caseData.getCodicilAddedDateList() != null) {
            List<CollectionMember<String>> formattedCodicilDates = new ArrayList<>();
            caseData.getCodicilAddedDateList().forEach(date -> {
                String formattedDate = dateFormatterService.formatDate(date.getValue().getDateCodicilAdded());
                formattedCodicilDates.add(new CollectionMember<>(formattedDate));
            });
            caseData.setCodicilAddedFormattedDateList(formattedCodicilDates);
        }
    }

    public void formatNames(CaseData caseData) {
        caseData.setDeceasedForenames(FormattingService.capitaliseEachWord(caseData.getDeceasedForenames()));
        caseData.setDeceasedSurname(FormattingService.capitaliseEachWord(caseData.getDeceasedSurname()));
        caseData.setSolsSolicitorFirmName(FormattingService.capitaliseEachWord(caseData.getSolsSolicitorFirmName()));
    }
}
