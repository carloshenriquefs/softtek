package com.saudemental.api.config;

import com.saudemental.api.model.entity.Question;
import com.saudemental.api.model.entity.Resource;
import com.saudemental.api.model.entity.User;
import com.saudemental.api.model.enums.*;
import com.saudemental.api.repository.QuestionRepository;
import com.saudemental.api.repository.ResourceRepository;
import com.saudemental.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ResourceRepository resourceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializeQuestions();
        initializeResources();
        log.info("Dados iniciais carregados com sucesso");
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .email("admin@empresa.com")
                    .name("Administrador do Sistema")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .status(UserStatus.ACTIVE)
                    .department("TI")
                    .employeeId("ADM001")
                    .consentGivenAt(LocalDateTime.now())
                    .dataRetentionUntil(LocalDateTime.now().plusYears(7))
                    .build();

            User hr = User.builder()
                    .email("rh@empresa.com")
                    .name("Recursos Humanos")
                    .password(passwordEncoder.encode("rh123"))
                    .role(UserRole.HR)
                    .status(UserStatus.ACTIVE)
                    .department("RH")
                    .employeeId("RH001")
                    .consentGivenAt(LocalDateTime.now())
                    .dataRetentionUntil(LocalDateTime.now().plusYears(7))
                    .build();

            User employee = User.builder()
                    .email("funcionario@empresa.com")
                    .name("Funcionário Teste")
                    .password(passwordEncoder.encode("func123"))
                    .role(UserRole.EMPLOYEE)
                    .status(UserStatus.ACTIVE)
                    .department("Operações")
                    .employeeId("EMP001")
                    .consentGivenAt(LocalDateTime.now())
                    .dataRetentionUntil(LocalDateTime.now().plusYears(7))
                    .build();

            userRepository.saveAll(Arrays.asList(admin, hr, employee));
            log.info("Usuários iniciais criados");
        }
    }

    private void initializeQuestions() {
        if (questionRepository.count() == 0) {
            List<Question> questions = Arrays.asList(
                Question.builder()
                        .text("Escolha o seu emoji de hoje!")
                        .type(QuestionType.EMOJI_SELECTION)
                        .category(AssessmentCategory.RISK_MAPPING)
                        .options(Arrays.asList("😢", "😕", "😐", "🙂", "😊"))
                        .order(1)
                        .build(),

                Question.builder()
                        .text("Como você se sente hoje?")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.RISK_MAPPING)
                        .options(Arrays.asList("Motivado", "Cansado", "Procupado", "Estressado", "Animado"))
                        .order(2)
                        .build(),

                Question.builder()
                        .text("Como você avalia sua carga de trabalho?")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.WORKLOAD)
                        .options(Arrays.asList("Muito Leve", "Leve", "Média", "Alta", "Muito Alta"))
                        .order(3)
                        .build(),

                Question.builder()
                        .text("Sua carga de trabalho afeta sua qualidade de vida?")
                        .type(QuestionType.FREQUENCY)
                        .category(AssessmentCategory.WORKLOAD)
                        .options(Arrays.asList("Não", "Raramente", "Ás vezes", "Frequentemente", "Sempre"))
                        .order(4)
                        .build(),

                Question.builder()
                        .text("Você trabalha além do seu horário regular?")
                        .type(QuestionType.FREQUENCY)
                        .category(AssessmentCategory.WORKLOAD)
                        .options(Arrays.asList("Não", "Raramente", "Ás vezes", "Frequentemente", "Sempre"))
                        .order(5)
                        .build(),

                Question.builder()
                        .text("Você tem apresentado sintomas como insônia, irritabilidade ou cansaço extremo?")
                        .type(QuestionType.FREQUENCY)
                        .category(AssessmentCategory.WARNING_SIGNS)
                        .options(Arrays.asList("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre"))
                        .order(6)
                        .build(),

                Question.builder()
                        .text("Você sente que sua saúde mental prejudica sua produtividade no trabalho?")
                        .type(QuestionType.FREQUENCY)
                        .category(AssessmentCategory.WARNING_SIGNS)
                        .options(Arrays.asList("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre"))
                        .order(7)
                        .build(),

                Question.builder()
                        .text("Como está o seu relacionamento com seu chefe?")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(8)
                        .description("Avalie em uma escala de 1 (muito ruim) a 5 (excelente)")
                        .build(),

                Question.builder()
                        .text("Como está o seu relacionamento com seus colegas de trabalho?")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(9)
                        .description("Avalie em uma escala de 1 (muito ruim) a 5 (excelente)")
                        .build(),

                Question.builder()
                        .text("Sinto que sou tratado(a) com respeito pelos meus colegas de trabalho.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(10)
                        .description("Avalie em uma escala de 1 (nunca) a 5 (sempre)")
                        .build(),

                Question.builder()
                        .text("Consigo me relacionar de forma saudável e colaborativa com minha equipe.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(11)
                        .description("Avalie em uma escala de 1 (nunca) a 5 (sempre)")
                        .build(),

                Question.builder()
                        .text("Tenho liberdade para expressar minhas opiniões sem medo de retaliações.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(12)
                        .description("Avalie em uma escala de 1 (nunca) a 5 (sempre)")
                        .build(),

                Question.builder()
                        .text("Me sinto acolhido(a) a parte do time onde trabalho.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(13)
                        .description("Avalie em uma escala de 1 (nunca) a 5 (sempre)")
                        .build(),

                Question.builder()
                        .text("Sinto que existe espírito de cooperação entre os colaboradores.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(14)
                        .description("Avalie em uma escala de 1 (nunca) a 5 (sempre)")
                        .build(),

                Question.builder()
                        .text("Recebo orientações claras e objetivas sobre minhas atividades e responsabilidades.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.COMMUNICATION)
                        .order(15)
                        .build(),

                Question.builder()
                        .text("Sinto que posso me comunicar abertamente com minha liderança.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.COMMUNICATION)
                        .order(16)
                        .build(),

                Question.builder()
                        .text("As informações importantes circulam de forma eficiente dentro da empresa.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.COMMUNICATION)
                        .order(17)
                        .build(),

                Question.builder()
                        .text("Tenho clareza sobre as metas e os resultados esperados de mim.")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.COMMUNICATION)
                        .order(18)
                        .build(),

                Question.builder()
                        .text("Minha liderança demonstra interesse pelo meu bem-estar no trabalho")
                        .type(QuestionType.YES_NO)
                        .category(AssessmentCategory.LEADERSHIP_RELATION)
                        .order(19)
                        .build(),

                Question.builder()
                        .text("Minha liderança está disponível para me ouvir quando necessário.")
                        .type(QuestionType.YES_NO)
                        .category(AssessmentCategory.LEADERSHIP_RELATION)
                        .order(20)
                        .build(),

                Question.builder()
                        .text("Me sinto confortável para reportar problemas ou dificuldades ao meu líder")
                        .type(QuestionType.YES_NO)
                        .category(AssessmentCategory.LEADERSHIP_RELATION)
                        .order(21)
                        .build(),

                Question.builder()
                        .text("Minha liderança reconhece minhas entregas e esforços")
                        .type(QuestionType.YES_NO)
                        .category(AssessmentCategory.LEADERSHIP_RELATION)
                        .order(22)
                        .build(),

                Question.builder()
                        .text("Existe confiança e transparência na relação com minha liderança")
                        .type(QuestionType.YES_NO)
                        .category(AssessmentCategory.LEADERSHIP_RELATION)
                        .order(23)
                        .build()


            );

            questionRepository.saveAll(questions);
            log.info("Perguntas iniciais criadas");
        }
    }

    private void initializeResources() {
        if (resourceRepository.count() == 0) {
            List<Resource> resources = Arrays.asList(
                Resource.builder()
                        .title("Centro de Valorização da Vida (CVV)")
                        .description("Suporte emocional e prevenção do suicídio")
                        .type(ResourceType.HOTLINE)
                        .link("https://www.cvv.org.br")
                        .featured(true)
                        .targetRiskLevels(Arrays.asList(RiskLevel.HIGH, RiskLevel.CRITICAL))
                        .displayOrder(1)
                        .tags(Arrays.asList("emergência", "suporte", "24h"))
                        .content("Ligue 188 - Atendimento 24h, gratuito e sigiloso")
                        .build(),

                Resource.builder()
                        .title("Técnicas de Respiração para Ansiedade")
                        .description("Exercícios simples para controlar a ansiedade no trabalho")
                        .type(ResourceType.ARTICLE)
                        .link("https://drauziovarella.uol.com.br/psiquiatria/tecnicas-de-respiracao-para-aliviar-a-ansiedade/")
                        .targetCategories(Arrays.asList(AssessmentCategory.STRESS_LEVEL))
                        .targetRiskLevels(Arrays.asList(RiskLevel.MODERATE, RiskLevel.HIGH))
                        .displayOrder(2)
                        .tags(Arrays.asList("ansiedade", "respiração", "autocuidado"))
                        .content("1. Inspire profundamente por 4 segundos\n2. Segure por 4 segundos\n3. Expire por 6 segundos\n4. Repita 5 vezes")
                        .build(),

                Resource.builder()
                        .title("Como Melhorar a Comunicação na Equipe")
                        .description("Guia prático para melhorar relacionamentos no trabalho")
                        .type(ResourceType.VIDEO)
                        .link("https://www.youtube.com/watch?v=w-_aoGK-LE0")
                        .targetCategories(Arrays.asList(AssessmentCategory.COMMUNICATION, AssessmentCategory.CLIMATE_RELATIONSHIP))
                        .displayOrder(3)
                        .tags(Arrays.asList("comunicação", "equipe", "relacionamento"))
                        .build(),

                Resource.builder()
                        .title("Canal de Denúncias da Empresa")
                        .description("Canal seguro e anônimo para reportar situações inadequadas")
                        .type(ResourceType.FORM)
                        .link("https://empresa.com/denuncias")
                        .targetRiskLevels(Arrays.asList(RiskLevel.HIGH, RiskLevel.CRITICAL))
                        .displayOrder(4)
                        .tags(Arrays.asList("denúncia", "segurança", "anônimo"))
                        .build(),

                Resource.builder()
                        .title("Contato RH - Suporte ao Funcionário")
                        .description("Entre em contato com o RH para suporte e orientações")
                        .type(ResourceType.CONTACT)
                        .link("mailto:rh@empresa.com?subject=Suporte%20Saude%20Mental&body=Preciso%20com%20meu%20emocional")
                        .content("Email: rh@empresa.com\nTelefone: (11) 1234-5678\nHorário: 8h às 17h")
                        .displayOrder(5)
                        .tags(Arrays.asList("RH", "suporte", "orientação"))
                        .build()
            );

            resourceRepository.saveAll(resources);
            log.info("Recursos iniciais criados");
        }
    }
}
