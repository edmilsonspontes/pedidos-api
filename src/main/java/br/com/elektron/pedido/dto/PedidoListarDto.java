package br.com.elektron.pedido.dto;

import java.util.Date;

import br.com.elektron.pedido.model.Status;

public class PedidoListarDto extends PedidoDto {

	public PedidoListarDto() {
		super();
	}

	public PedidoListarDto(Long id, String numero, Date data) {
		super(id, numero, data);
	}

	public PedidoListarDto(String numero, Date data, Status status) {
		super(numero, data);
	}

}
