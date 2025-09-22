package br.com.softtek.softtek.domain.form;

import br.com.softtek.softtek.domain.question.Question;

import java.util.ArrayList;
import java.util.List;

public class Form {

    private String id;
    private String name;
    private String description;
    private List<Question> questions = new ArrayList<>();

    public Form() {
    }

    public Form(String id, String name, String description, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public Form setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Form setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Form setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Form setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }
}
