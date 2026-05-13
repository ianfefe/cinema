package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.TipoAudio;

public interface TipoAudioRepository extends JpaRepository<TipoAudio, Long> {
}
