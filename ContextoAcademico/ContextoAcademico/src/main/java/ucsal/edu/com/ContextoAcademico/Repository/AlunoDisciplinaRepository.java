package ucsal.edu.com.ContextoAcademico.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucsal.edu.com.ContextoAcademico.Entity.AlunoDisciplina;

import java.util.List;

@Repository
public interface AlunoDisciplinaRepository extends JpaRepository<AlunoDisciplina, Long> {

    List<AlunoDisciplina> findByAlunoId(Long alunoId);

    List<AlunoDisciplina> findByProfessorId(Long professorId);

    List<AlunoDisciplina> findByDisciplinaId(Long disciplinaId);

    boolean existsByAlunoIdAndDisciplinaIdAndProfessorIdAndSemestre(
            Long alunoId,
            Long disciplinaId,
            Long professorId,
            String semestre
    );
}

