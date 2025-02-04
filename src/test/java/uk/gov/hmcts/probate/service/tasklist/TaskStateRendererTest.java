package uk.gov.hmcts.probate.service.tasklist;

import org.junit.Test;
import uk.gov.hmcts.probate.model.caseprogress.UrlConstants;
import uk.gov.hmcts.probate.model.caseprogress.TaskListState;
import uk.gov.hmcts.probate.model.caseprogress.TaskState;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TaskStateRendererTest {

    @Test
    public void shouldRenderCorrectHtmlForState_CaseCreated() {
        final String testHtml = "<div><addSolicitorLink/></div>\n"
            + "<div><addDeceasedLink/></div>\n"
            + "<div><addAppLink/></div>\n"
            + "<div><rvwLink/></div>\n"
            + "<div><sendDocsLink/>/div>\n"
            + "<div><authDocsLink/></div>\n"
            + "<div><examAppLink/></div>\n"
            + "<div><issueGrantLink/></div>\n"
            + "<p><status-addSolicitor/></p>\n"
            + "<p><status-addDeceasedDetails/></p>\n"
            + "<p><status-addApplicationDetails/></p>\n"
            + "<p><status-reviewAndSubmit/></p>\n"
            + "<p><reviewAndSubmitDate/></p>\n"
            + "<p><status-sendDocuments/></p>\n"
            + "<p><authenticatedDate/></p>\n"
            + "<p><status-authDocuments/></p>\n"
            + "<p><status-examineApp/></p>\n"
            + "<p><status-issueGrant/></p>\n";

        final String expectedHtml = "<div>Add solicitor details</div>\n"
            + "<div><a href=\""
            + UrlConstants.DECEASED_DETAILS_URL_TEMPLATE.replaceFirst("<CASE_ID>", "9999")
            + "\" class=\"govuk-link\">Add deceased details</a></div>\n"
            + "<div>Add application details</div>\n"
            + "<div>Review and sign legal statement and submit application</div>\n"
            + "<div>/div>\n"
            + "<div>Authenticate documents</div>\n"
            + "<div>Examine application</div>\n"
            + "<div>Issue grant of representation<</div>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/not-started.png\" alt=\"NOT STARTED\" title=\"NOT STARTED\" /></p>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\">"
            + "<p><strong>Submitted on 01 Nov 2020</strong></p></div><div class=\"govuk-grid-column-one-third\">"
            + "&nbsp;</div></div>\n</p>\n<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\">"
            + "<p><strong>Authenticated on "
            + "10 Oct 2020</strong></p></div><div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n<p></p>\n<p></p>\n<p></p>\n";

        String result = TaskStateRenderer.renderByReplace(TaskListState.TL_STATE_ADD_DECEASED_DETAILS,
                testHtml, (long) 9999, "WillLeft", "No",
                LocalDate.of(2020,10,10),
                LocalDate.of(2020,11, 1));

        assertEquals(expectedHtml, result);
    }

    @Test
    public void shouldRenderCorrectHtmlForState_AddAppDtls_Gop_UpdateCase() {
        final String testHtml = "<div><addSolicitorLink/></div>\n"
            + "<div><addDeceasedLink/></div>\n"
            + "<div><addAppLink/></div>\n"
            + "<div><rvwLink/></div>\n"
            + "<div><sendDocsLink/>/div>\n"
            + "<div><authDocsLink/></div>\n"
            + "<div><examAppLink/></div>\n"
            + "<div><issueGrantLink/></div>\n"
            + "<p><status-addSolicitor/></p>\n"
            + "<p><status-addDeceasedDetails/></p>\n"
            + "<p><status-addApplicationDetails/></p>\n"
            + "<p><status-reviewAndSubmit/></p>\n"
            + "<p><reviewAndSubmitDate/></p>\n"
            + "<p><status-sendDocuments/></p>\n"
            + "<p><authenticatedDate/></p>\n"
            + "<p><status-authDocuments/></p>\n"
            + "<p><status-examineApp/></p>\n"
            + "<p><status-issueGrant/></p>\n";

        final String expectedHtml = "<div>Add solicitor details</div>\n"
            + "<div>Add deceased details</div>\n"
            + "<div><a href=\""
            + UrlConstants.ADD_APPLICATION_DETAILS_URL_TEMPLATE_GOP.replaceFirst("<CASE_ID>", "9999")
            + "\" class=\"govuk-link\">Add application details</a></div>\n"
            + "<div>Review and sign legal statement and submit application</div>\n"
            + "<div>/div>\n"
            + "<div>Authenticate documents</div>\n"
            + "<div>Examine application</div>\n"
            + "<div>Issue grant of representation<</div>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/in-progress.png\" alt=\"IN PROGRESS\" title=\"IN PROGRESS\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/in-progress.png\" alt=\"IN PROGRESS\" title=\"IN PROGRESS\" /></p>\n"
            + "</p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\">"
            + "<p><strong>Submitted on 01 Nov 2020</strong></p></div>"
            + "<div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\">"
            + "<p><strong>Authenticated on 10 Oct 2020</strong></p></div>"
            + "<div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p></p>\n"
            + "<p></p>\n";

        String result = TaskStateRenderer.renderByReplace(TaskListState.TL_STATE_ADD_APPLICATION_DETAILS,
                testHtml, (long) 9999, "WillLeft", "Yes",
                LocalDate.of(2020,10,10),
                LocalDate.of(2020,11, 1));

        assertEquals(expectedHtml, result);
    }

    @Test
    public void shouldRenderCorrectHtmlForState_AddAppDtls_Gop_NotUpdatingCase() {
        final String testHtml = "<div><addSolicitorLink/></div>\n"
            + "<div><addDeceasedLink/></div>\n"
            + "<div><addAppLink/></div>\n"
            + "<div><rvwLink/></div>\n"
            + "<div><sendDocsLink/>/div>\n"
            + "<div><authDocsLink/></div>\n"
            + "<div><examAppLink/></div>\n"
            + "<div><issueGrantLink/></div>\n"
            + "<p><status-addSolicitor/></p>\n"
            + "<p><status-addDeceasedDetails/></p>\n"
            + "<p><status-addApplicationDetails/></p>\n"
            + "<p><status-reviewAndSubmit/></p>\n"
            + "<p><reviewAndSubmitDate/></p>\n"
            + "<p><status-sendDocuments/></p>\n"
            + "<p><authenticatedDate/></p>\n"
            + "<p><status-authDocuments/></p>\n"
            + "<p><status-examineApp/></p>\n"
            + "<p><status-issueGrant/></p>\n";

        final String expectedHtml = "<div>Add solicitor details</div>\n"
            + "<div>Add deceased details</div>\n"
            + "<div><a href=\""
            + UrlConstants.ADD_APPLICATION_DETAILS_URL_TEMPLATE_GOP.replaceFirst("<CASE_ID>", "9999")
            + "\" class=\"govuk-link\">Add application details</a></div>\n"
            + "<div>Review and sign legal statement and submit application</div>\n"
            + "<div>/div>\n"
            + "<div>Authenticate documents</div>\n"
            + "<div>Examine application</div>\n"
            + "<div>Issue grant of representation<</div>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/not-started.png\" alt=\"NOT STARTED\" title=\"NOT STARTED\" /></p>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\"><p><strong>"
            + "Submitted on 01 Nov 2020"
            + "</strong></p></div><div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\"><p><strong>Authenticated "
            + "on 10 Oct 2020"
            + "</strong></p></div><div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n<p></p>\n<p></p>\n<p></p>\n";

        String result = TaskStateRenderer.renderByReplace(TaskListState.TL_STATE_ADD_APPLICATION_DETAILS,
                testHtml, (long) 9999, "WillLeft", "No",
                LocalDate.of(2020,10,10),
                LocalDate.of(2020,11, 1));

        assertEquals(expectedHtml, result);
    }

    @Test
    public void shouldRenderCorrectHtmlForState_AddAppDtls_Intestacy() {
        final String testHtml = "<div><addSolicitorLink/></div>\n"
            + "<div><addDeceasedLink/></div>\n"
            + "<div><addAppLink/></div>\n"
            + "<div><rvwLink/></div>\n"
            + "<div><sendDocsLink/>/div>\n"
            + "<div><authDocsLink/></div>\n"
            + "<div><examAppLink/></div>\n"
            + "<div><issueGrantLink/></div>\n"
            + "<p><status-addSolicitor/></p>\n"
            + "<p><status-addDeceasedDetails/></p>\n"
            + "<p><status-addApplicationDetails/></p>\n"
            + "<p><status-reviewAndSubmit/></p>\n"
            + "<p><reviewAndSubmitDate/></p>\n"
            + "<p><status-sendDocuments/></p>\n"
            + "<p><authenticatedDate/></p>\n"
            + "<p><status-authDocuments/></p>\n"
            + "<p><status-examineApp/></p>\n"
            + "<p><status-issueGrant/></p>\n";

        final String expectedHtml = "<div>Add solicitor details</div>\n"
            + "<div>Add deceased details</div>\n"
            + "<div><a href=\""
            + UrlConstants.ADD_APPLICATION_DETAILS_URL_TEMPLATE_INTESTACY
                .replaceFirst("<CASE_ID>", "9999")
            + "\" class=\"govuk-link\">Add application details</a></div>\n"
            + "<div>Review and sign legal statement and submit application</div>\n"
            + "<div>/div>\n"
            + "<div>Authenticate documents</div>\n"
            + "<div>Examine application</div>\n"
            + "<div>Issue grant of representation<</div>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/not-started.png\" alt=\"NOT STARTED\" title=\"NOT STARTED\" /></p>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\"><p><strong>Submitted on "
            + "01 Nov 2020"
            + "</strong></p></div><div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\"><p><strong>"
            + "Authenticated on 10 Oct 2020"
            + "</strong></p></div><div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n<p></p>\n<p></p>\n<p></p>\n";

        String result = TaskStateRenderer.renderByReplace(TaskListState.TL_STATE_ADD_APPLICATION_DETAILS,
                testHtml, (long) 9999, "NoWill", null,
                LocalDate.of(2020,10,10),
                LocalDate.of(2020,11, 1));

        assertEquals(expectedHtml, result);
    }

    @Test
    public void shouldRenderCorrectHtmlForState_AddAppDtls_AdmonWill() {
        final String testHtml = "<div><addSolicitorLink/></div>\n"
            + "<div><addDeceasedLink/></div>\n"
            + "<div><addAppLink/></div>\n"
            + "<div><rvwLink/></div>\n"
            + "<div><sendDocsLink/>/div>\n"
            + "<div><authDocsLink/></div>\n"
            + "<div><examAppLink/></div>\n"
            + "<div><issueGrantLink/></div>\n"
            + "<p><status-addSolicitor/></p>\n"
            + "<p><status-addDeceasedDetails/></p>\n"
            + "<p><status-addApplicationDetails/></p>\n"
            + "<p><status-reviewAndSubmit/></p>\n"
            + "<p><reviewAndSubmitDate/></p>\n"
            + "<p><status-sendDocuments/></p>\n"
            + "<p><authenticatedDate/></p>\n"
            + "<p><status-authDocuments/></p>\n"
            + "<p><status-examineApp/></p>\n"
            + "<p><status-issueGrant/></p>\n";

        final String expectedHtml = "<div>Add solicitor details</div>\n"
            + "<div>Add deceased details</div>\n"
            + "<div><a href=\""
            + UrlConstants.ADD_APPLICATION_DETAILS_URL_TEMPLATE_ADMON_WILL
                .replaceFirst("<CASE_ID>", "9999")
            + "\" class=\"govuk-link\">Add application details</a></div>\n"
            + "<div>Review and sign legal statement and submit application</div>\n"
            + "<div>/div>\n"
            + "<div>Authenticate documents</div>\n"
            + "<div>Examine application</div>\n"
            + "<div>Issue grant of representation<</div>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/completed.png\" alt=\"COMPLETED\" title=\"COMPLETED\" /></p>\n"
            + "</p>\n"
            + "<p><p><img align=\"right\" width=\"114px\" height=\"31px\" "
            + "src=\"https://raw.githubusercontent.com/hmcts/probate-back-office/"
            + TaskState.CODE_BRANCH
            + "/src/main/resources/statusImages/not-started.png\" alt=\"NOT STARTED\" title=\"NOT STARTED\" /></p>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\"><p><strong>Submitted on "
            + "01 Nov 2020"
            + "</strong></p></div><div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p><div class=\"govuk-grid-row\"><div class=\"govuk-grid-column-two-thirds\"><p><strong>Authenticated "
            + "on 10 Oct 2020"
            + "</strong></p></div><div class=\"govuk-grid-column-one-third\">&nbsp;</div></div>\n"
            + "</p>\n"
            + "<p></p>\n"
            + "<p></p>\n"
            + "<p></p>\n";

        String result = TaskStateRenderer.renderByReplace(TaskListState.TL_STATE_ADD_APPLICATION_DETAILS,
                testHtml, (long) 9999, "WillLeftAnnexed", "No",
                LocalDate.of(2020,10,10),
                LocalDate.of(2020,11, 1));

        assertEquals(expectedHtml, result);
    }
}
