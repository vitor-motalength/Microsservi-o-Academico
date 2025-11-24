package ucsal.edu.com.ContextoAcademico.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aluno_disciplina")
public class AlunoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Aluno aluno;

    @Column(nullable = false)
    private Long disciplinaId;

    @Column(nullable = false)
    private Long professorId;

    @Column(nullable = false)
    private String semestre;

    public AlunoDisciplina() {}

    public AlunoDisciplina(Aluno aluno, Long disciplinaId, Long professorId, String semestre) {
        this.aluno = aluno;
        this.disciplinaId = disciplinaId;
        this.professorId = professorId;
        this.semestre = semestre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}
