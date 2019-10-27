package com.px.tool.model.response;

import com.px.tool.model.AbstractObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse extends AbstractObject {
    private String code;
    private String message;
}
