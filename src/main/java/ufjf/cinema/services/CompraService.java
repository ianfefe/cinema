package ufjf.cinema.services;

import org.springframework.stereotype.Service;
import ufjf.cinema.api.dto.CompraDTO;
import ufjf.cinema.api.dto.IngressoDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.StatusCompra;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.entity.Compra;
import ufjf.cinema.model.entity.Ingresso;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.repository.CompraRepository;
import ufjf.cinema.model.repository.IngressoRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService extends CrudServiceBase<Compra, Long>{
    private final CompraRepository compraRepository;
    private final IngressoRepository ingressoRepository;
    private final SessaoService sessaoService;
    private final CinemaService cinemaService;
    private final ClienteService clienteService;
    private final AssentoService assentoService;
    private final TipoIngressoService tipoIngressoService;
    private final IngressoService ingressoService;

    public CompraService(CompraRepository compraRepository,
                         IngressoRepository ingressoRepository,
                         SessaoService sessaoService,
                         AssentoService assentoService,
                         CinemaService cinemaService,
                         ClienteService clienteService,
                         TipoIngressoService tipoIngressoService, IngressoService ingressoService) {
        super(compraRepository);
        this.compraRepository = compraRepository;
        this.ingressoRepository = ingressoRepository;
        this.sessaoService = sessaoService;
        this.assentoService = assentoService;
        this.cinemaService = cinemaService;
        this.clienteService = clienteService;
        this.tipoIngressoService = tipoIngressoService;
        this.ingressoService = ingressoService;
    }

    @Transactional
    public Compra realizarCompra(CompraDTO dto) {
        Compra compra = new Compra();
        compra.setStatus(StatusCompra.PENDENTE);
        compra.setDataHora(dto.getDataHora());
        compra.setFormaPagamento(dto.getFormaPagamento());
        compra.setCinema(cinemaService.findById(dto.getCinemaId())
                .orElseThrow(() -> new RegraNegocioException("Cinema não encontrado")));
        compra.setCliente(clienteService.findById(dto.getClienteId())
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado")));

        List<Ingresso> ingressos = new ArrayList<>();

        for (IngressoDTO ingDto : dto.getIngressos()) {
            Ingresso ingresso = new Ingresso();

            Sessao sessao = sessaoService.findById(ingDto.getIdSessao())
                    .orElseThrow(() -> new RegraNegocioException("Sessão não encontrada"));

            Assento assento = assentoService.findById(ingDto.getIdAssento())
                    .orElseThrow(() -> new RegraNegocioException("Assento não encontrado"));

            if (ingressoRepository.existsBySessaoAndAssento(ingresso.getSessao(), ingresso.getAssento())) {
                throw new RegraNegocioException("O assento " + ingresso.getAssento().getPosicao() + " já está ocupado para esta sessão.");
            }

            ufjf.cinema.model.entity.TipoIngresso tipo = tipoIngressoService.findById(ingDto.getIdTipoIngresso())
                    .orElseThrow(() -> new RegraNegocioException("Tipo de ingresso não encontrado"));

            ingresso.setSessao(sessao);
            ingresso.setAssento(assento);
            ingresso.setTipoIngresso(tipo);
            ingresso.setCompra(compra);

            ingressos.add(ingresso);
        }

        compra.setIngressos(ingressos);

        Compra compraSalva = super.salvar(compra);

        for (Ingresso ing : ingressos) {
            ing.setCompra(compraSalva);
            ingressoService.salvar(ing);
        }

        return compraSalva;
    }

    @Transactional
    public Compra cancelarCompra(Compra idCompra) {
        Compra compra = this.findById(idCompra.getId())
                .orElseThrow(() -> new RegraNegocioException("Compra não encontrada"));

        if (StatusCompra.CANCELADA.equals(compra.getStatus())) {
            throw new RegraNegocioException("Esta compra já se encontra cancelada.");
        }

        compra.setStatus(StatusCompra.CANCELADA);

        List<Ingresso> ingressos = ingressoRepository.getIngressosByCompra(compra);
        if (ingressos != null && !ingressos.isEmpty()) {
            ingressoRepository.deleteAll(ingressos);
        }

        compra.setIngressos(new ArrayList<>());
        compra.setTotal(BigDecimal.ZERO);

        return compraRepository.save(compra);
    }

    @Override
    public void validar (Compra compra){

        if (this.findById(compra.getId()).get().getStatus().equals("CONCLUIDA") || this.findById(compra.getId()).get().getStatus().equals("CANCELADA")) {
            throw new RegraNegocioException("A compra foi finalizada e não pode ser atualizada");
        };

        validarCampo(compra.getDataHora(),  "dataHora");
        validarCampo(compra.getFormaPagamento(),  "formaPagamento");
        validarEntidade(compra.getCinema(), "cinema");

        try{
        validarEntidade(compra.getCliente(), "cliente");
        }catch(RegraNegocioException e){
            try{
                validarEntidade(compra.getFuncionario(), "funcionario");
            }catch(RegraNegocioException ex){
                throw new RegraNegocioException(e.getMessage());
            }
        }

        if (compra.getIngressos() != null && !compra.getIngressos().isEmpty()) {
            for (Ingresso ingresso : compra.getIngressos()) {
                Long idCinemaIngresso = ingresso.getSessao().getSala().getCinema().getId();
                Long idCinemaCompra = compra.getCinema().getId();

                if (!idCinemaIngresso.equals(idCinemaCompra)) {
                    throw new RegraNegocioException("Existem ingressos que não pertencem ao cinema selecionado nesta compra.");
                }
            }
        } else {
            throw new RegraNegocioException("Uma compra deve conter pelo menos um ingresso.");
        }

        compra.setTotal(this.getValorCompra(compra));
    }

    public List<Ingresso> getIngressosByCompra(Compra  compra){
        return ingressoRepository.getIngressosByCompra(compra);
    }

    public BigDecimal getValorCompra(Compra compra) {
        BigDecimal valor = BigDecimal.ZERO;

        List<Ingresso> ingressos = compra.getIngressos();

        if (ingressos == null || ingressos.isEmpty()) {
            throw new RegraNegocioException("A compra não possui nenhum ingresso");
        }

        for (Ingresso ingresso : ingressos) {

            BigDecimal precoBase = ingresso.getSessao().getPrecoBase();
            String tipo = ingresso.getTipoIngresso().getTipo();

            if ("MEIA-ENTRADA".equalsIgnoreCase(tipo)) {
                valor = valor.add(precoBase.divide(new BigDecimal("2.0"), 2, RoundingMode.HALF_UP));
            } else if ("INTEIRA".equalsIgnoreCase(tipo)) {
                valor = valor.add(precoBase);
            } else {
                throw new RegraNegocioException("Tipo de ingresso desconhecido: " + tipo);
            }
        }
        return valor;
    }
}
