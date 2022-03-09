package br.com.elektron.pedido.response;

import java.util.List;

import br.com.elektron.pedido.model.PedidoVO;

public class ServicePageableResponse<T> {

	public List<PedidoVO> pedidos;

	public ServicePageableResponse() {
		super();
	}

	public ServicePageableResponse(List<PedidoVO> pedidos) {
		super();
		this.pedidos = pedidos;
	}

	public List<PedidoVO> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoVO> pedidos) {
		this.pedidos = pedidos;
	}

}
