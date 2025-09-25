package br.com.softtek.softtek.adapters.outbound.jpa.entities;

import br.com.softtek.softtek.domain.question.Question;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "forms")
public class MongoFormEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private List<Question> questions = new ArrayList<>();;
}
