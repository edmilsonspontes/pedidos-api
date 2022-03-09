package br.com.elektron.pedido.model;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.elektron.pedido.util.Util;

@Entity
public class PedidoVO {

	@Id
	@GeneratedValue
	private Long id;

	@NonNull
	private String numero;

	@NonNull
	private Date vencimento;

	public PedidoVO() {
		super();
	}

	public PedidoVO(Long id, String numero, Date vencimento) {
		super();
		this.id = id;
		this.numero = numero;
		this.vencimento = Optional.ofNullable(vencimento).orElse(new Date());
	}

	public PedidoVO(String numero, Date vencimento) {
		super();
		this.numero = numero;
		this.vencimento = vencimento; // Optional.ofNullable(vencimento).orElse(new Date());
	}

	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return this.id.toString().concat(" - " + this.numero)
				.concat(this.vencimento != null ? (" - " + Util.getDateHour(this.vencimento)) : "");
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(((PedidoVO) obj).getId().equals(this.id));
	}

}
