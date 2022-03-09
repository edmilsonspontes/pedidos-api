package br.com.elektron.pedido.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.elektron.pedido.dto.PedidoDto;
import br.com.elektron.pedido.dto.PedidoListarDto;
import br.com.elektron.pedido.dto.VencimentoPedidoDto;
import br.com.elektron.pedido.model.PedidoVO;
import br.com.elektron.pedido.model.Status;
import br.com.elektron.pedido.repository.PedidoRepository;
import br.com.elektron.pedido.repository.StatusRepository;
import br.com.elektron.pedido.response.ServicePageableResponse;
import br.com.elektron.pedido.response.ServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Pedido API")
@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private StatusRepository statusRepository;

	@ApiOperation(value = "Consulta pedido por ID", response = ResponseEntity.class, tags = "consultar")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pedido encontrado"),
			@ApiResponse(code = 404, message = "Pedido não encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<ServiceResponse<PedidoVO>> consultar(@PathVariable(value = "id") Long idPedido) {
		PedidoVO pedido = pedidoRepository.findById(idPedido).orElse(null);
		if(pedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ServiceResponse<PedidoVO> serviceResponse = new ServiceResponse<PedidoVO>(pedido);
		return new ResponseEntity<ServiceResponse<PedidoVO>>(serviceResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos pedidos", response = ResponseEntity.class, tags = "listarTodos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pedidos encontrados"),
			@ApiResponse(code = 404, message = "Nenhum pedido foi encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@GetMapping
	public ResponseEntity<ServicePageableResponse<List<PedidoVO>>> listarTodos() {
		List pedidos = (List) pedidoRepository.findAll();
		if(pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ServicePageableResponse<List<PedidoVO>> servicePageableResponse = new ServicePageableResponse<List<PedidoVO>>(pedidos);
		return new ResponseEntity<ServicePageableResponse<List<PedidoVO>>>(servicePageableResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos pedidos correspondentes ao exemplo", response = ResponseEntity.class, tags = "listar")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pedidos encontrados"),
			@ApiResponse(code = 404, message = "Nenhum pedido foi encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@PostMapping(value = "/list")
	public ResponseEntity<ServicePageableResponse<List<PedidoVO>>> listar( @RequestBody PedidoListarDto pedidoListarDto) {
		Example<PedidoVO> pedidoExample = Example.of(new PedidoVO(pedidoListarDto.getNumero(), pedidoListarDto.getVencimento())); 
		List pedidos = (List) pedidoRepository.findAll(pedidoExample);
		if(pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ServicePageableResponse<List<PedidoVO>> servicePageableResponse = new ServicePageableResponse<List<PedidoVO>>(pedidos);
		return new ResponseEntity<ServicePageableResponse<List<PedidoVO>>>(servicePageableResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "Cria um novo pedido e cria respectivo status (NOVO)", response = ResponseEntity.class, tags = "criar")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Pedido criado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@PostMapping
	public ResponseEntity<ServiceResponse<Void>>criar(@RequestBody PedidoDto pedidoDto) {
        PedidoVO pedido = new PedidoVO(pedidoDto.getNumero(), pedidoDto.getVencimento());
        pedido = pedidoRepository.save(pedido);
        
        if(pedido != null) {
        	Status status = new Status("NOVO", new Date(), pedido);
        	status = statusRepository.save(status);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<ServiceResponse<Void>>(new ServiceResponse<>(pedido), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Altera um pedido e cria status (ALTERACAO)", response = ResponseEntity.class, tags = "alterar")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pedido alterado"),
			@ApiResponse(code = 404, message = "Pedido não encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@PutMapping(value = "/{id}")
	public ResponseEntity<ServiceResponse<PedidoVO>> alterar(@PathVariable(value = "id") Long idPedido, @RequestBody PedidoVO pedido) {
		PedidoVO pedidoBase = pedidoRepository.findById(idPedido).orElse(null);
		if(pedidoBase == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		PedidoVO pedidoUpdate = new PedidoVO(idPedido, pedido.getNumero(), pedido.getVencimento());
		pedidoUpdate = pedidoRepository.save(pedidoUpdate);
		
        if(pedidoUpdate != null) {
        	Status status = new Status("ALTERACAO", new Date(), pedido);
        	status = statusRepository.save(status);
        }

		ServiceResponse<PedidoVO> serviceResponse = new ServiceResponse<PedidoVO>(pedidoUpdate);
		return new ResponseEntity<ServiceResponse<PedidoVO>>(serviceResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Exclui um pedido e cria status (EXCLUSAO)", response = ResponseEntity.class, tags = "excluir")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pedido excluído"),
			@ApiResponse(code = 404, message = "Pedido não encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@DeleteMapping(value = "/{id}")
	 public ResponseEntity<ServiceResponse<Void>> excluir(@PathVariable(value = "id") Long idPedido) {
		PedidoVO pedidoBase = pedidoRepository.findById(idPedido).orElse(null);
		if(pedidoBase == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
        	Status status = new Status("ALTERACAO", new Date(), pedidoBase);
        	status = statusRepository.save(status);
			pedidoRepository.delete(pedidoBase);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

	@ApiOperation(value = "Atualiza status do pedido (cria novo status)", response = ResponseEntity.class, tags = "atualizarStatus")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Status do pedido alterado (criado)"),
			@ApiResponse(code = 404, message = "Pedido não encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@PatchMapping(value = "/status/{idPedido}")
	public ResponseEntity<ServiceResponse<PedidoVO>> atualizarStatus(@PathVariable(value = "idPedido") Long idPedido, @RequestBody Status status) {
		PedidoVO pedidoBase = pedidoRepository.findById(idPedido).orElse(null);
        if(pedidoBase == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Status newStatus = new Status(status.getNome(), status.getData() != null ? status.getData() : new Date(), pedidoBase);
        newStatus = statusRepository.save(newStatus);
		
		ServiceResponse<PedidoVO> serviceResponse = new ServiceResponse<PedidoVO>(pedidoBase);
		return new ResponseEntity<ServiceResponse<PedidoVO>>(serviceResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "Atualiza data de vencimento do pedido e cria status (ALTERACAO_VENCIMENTO)", response = ResponseEntity.class, tags = "atualizarVencimentoPedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Data de vencimento do pedido atualizada"),
			@ApiResponse(code = 404, message = "Pedido não encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@PatchMapping(value = "/vencimento/{idPedido}")
	public ResponseEntity<ServiceResponse<Void>> atualizarVencimentoPedido(@PathVariable(value = "idPedido") Long idPedido, 
			@RequestBody VencimentoPedidoDto vencimentoPedidoDto) {
		
        if(vencimentoPedidoDto == null || vencimentoPedidoDto.getVencimento() == null) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

		PedidoVO pedido = pedidoRepository.findById(idPedido).orElse(null);
        if(pedido == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        pedido.setVencimento(vencimentoPedidoDto.getVencimento());
        pedido = pedidoRepository.save(pedido);
    	Status status = new Status("ALTERACAO_VENCIMENTO", new Date(), pedido);
    	status = statusRepository.save(status);
		
    	return new ResponseEntity<ServiceResponse<Void>>(new ServiceResponse<>(pedido), HttpStatus.OK);
	}

	@ApiOperation(value = "Lista todos os status de todos os pedidos", response = ResponseEntity.class, tags = "status")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Status encontrados"),
			@ApiResponse(code = 404, message = "Nenhum status foi encontrado"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@GetMapping(value = "/status/list")
	public ResponseEntity<ServicePageableResponse<List<Status>>> listarStatus() {
		List status = (List) statusRepository.findAll();
		if(status.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ServicePageableResponse<List<Status>> servicePageableResponse = new ServicePageableResponse<List<Status>>(status);
		return new ResponseEntity<ServicePageableResponse<List<Status>>>(servicePageableResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos os status do pedido", response = ResponseEntity.class, tags = "listarStatusPorPedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Status encontrados"),
			@ApiResponse(code = 404, message = "Nenhum status foi encontrado para o pedido"),
			@ApiResponse(code = 400, message = "Requisição inválida"),
			@ApiResponse(code = 502, message = "Erro interno na aplcação") })
	@GetMapping(value = "/status/list/{idPedido}")
	public ResponseEntity<ServicePageableResponse<List<Status>>> listarStatusPorPedido(@PathVariable(value = "idPedido") Long idPedido) {
		PedidoVO pedido = pedidoRepository.findById(idPedido).orElse(null);
		if(pedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Example<Status> statusExample = Example.of(new Status(null, null, pedido)); 
		List statusList = (List) statusRepository.findAll(statusExample);
		if(statusList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ServicePageableResponse<List<Status>> servicePageableResponse = new ServicePageableResponse<List<Status>>(statusList);
		return new ResponseEntity<ServicePageableResponse<List<Status>>>(servicePageableResponse, HttpStatus.OK);
	}

	
}