package ucsal.edu.com.ContextoAcademico.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucsal.edu.com.ContextoAcademico.DTO.AlunoDisciplinaDTO;
import ucsal.edu.com.ContextoAcademico.Entity.Aluno;
import ucsal.edu.com.ContextoAcademico.Entity.AlunoDisciplina;
import ucsal.edu.com.ContextoAcademico.Repository.AlunoDisciplinaRepository;
import ucsal.edu.com.ContextoAcademico.Repository.AlunoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlunoDisciplinaService {

    private final AlunoDisciplinaRepository alunoDisciplinaRepository;
    private final AlunoRepository alunoRepository;

    public AlunoDisciplinaService(AlunoDisciplinaRepository alunoDisciplinaRepository,
                                  AlunoRepository alunoRepository) {
        this.alunoDisciplinaRepository = alunoDisciplinaRepository;
        this.alunoRepository = alunoRepository;
    }

    /**
     * Associa um aluno à disciplina (IDs vindos dos outros microsserviços).
     */
    public AlunoDisciplinaDTO associateAlunoDisciplina(
            Long alunoId,
            Long disciplinaId,
            Long professorId,
            String semestre) {

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id " + alunoId));

        // Verifica duplicata
        boolean exists = alunoDisciplinaRepository
                .existsByAlunoIdAndDisciplinaIdAndProfessorIdAndSemestre(
                        alunoId, disciplinaId, professorId, semestre
                );

        if (exists) {
            // Retorna o já existente
            AlunoDisciplina existing = alunoDisciplinaRepository.findAll().stream()
                    .filter(ad -> ad.getAluno().getId().equals(alunoId) &&
                            ad.getDisciplinaId().equals(disciplinaId) &&
                            ad.getProfessorId().equals(professorId) &&
                            ad.getSemestre().equals(semestre))
                    .findFirst()
                    .orElseThrow();
            return toDto(existing);
        }

        AlunoDisciplina ad = new AlunoDisciplina(
                aluno,
                disciplinaId,
                professorId,
                semestre
        );

        alunoDisciplinaRepository.save(ad);
        return toDto(ad);
    }

    public List<AlunoDisciplinaDTO> listByAluno(Long alunoId) {
        return alunoDisciplinaRepository.findByAlunoId(alunoId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<AlunoDisciplinaDTO> listByProfessor(Long professorId) {
        return alunoDisciplinaRepository.findByProfessorId(professorId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<AlunoDisciplinaDTO> listByDisciplina(Long disciplinaId) {
        return alunoDisciplinaRepository.findByDisciplinaId(disciplinaId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private AlunoDisciplinaDTO toDto(AlunoDisciplina ad) {
        return new AlunoDisciplinaDTO(
                ad.getId(),
                ad.getAluno().getId(),
                ad.getAluno().getNome(),
                ad.getDisciplinaId(),
                ad.getProfessorId(),
                ad.getSemestre()
        );
    }
}
