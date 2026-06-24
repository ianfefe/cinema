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
        validarCampo(funcionario.getNome(), "nome");
        validarCampo(funcionario.getSenha(), "senha");
        validarCampo(funcionario.getEmail(), "email");
        validarCampo(funcionario.getTelefone(), "telefone");
        validarCampo(funcionario.getMatricula(), "matricula");
        if (funcionario.getEndereco() == null || funcionario.getEndereco().getId() == null || funcionario.getEndereco().getId() == 0) {
            throw new RegraNegocioException("Endereço inválido");
        }
    }
}