package ucsal.edu.com.ContextoAcademico.DTO;

public record AlunoDisciplinaDTO(
        Long id,
        Long alunoId,
        String alunoNome,
        Long disciplinaId,
        Long professorId,
        String semestre
) {}
