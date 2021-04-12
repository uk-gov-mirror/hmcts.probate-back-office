package uk.gov.hmcts.probate.model.ccd.raw.request;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import uk.gov.hmcts.probate.model.ccd.raw.AdditionalExecutorNotApplyingPowerReserved;
import uk.gov.hmcts.probate.model.ccd.raw.AdditionalExecutorPartners;
import uk.gov.hmcts.probate.model.ccd.raw.AdditionalExecutorTrustCorps;
import uk.gov.hmcts.probate.model.ccd.raw.CodicilAddedDate;
import uk.gov.hmcts.probate.model.ccd.raw.CollectionMember;
import uk.gov.hmcts.probate.model.ccd.raw.SolsAddress;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@SuperBuilder
@Data
public class CaseDataParent {

    protected final String schemaVersion;
    protected final String registrySequenceNumber;
    protected final String deceasedDeathCertificate;
    protected final String deceasedDiedEngOrWales;
    protected final String deceasedForeignDeathCertInEnglish;
    protected final String deceasedForeignDeathCertTranslation;
    protected final String solsForenames;
    protected final String solsSurname;
    protected final String solsSolicitorWillSignSOT;
    protected final String dispenseWithNotice;
    protected final String titleAndClearingType;
    protected final String titleAndClearingTypeNoT;
    protected final String trustCorpName;
    protected SolsAddress trustCorpAddress;
    // Not final so field can be reset in CaseDataTransformer
    protected List<CollectionMember<AdditionalExecutorTrustCorps>> additionalExecutorsTrustCorpList;
    protected final String lodgementAddress;
    protected final LocalDate lodgementDate;
    protected final String nameOfFirmNamedInWill;
    protected final String nameOfSucceededFirm;
    protected final String isPractitionerAnExecutor;
    // Not final so field can be reset in CaseDataTransformer
    protected List<CollectionMember<AdditionalExecutorPartners>> otherPartnersApplyingAsExecutors;
    protected final String morePartnersHoldingPowerReserved;
    protected final String dispenseWithNoticeLeaveGiven;
    protected final String dispenseWithNoticeLeaveGivenDate;
    protected final String dispenseWithNoticeOverview;
    protected final String dispenseWithNoticeSupportingDocs;
    // Not final so field can be reset in CaseDataTransformer
    protected List<CollectionMember<AdditionalExecutorNotApplyingPowerReserved>> dispenseWithNoticeOtherExecsList;
    protected final String soleTraderOrLimitedCompany;
    protected final List<String> whoSharesInCompanyProfits;
    protected final String solsIdentifiedApplyingExecs;
    protected final String solsIdentifiedNotApplyingExecs;
    protected final String iht217;
    protected final String noOriginalWillAccessReason;
    protected final LocalDate originalWillSignedDate;
    protected final List<CollectionMember<CodicilAddedDate>> codicilAddedDateList;

    @Getter
    protected LocalDate authenticatedDate;
}
