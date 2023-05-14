package com.zaiats.catcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zaiats.catcher.repository.entity.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

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

    @JsonProperty(access = READ_ONLY)
    private Instant timestamp = Instant.now();

    public CommentModel() {
    }

    public CommentModel(Long id, String text, String author, Instant timestamp) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.timestamp = timestamp;
    }

    public static CommentModel fromEntity(Comment comment) {
        return new CommentModel(
                comment.getId(),
                comment.getText(),
                comment.getAuthor(),
                comment.getTimestamp());
    }

    public static Comment toEntity(CommentModel commentModel) {
        Comment comment = new Comment();
        comment.setText(commentModel.getText());
        comment.setAuthor(commentModel.getAuthor());
        comment.setTimestamp(commentModel.getTimestamp());
        return comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentModel that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(text, that.text)
                && Objects.equals(author, that.author)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, author, timestamp);
    }

}
