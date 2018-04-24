package uk.gov.hmcts.probate.model.ccd.raw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AliasNames {
    @JsonProperty(value = "value")
    private final AliasName aliasName;

    @JsonProperty(value = "id")
    private final String id;

}
