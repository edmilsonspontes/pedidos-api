package br.com.elektron.pedido.dto;

import java.util.Date;

import br.com.elektron.pedido.model.Status;

public class PedidoDto {
	private Long id;
	private String numero;
	private Date vencimento;

	public PedidoDto() {
		super();
	}
	
	public PedidoDto(String numero, Date vencimento) {
		super();
		this.numero = numero;
		this.vencimento = vencimento;
	}

	public PedidoDto(Long id, String numero, Date vencimento) {
		super();
		this.id = id;
		this.numero = numero;
		this.vencimento = vencimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	
}
