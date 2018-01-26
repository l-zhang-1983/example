package org.oneicy.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "example_item", schema = "cps_voting_2017")
public class ExampleItem {
	private int id;
	private String itemName;
	private Collection<ExampleList> exampleListsById;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "item_name")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExampleItem that = (ExampleItem) o;

		if (id != that.id) return false;
		if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "exampleItemByItemId")
	public Collection<ExampleList> getExampleListsById() {
		return exampleListsById;
	}

	public void setExampleListsById(Collection<ExampleList> exampleListsById) {
		this.exampleListsById = exampleListsById;
	}
}
