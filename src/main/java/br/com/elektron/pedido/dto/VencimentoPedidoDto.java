package br.com.elektron.pedido.dto;

import java.util.Date;

public class VencimentoPedidoDto {

	private Date vencimento;

	public VencimentoPedidoDto() {
		super();
	}

	public VencimentoPedidoDto(Date vencimento) {
		super();
		this.vencimento = vencimento;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

}
