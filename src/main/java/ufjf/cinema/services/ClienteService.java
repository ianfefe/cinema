package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ufjf.cinema.api.dto.ClienteDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cliente;
import ufjf.cinema.model.entity.Usuario;
import ufjf.cinema.model.repository.ClienteRepository;
import ufjf.cinema.model.repository.UsuarioRepository;

import javax.transaction.Transactional;

@Service
public class ClienteService extends CrudServiceBase<Cliente, Long> {
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public ClienteService(JpaRepository<Cliente, Long> repository, PasswordEncoder passwordEncoder, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        super(repository);
        this.clienteRepository = (ClienteRepository) repository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public void validar(Cliente cliente) {
        validarCampo(cliente.getEmail(), "email");
        validarCampo(cliente.getNome(), "nome");
        validarCampo(cliente.getTelefone(), "telefone");
    }

    @Transactional
    public Cliente cadastrarNovoCliente(ClienteDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getUsuario().getLogin());
        usuario.setSenha(passwordEncoder.encode(dto.getUsuario().getSenha()));
        usuario.setAdmin(false);

        usuarioService.validar(usuario);
        usuario = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setUsuario(usuario);

        if (clienteRepository.existsByCpfAndIdNot(cliente.getCpf(), cliente.getId())) {
            throw new RegraNegocioException("Já existe um cliente cadastrado com este CPF.");
        }

        this.validar(cliente);
        return clienteRepository.save(cliente);
    }
}
