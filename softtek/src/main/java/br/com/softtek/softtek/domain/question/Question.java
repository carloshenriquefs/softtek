package br.com.softtek.softtek.domain.question;

import br.com.softtek.softtek.domain.question.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String id;
    private String text;
    private QuestionType type;
    private List<String> options = new ArrayList<>();
}
