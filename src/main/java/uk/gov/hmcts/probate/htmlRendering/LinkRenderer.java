package uk.gov.hmcts.probate.htmlRendering;

import static java.lang.String.format;

public class LinkRenderer {
    public static String render(String linkText, String link) {
        return format("<a href=\"%s\" target=\"_blank\" rel=\"noopener noreferrer\" class=\"govuk-link\">%s</a>", link, linkText);
    }
}
