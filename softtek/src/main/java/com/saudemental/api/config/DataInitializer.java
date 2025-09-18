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
                // Perguntas de Estresse
                Question.builder()
                        .text("Como você avalia seu nível de estresse no trabalho atualmente?")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.STRESS_LEVEL)
                        .order(1)
                        .description("Avalie em uma escala de 1 (muito baixo) a 5 (muito alto)")
                        .build(),

                Question.builder()
                        .text("Com que frequência você se sente sobrecarregado com suas tarefas?")
                        .type(QuestionType.FREQUENCY)
                        .category(AssessmentCategory.WORKLOAD)
                        .options(Arrays.asList("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre"))
                        .order(2)
                        .build(),

                // Perguntas de Relacionamento
                Question.builder()
                        .text("Como você avalia o relacionamento com seus colegas de trabalho?")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.CLIMATE_RELATIONSHIP)
                        .order(3)
                        .description("Avalie em uma escala de 1 (muito ruim) a 5 (excelente)")
                        .build(),

                Question.builder()
                        .text("Você sente que pode contar com o apoio de sua liderança?")
                        .type(QuestionType.YES_NO)
                        .category(AssessmentCategory.LEADERSHIP_RELATION)
                        .order(4)
                        .build(),

                // Perguntas de Satisfação
                Question.builder()
                        .text("O quanto você está satisfeito com seu trabalho atual?")
                        .type(QuestionType.EMOJI_SELECTION)
                        .category(AssessmentCategory.WORKPLACE_SATISFACTION)
                        .options(Arrays.asList("😢", "😕", "😐", "🙂", "😊"))
                        .order(5)
                        .build(),

                // Perguntas de Comunicação
                Question.builder()
                        .text("A comunicação em sua equipe é clara e efetiva?")
                        .type(QuestionType.SCALE_1_TO_5)
                        .category(AssessmentCategory.COMMUNICATION)
                        .order(6)
                        .build(),

                // Perguntas de Sinais de Alerta
                Question.builder()
                        .text("Você tem apresentado dificuldades para dormir relacionadas ao trabalho?")
                        .type(QuestionType.FREQUENCY)
                        .category(AssessmentCategory.WARNING_SIGNS)
                        .options(Arrays.asList("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre"))
                        .order(7)
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
                        .targetCategories(Arrays.asList(AssessmentCategory.STRESS_LEVEL))
                        .targetRiskLevels(Arrays.asList(RiskLevel.MODERATE, RiskLevel.HIGH))
                        .displayOrder(2)
                        .tags(Arrays.asList("ansiedade", "respiração", "autocuidado"))
                        .content("1. Inspire profundamente por 4 segundos\n2. Segure por 4 segundos\n3. Expire por 6 segundos\n4. Repita 5 vezes")
                        .build(),

                Resource.builder()
                        .title("Como Melhorar a Comunicação na Equipe")
                        .description("Guia prático para melhorar relacionamentos no trabalho")
                        .type(ResourceType.GUIDANCE)
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
