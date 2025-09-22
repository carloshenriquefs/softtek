package br.com.softtek.softtek.adapters.outbound.jpa.entities;

import br.com.softtek.softtek.domain.question.QuestionResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "form_responses")
public class JpaFormResponseEntity {

    @Id
    private String id;
    private String userId;
    private String formId;

    @DBRef(lazy = true)
    private List<QuestionResponse> answers = new ArrayList<>();
    private LocalDateTime createdAt;
}
