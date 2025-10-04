package com.saudemental.api.service;
import com.saudemental.api.model.dto.MoodEntryRequest;
import com.saudemental.api.model.dto.MoodEntryResponse;
import com.saudemental.api.model.entity.MoodEntry;
import com.saudemental.api.model.enums.AuditAction;
import com.saudemental.api.repository.MoodEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class MoodService {
    private final MoodEntryRepository moodEntryRepository;
    private final AuditService auditService;

    public MoodEntryResponse saveMoodEntry(String userId, MoodEntryRequest request, String ipAddress) {
        MoodEntry moodEntry = MoodEntry.builder()
                .userId(userId)
                .emoji(request.getEmoji())
                .feeling(request.getFeeling())
                .note(request.getNote())
                .date(LocalDateTime.now())
                .deviceInfo(request.getDeviceInfo())
                .ipAddress(ipAddress)
                .dataRetentionUntil(LocalDateTime.now().plusYears(7)) // NR-1 compliance
                .build();

        moodEntry = moodEntryRepository.save(moodEntry);

        // Auditoria
        auditService.logAction(userId, AuditAction.CREATE, "mood_entry",
                moodEntry.getId(), ipAddress, request.getDeviceInfo());

        log.info("Mood entry saved for user: {}", userId);

        return convertToResponse(moodEntry);
    }

    public List<MoodEntryResponse> getUserMoodEntries(String userId) {
        return moodEntryRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<MoodEntryResponse> getRecentMoodEntries(String userId, int limit) {
        return moodEntryRepository.findByUserIdOrderByDateDesc(userId, PageRequest.of(0, limit))
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<MoodEntryResponse> getMoodEntriesByDateRange(String userId,
                                                             LocalDateTime startDate, LocalDateTime endDate) {
        return moodEntryRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public long getUserMoodCount(String userId, LocalDateTime since) {
        return moodEntryRepository.countByUserIdAndDateAfter(userId, since);
    }

    private MoodEntryResponse convertToResponse(MoodEntry entry) {
        return MoodEntryResponse.builder()
                .id(entry.getId())
                .emoji(entry.getEmoji())
                .feeling(entry.getFeeling())
                .note(entry.getNote())
                .date(entry.getDate())
                .build();
    }
}
