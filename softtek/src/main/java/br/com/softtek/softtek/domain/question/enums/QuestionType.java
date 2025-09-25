package br.com.softtek.softtek.domain.question.enums;

public enum QuestionType {

    TEXT("text"),
    SCALE("scale"),
    EMOJI("emoji"),
    YES_NO("yes_no"),
    MULTIPLE_CHOICE("multiple_choice");

    private String value;

    private QuestionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
