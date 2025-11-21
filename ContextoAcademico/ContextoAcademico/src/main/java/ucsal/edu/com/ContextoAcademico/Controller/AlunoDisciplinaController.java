package ucsal.edu.com.ContextoAcademico.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.edu.com.ContextoAcademico.DTO.AlunoDisciplinaDTO;
import ucsal.edu.com.ContextoAcademico.Service.AlunoDisciplinaService;

import java.util.List;

@RestController
@RequestMapping("/api/aluno-disciplinas")
public class AlunoDisciplinaController {

    private final AlunoDisciplinaService alunoDisciplinaService;

    public AlunoDisciplinaController(AlunoDisciplinaService alunoDisciplinaService) {
        this.alunoDisciplinaService = alunoDisciplinaService;
    }

    /**
     * Associates a student with a discipline taught by a professor in a given
     * semester.
     */
    @PostMapping
    public ResponseEntity<AlunoDisciplinaDTO> associate(@RequestParam Long alunoId,
                                                        @RequestParam Long disciplinaId,
                                                        @RequestParam Long professorId,
                                                        @RequestParam String semestre) {
        AlunoDisciplinaDTO dto =
                alunoDisciplinaService.associateAlunoDisciplina(alunoId, disciplinaId, professorId, semestre);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<AlunoDisciplinaDTO> listByAluno(@PathVariable Long alunoId) {
        return alunoDisciplinaService.listByAluno(alunoId);
    }

    @GetMapping("/professor/{professorId}")
    public List<AlunoDisciplinaDTO> listByProfessor(@PathVariable Long professorId) {
        return alunoDisciplinaService.listByProfessor(professorId);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public List<AlunoDisciplinaDTO> listByDisciplina(@PathVariable Long disciplinaId) {
        return alunoDisciplinaService.listByDisciplina(disciplinaId);
    }
}
