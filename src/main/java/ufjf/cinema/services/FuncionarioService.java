package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Funcionario;
import ufjf.cinema.model.repository.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService extends CrudServiceBase<Funcionario, Long>{
    private FuncionarioRepository funcionarioRepository;

    private EnderecoService enderecoService;

    public FuncionarioService(JpaRepository<Funcionario, Long> repository) {
        super(repository);
        this.funcionarioRepository = (FuncionarioRepository) repository;
    }

    public List<Funcionario> getFuncionariosByCinema(Cinema cinema) {
        return funcionarioRepository.findByCinema(cinema);
    }

    public Optional<Funcionario> getFuncionarioByMatricula(Integer matricula){
        return funcionarioRepository.findByMatricula(matricula);
    }

    public Optional<Funcionario> getFuncionarioByNome(String nome){
        return funcionarioRepository.findByNome(nome);
    }

    @Override
    public void validar(Funcionario funcionario){
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (funcionario.getSenha() == null || funcionario.getSenha().trim().isEmpty()) {
            throw new RegraNegocioException("Senha inválida");
        }
        if (funcionario.getEmail() == null || funcionario.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("Email inválido");
        }
        if (funcionario.getTelefone() == null || funcionario.getTelefone().trim().isEmpty()) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if (funcionario.getMatricula() == null || funcionario.getMatricula().trim().isEmpty()) {
            throw new RegraNegocioException("Matricula inválida");
        }
        enderecoService.validar(funcionario.getEndereco());
    }

}
