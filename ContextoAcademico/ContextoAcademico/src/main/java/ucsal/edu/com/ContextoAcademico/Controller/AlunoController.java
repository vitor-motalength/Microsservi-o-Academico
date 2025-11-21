package ucsal.edu.com.ContextoAcademico.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ucsal.edu.com.ContextoAcademico.DTO.AlunoDTO;
import ucsal.edu.com.ContextoAcademico.Service.AlunoService;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> create(@Validated @RequestBody AlunoDTO dto) {
        AlunoDTO aluno = alunoService.createOrGetAluno(dto);
        return new ResponseEntity<>(aluno, HttpStatus.CREATED);
    }

    @GetMapping
    public List<AlunoDTO> list() {
        return alunoService.listAlunos();
    }

    @GetMapping("/{id}")
    public AlunoDTO get(@PathVariable Long id) {
        return alunoService.getAluno(id);
    }
}

