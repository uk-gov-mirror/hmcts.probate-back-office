package uk.gov.hmcts.probate.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.probate.insights.AppInsights;
import uk.gov.hmcts.probate.model.ccd.raw.Document;
import uk.gov.hmcts.probate.model.ccd.raw.request.CallbackRequest;
import uk.gov.hmcts.probate.service.DocumentService;
import uk.gov.hmcts.probate.service.template.pdf.PDFManagementService;
import uk.gov.hmcts.probate.util.TestUtils;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.probate.model.DocumentType.ADMON_WILL_GRANT_DRAFT;
import static uk.gov.hmcts.probate.model.DocumentType.ADMON_WILL_GRANT;
import static uk.gov.hmcts.probate.model.DocumentType.DIGITAL_GRANT;
import static uk.gov.hmcts.probate.model.DocumentType.DIGITAL_GRANT_DRAFT;
import static uk.gov.hmcts.probate.model.DocumentType.EDGE_CASE;
import static uk.gov.hmcts.probate.model.DocumentType.INTESTACY_GRANT_DRAFT;
import static uk.gov.hmcts.probate.model.DocumentType.INTESTACY_GRANT;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestUtils testUtils;

    @MockBean
    private PDFManagementService pdfManagementService;

    @SpyBean
    private DocumentService documentService;

    @MockBean
    private AppInsights appInsights;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        when(pdfManagementService.generateAndUpload(Mockito.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT)))
                .thenReturn(Document.builder().documentType(DIGITAL_GRANT_DRAFT).build());
        when(pdfManagementService.generateAndUpload(Mockito.any(CallbackRequest.class), eq(DIGITAL_GRANT)))
                .thenReturn(Document.builder().documentType(DIGITAL_GRANT).build());
        when(pdfManagementService.generateAndUpload(Mockito.any(CallbackRequest.class), eq(INTESTACY_GRANT_DRAFT)))
                .thenReturn(Document.builder().documentType(INTESTACY_GRANT_DRAFT).build());
        when(pdfManagementService.generateAndUpload(Mockito.any(CallbackRequest.class), eq(INTESTACY_GRANT)))
                .thenReturn(Document.builder().documentType(INTESTACY_GRANT).build());
        when(pdfManagementService.generateAndUpload(Mockito.any(CallbackRequest.class), eq(ADMON_WILL_GRANT_DRAFT)))
                .thenReturn(Document.builder().documentType(ADMON_WILL_GRANT_DRAFT).build());
        when(pdfManagementService.generateAndUpload(Mockito.any(CallbackRequest.class), eq(ADMON_WILL_GRANT)))
                .thenReturn(Document.builder().documentType(ADMON_WILL_GRANT).build());
        when(pdfManagementService.generateAndUpload(Mockito.any(CallbackRequest.class), eq(EDGE_CASE)))
                .thenReturn(Document.builder().documentType(EDGE_CASE).build());
    }

    @Test
    public void generateGrantDraftGrantOfRepresentation() throws Exception {

        String solicitorPayload = testUtils.getStringFromFile("solicitorPayloadNotifications.json");

        mockMvc.perform(post("/document/generate-grant-draft")
                .content(solicitorPayload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("data")));

        doNothing().when(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT));

        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(ADMON_WILL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(INTESTACY_GRANT_DRAFT));
    }

    @Test
    public void generateGrantDraftIntestacy() throws Exception {

        String solicitorPayload = testUtils.getStringFromFile("solicitorPayloadNotificationsIntestacy.json");

        mockMvc.perform(post("/document/generate-grant-draft")
                .content(solicitorPayload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("data")));

        doNothing().when(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(INTESTACY_GRANT_DRAFT));

        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(ADMON_WILL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(INTESTACY_GRANT_DRAFT));
    }

    @Test
    public void generateGrantDraftAdmonWill() throws Exception {

        String solicitorPayload = testUtils.getStringFromFile("solicitorPayloadNotificationsAdmonWill.json");

        mockMvc.perform(post("/document/generate-grant-draft")
                .content(solicitorPayload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("data")));

        doNothing().when(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(ADMON_WILL_GRANT_DRAFT));

        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(ADMON_WILL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(INTESTACY_GRANT_DRAFT));
    }

    @Test
    public void shouldNotGenerateGrantEdgeCase() throws Exception {

        String solicitorPayload = testUtils.getStringFromFile("solicitorPayloadNotificationsEdgeCase.json");

        mockMvc.perform(post("/document/generate-grant-draft")
                .content(solicitorPayload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("data")));

        doNothing().when(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(EDGE_CASE));

        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(ADMON_WILL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(INTESTACY_GRANT_DRAFT));
    }

    @Test
    public void shouldGenerateGrantDefaultCaseType() throws Exception {

        String solicitorPayload = testUtils.getStringFromFile("solicitorPayloadNotificationsDefaultCase.json");

        mockMvc.perform(post("/document/generate-grant-draft")
                .content(solicitorPayload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("data")));

        doNothing().when(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(EDGE_CASE));

        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(ADMON_WILL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(DIGITAL_GRANT_DRAFT));
        verify(documentService).expire(ArgumentMatchers.any(CallbackRequest.class), eq(INTESTACY_GRANT_DRAFT));
    }

}