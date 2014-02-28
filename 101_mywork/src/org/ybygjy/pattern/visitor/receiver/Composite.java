package org.ybygjy.pattern.visitor.receiver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ybygjy.pattern.visitor.Equipment;
import org.ybygjy.pattern.visitor.Visitor;

public abstract class Composite implements Equipment {
    private List<Equipment> composites = new ArrayList<Equipment>();
    public void add(Equipment composite) {
        composites.add(composite);
    }
    public void accept(Visitor visitor) {
        for (Iterator<Equipment> iterator = composites.iterator(); iterator.hasNext();) {
            Equipment equipment = iterator.next();
            equipment.accept(visitor);
        }
    }

    public double price() {
        double rtnValue = 0D;
        for (Iterator<Equipment> iterator = this.composites.iterator(); iterator.hasNext();) {
            rtnValue += iterator.next().price();
        }
        return rtnValue;
    }
}
