package org.oneicy.entity;

import javax.persistence.*;

@Entity
@Table(name = "example_list", schema = "cps_voting_2017")
public class ExampleList {
	private int id;
	private String listName;
	private ExampleItem exampleItemByItemId;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "list_name")
	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExampleList that = (ExampleList) o;

		if (id != that.id) return false;
		if (listName != null ? !listName.equals(that.listName) : that.listName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (listName != null ? listName.hashCode() : 0);
		return result;
	}

	@ManyToOne
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	public ExampleItem getExampleItemByItemId() {
		return exampleItemByItemId;
	}

	public void setExampleItemByItemId(ExampleItem exampleItemByItemId) {
		this.exampleItemByItemId = exampleItemByItemId;
	}
}
