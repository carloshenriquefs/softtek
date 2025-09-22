package br.com.softtek.softtek.domain.form;

import br.com.softtek.softtek.domain.question.QuestionResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FormResponse {

    private String id;
    private String userId;
    private String formId;

    private List<QuestionResponse> answers = new ArrayList<>();
    private LocalDateTime createdAt;

    public FormResponse() {
    }

    public FormResponse(String id, String userId, String formId, List<QuestionResponse> answers, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.formId = formId;
        this.answers = answers;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public FormResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public FormResponse setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getFormId() {
        return formId;
    }

    public FormResponse setFormId(String formId) {
        this.formId = formId;
        return this;
    }

    public List<QuestionResponse> getAnswers() {
        return answers;
    }

    public FormResponse setAnswers(List<QuestionResponse> answers) {
        this.answers = answers;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public FormResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
