package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ufjf.cinema.api.dto.FuncionarioDTO;
import ufjf.cinema.api.dto.UsuarioDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Funcionario;
import ufjf.cinema.model.entity.Usuario;
import ufjf.cinema.model.repository.FuncionarioRepository;
import ufjf.cinema.model.repository.UsuarioRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService extends CrudServiceBase<Funcionario, Long> {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoService enderecoService;
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(JpaRepository<Funcionario, Long> repository, PasswordEncoder passwordEncoder, UsuarioService usuarioService, UsuarioRepository usuarioRepository, EnderecoService enderecoService) {
        super(repository);
        this.funcionarioRepository = (FuncionarioRepository) repository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.enderecoService = enderecoService;
    }

    public List<Funcionario> getFuncionariosByCinema(Cinema cinema) {
        return funcionarioRepository.findByCinema(cinema);
    }

    public Optional<Funcionario> getFuncionarioByMatricula(Integer matricula) {
        return funcionarioRepository.findByMatricula(matricula);
    }

    public Optional<Funcionario> getFuncionarioByNome(String nome) {
        return funcionarioRepository.findByNome(nome);
    }

    @Override
    public void validar(Funcionario funcionario) {
        validarCampo(funcionario.getNome(), "nome");
        validarCampo(funcionario.getEmail(), "email");
        validarCampo(funcionario.getTelefone(), "telefone");
        validarCampo(funcionario.getMatricula(), "matricula");
        if (funcionario.getEndereco() == null || funcionario.getEndereco().getId() == null || funcionario.getEndereco().getId() == 0) {
            throw new RegraNegocioException("Endereço inválido");
        }
    }

    @Transactional
    public Funcionario cadastrarNovoFuncionario(FuncionarioDTO dto) {
        UsuarioDTO userDto = dto.getUsuario();

        Usuario usuario = new Usuario();
        usuario.setLogin(userDto.getLogin());
        usuario.setSenha(passwordEncoder.encode(userDto.getSenha()));
        usuario.setAdmin(true);

        usuarioService.validar(usuario);
        usuario = usuarioRepository.save(usuario);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setMatricula(dto.getMatricula());
        funcionario.setUsuario(usuario);

        this.validar(funcionario);
        return funcionarioRepository.save(funcionario);
    }
}