package br.com.votingsessionmanager.votingsessionmanager.domain.agenda;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.votingsessionmanager.votingsessionmanager.domain.associate.Associate;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "associate_id")
	private Associate associate;

	@Enumerated(EnumType.STRING)
	private VoteType type;

	@SuppressWarnings("unused")
	private Vote() {
	}

	public Vote(Associate associate, VoteType type) {
		this.associate = associate;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public Associate getAssociate() {
		return associate;
	}

	public VoteType getType() {
		return type;
	}

}
