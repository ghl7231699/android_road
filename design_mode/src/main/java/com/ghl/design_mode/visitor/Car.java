package com.ghl.design_mode.visitor;

import java.util.ArrayList;
import java.util.List;

class Car {
    private List<Visitable> visit = new ArrayList<>();

    public void addVisit(Visitable visitable) {
        visit.add(visitable);
    }

    public void show(Visitor visitor) {
        for (Visitable v :
                visit) {
            v.accept(visitor);
        }
    }
}
