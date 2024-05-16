package com.kasumov.med_ci.service.entity.economic.impl;

import com.kasumov.med_ci.model.entity.economic.Yet;
import com.kasumov.med_ci.repository.economic.YetRepository;
import com.kasumov.med_ci.service.entity.economic.YetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class YetServiceImpl implements YetService {

    private final YetRepository yetRepository;

    @Override
    public List<Yet> getAll() {
        return yetRepository.findAll();
    }

    @Override
    public Yet getYetById(long yetId) {
        return yetRepository.getYetById(yetId);
    }

    @Override
    public void delete(Yet yet) {
        yetRepository.delete(yet);
    }

    @Override
    public boolean existsById(long yetId) {
        return yetRepository.existsById(yetId);
    }

    @Override
    public Yet findYetById(long yetId) {
        return yetRepository.findYetById(yetId);
    }

    @Override
    public Yet save(Yet yet) {
        return yetRepository.save(yet);
    }

    @Override
    public List<String> getMessagesForYet() {
        List<Yet> allYets = getAll();
        List<Yet> sortedYets = allYets.stream()
                .sorted(Comparator.comparing(Yet::getDayFrom))
                .toList();

        List<Yet> overlappedYets = new ArrayList<>();
        List<Yet> gapYets = new ArrayList<>();

        IntStream.range(0, sortedYets.size() - 1).forEach(i -> {
            Yet currentYet = sortedYets.get(i);
            Yet nextYet = sortedYets.get(i + 1);

            boolean currentYetDayToNotNull = currentYet.getDayTo() != null;
            boolean nextYetDayFromNotNull = nextYet.getDayFrom() != null;

            if (currentYetDayToNotNull && nextYetDayFromNotNull && currentYet.getDayTo().isAfter(nextYet.getDayFrom())) {
                overlappedYets.addAll(Arrays.asList(currentYet, nextYet));
            } else if (!currentYetDayToNotNull || !nextYetDayFromNotNull ||
                    !currentYet.getDayTo().plusDays(1).isEqual(nextYet.getDayFrom())) {
                gapYets.addAll(Arrays.asList(currentYet, nextYet));
            }
        });

        LocalDate minEndDate = sortedYets.stream()
                .map(Yet::getDayTo)
                .reduce(sortedYets.get(0).getDayFrom().minusDays(1),
                        (date1, date2) -> date2 == null ? date1 : date1.isBefore(date2) ? date1 : date2);

        if (!Year.isLeap(minEndDate.getYear())) {
            List<LocalDate> endDates = new ArrayList<>(sortedYets.stream()
                    .map(Yet::getDayTo)
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted()
                    .toList());

            IntStream.range(0, endDates.size() - 1)
                    .filter(i -> endDates.get(i).isEqual(minEndDate))
                    .findFirst()
                    .ifPresent(i -> endDates.set(i + 1, endDates.get(i + 1).plusDays(1)));


            endDates.stream().filter(date -> date.equals(minEndDate)).findFirst().ifPresent(endDate -> endDates.stream()
                    .filter(otherEndDate -> otherEndDate.isAfter(endDate))
                    .findFirst()
                    .ifPresent(nextEndDate -> sortedYets.stream()
                            .filter(yet -> yet.getDayTo() != null && yet.getDayTo().isEqual(endDate) &&
                                    !yet.getDayFrom().isEqual(nextEndDate.plusDays(-1)))
                            .forEach(gapYets::add)));
        }

        List<String> messages = new ArrayList<>();
        if (!overlappedYets.isEmpty()) {
            messages.addAll(overlappedYets.stream()
                    .map(yet -> String.format("Период с %s по %s имеет наложение с другим периодом УЕТ",
                            yet.getDayFrom(), yet.getDayTo()))
                    .toList());
        }

        if (!gapYets.isEmpty()) {
            messages.addAll(gapYets.stream()
                    .map(yet -> String.format("Период с %s по %s имеет разрыв между соседними периодами УЕТ",
                            yet.getDayFrom(), yet.getDayTo()))
                    .toList());
        }

        return messages;
    }

}
