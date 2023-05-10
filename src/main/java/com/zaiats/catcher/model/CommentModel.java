package com.zaiats.catcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

public class CommentModel {

    @JsonProperty(access = READ_ONLY)
    private Long id;

    @NotBlank(message = "{xxx}")
    @Size(max = 512, message = "{xxx}")
    private String text;

    @NotBlank(message = "{xxx}")
    @Size(max = 64, message = "{xxx}")
    private String author;

    private Instant timestamp;

}
