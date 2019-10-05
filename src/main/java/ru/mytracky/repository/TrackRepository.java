package ru.mytracky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mytracky.model.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
