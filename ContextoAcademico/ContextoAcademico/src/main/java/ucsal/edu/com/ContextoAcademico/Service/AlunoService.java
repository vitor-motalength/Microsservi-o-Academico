package ucsal.edu.com.ContextoAcademico.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ucsal.edu.com.ContextoAcademico.DTO.AlunoDTO;
import ucsal.edu.com.ContextoAcademico.Entity.Aluno;
import ucsal.edu.com.ContextoAcademico.Exception.ResourceNotFoundException;
import ucsal.edu.com.ContextoAcademico.Repository.AlunoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final WebClient webClient;

    public AlunoService(AlunoRepository alunoRepository, WebClient.Builder webClientBuilder) {
        this.alunoRepository = alunoRepository;

        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080") // <-- URL do outro microserviço (ajuste se necessário)
                .build();
    }

    /**
     * Creates a new student or returns the existing one based on matrícula.
     */
    public AlunoDTO createOrGetAluno(AlunoDTO dto) {

        if (dto.matricula() == null || dto.matricula().trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula do aluno é obrigatória");
        }

        return alunoRepository.findByMatricula(dto.matricula())
                .map(this::toDto)
                .orElseGet(() -> {
                    Aluno novo = new Aluno(dto.matricula(), dto.nome());
                    alunoRepository.save(novo);
                    return toDto(novo);
                });
    }

    /**
     * Example call using WebClient:
     * Gets disciplines from Microservice Escola/Disciplina.
     */
    public String consultarDisciplinasDoAluno(Long alunoId) {

        return webClient.get()
                .uri("/api/disciplinas/aluno/{id}", alunoId)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Pode mudar para async se quiser
    }

    public AlunoDTO getAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com id " + id));
        return toDto(aluno);
    }

    public List<AlunoDTO> listAlunos() {
        return alunoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AlunoDTO toDto(Aluno aluno) {
        return new AlunoDTO(aluno.getId(), aluno.getMatricula(), aluno.getNome());
    }
}