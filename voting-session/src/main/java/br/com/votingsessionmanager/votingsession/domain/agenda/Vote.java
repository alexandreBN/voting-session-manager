package br.com.votingsessionmanager.votingsession.domain.agenda;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.votingsessionmanager.votingsession.domain.associate.Associate;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vote other = (Vote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
