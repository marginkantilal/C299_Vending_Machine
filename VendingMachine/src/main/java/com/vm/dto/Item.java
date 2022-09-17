package com.vm.dto;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter 
@Setter
public class Item {
	private String itemName;
	private BigDecimal price;
	private int inventoryLevel;
	
	@Override
	public String toString() {
		return "Item [itemName=" + itemName + ", price=" + price + ", inventoryLevel=" + inventoryLevel + "]";
	}

	@Override
    public int hashCode() {
        return Objects.hash(itemName, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.itemName != other.itemName) {
            return false;
        }
        if (!Objects.equals(this.inventoryLevel, other.inventoryLevel)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
       
        return true;
    }
    

}
