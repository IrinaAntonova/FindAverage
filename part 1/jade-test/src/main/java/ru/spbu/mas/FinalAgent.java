package ru.spbu.mas;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import jade.core.AID;
import jade.core.Agent;

import javax.print.attribute.SetOfIntegerSyntax;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FinalAgent extends Agent {

    @Override
    protected void setup() {
        int id = Integer.parseInt(getAID().getLocalName()
        );
        addBehaviour(new SendMessage(this));
    }
}
