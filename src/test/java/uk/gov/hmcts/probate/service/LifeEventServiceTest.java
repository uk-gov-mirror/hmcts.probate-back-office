package uk.gov.hmcts.probate.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.lifeevents.client.model.Deceased;
import uk.gov.hmcts.lifeevents.client.model.V1Death;
import uk.gov.hmcts.lifeevents.client.service.DeathService;
import uk.gov.hmcts.probate.model.ccd.raw.request.CaseData;
import uk.gov.hmcts.probate.model.ccd.raw.request.CaseDetails;
import uk.gov.hmcts.probate.security.SecurityUtils;
import uk.gov.hmcts.probate.service.ccd.CcdClientApi;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LifeEventService.class)

public class LifeEventServiceTest {

    @Autowired
    LifeEventService lifeEventService;

    @MockBean
    DeathService deathService;
    @MockBean
    CcdClientApi ccdClientApi;
    @MockBean
    SecurityUtils securityUtils;

    @Mock
    CaseDetails caseDetails;

    @Mock
    CaseData caseData;

    @Test
    public void verifyDeathRecord() {

        final String firstName = "Wibble";
        final String lastName = "Wobble";
        final LocalDate localDate = LocalDate.now();

        Deceased deceased = new Deceased();
        deceased.setForenames("Firstname");
        deceased.setSurname("LastName");
        deceased.setSex(Deceased.SexEnum.INDETERMINATE);
        V1Death v1Death = new V1Death();
        v1Death.setDeceased(deceased);
        List<V1Death> deathRecords = new ArrayList<>();
        deathRecords.add(v1Death);

        when(caseDetails.getData()).thenReturn(caseData);
        when(caseData.getDeceasedForenames()).thenReturn(firstName);
        when(caseData.getDeceasedSurname()).thenReturn(lastName);
        when(caseData.getDeceasedDateOfDeath()).thenReturn(localDate);
        when(deathService.searchForDeathRecordsByNamesAndDate(any(), any(), any())).thenReturn(deathRecords);
        lifeEventService.verifyDeathRecord(caseDetails);
        verify(deathService, times(1)).searchForDeathRecordsByNamesAndDate(eq("Wibble"), eq("Wobble"), eq(localDate));
    }
}
