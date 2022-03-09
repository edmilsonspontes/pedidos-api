package br.com.elektron.pedido.response;

import br.com.elektron.pedido.model.PedidoVO;

public class ServiceResponse<T> {

	public PedidoVO pedido;

	public ServiceResponse(PedidoVO pedido) {
		super();
		this.pedido = pedido;
	}

	public ServiceResponse() {
		super();
	}

	public PedidoVO getPedido() {
		return pedido;
	}

	public void setPedido(PedidoVO pedido) {
		this.pedido = pedido;
	}

}
