package com.saudemental.api.controller;

import com.saudemental.api.model.entity.Resource;
import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.ResourceType;
import com.saudemental.api.model.enums.RiskLevel;
import com.saudemental.api.repository.ResourceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
@Tag(name = "Recursos", description = "Endpoints para recursos de saúde mental")
public class ResourceController {

    private final ResourceRepository resourceRepository;

    @GetMapping
    @Operation(summary = "Listar recursos", description = "Retorna lista de recursos disponíveis")
    public ResponseEntity<List<Resource>> getResources(
            @RequestParam(required = false) ResourceType type,
            @RequestParam(required = false) AssessmentCategory category,
            @RequestParam(required = false) RiskLevel riskLevel,
            @RequestParam(required = false, defaultValue = "false") Boolean featured) {

        List<Resource> resources;

        if (featured) {
            resources = resourceRepository.findByActiveAndFeaturedOrderByDisplayOrder(true, true);
        } else if (type != null) {
            resources = resourceRepository.findByActiveAndTypeOrderByDisplayOrder(true, type);
        } else if (category != null) {
            resources = resourceRepository.findByActiveAndTargetCategoriesContainingOrderByDisplayOrder(true, category);
        } else if (riskLevel != null) {
            resources = resourceRepository.findByActiveAndTargetRiskLevelsContainingOrderByDisplayOrder(true, riskLevel);
        } else {
            resources = resourceRepository.findByActiveOrderByDisplayOrder(true);
        }

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{resourceId}")
    @Operation(summary = "Obter recurso específico", description = "Retorna detalhes de um recurso específico")
    public ResponseEntity<Resource> getResource(@PathVariable String resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        // Incrementar contador de visualizações
        resource.setViewCount(resource.getViewCount() + 1);
        resourceRepository.save(resource);

        return ResponseEntity.ok(resource);
    }
}
