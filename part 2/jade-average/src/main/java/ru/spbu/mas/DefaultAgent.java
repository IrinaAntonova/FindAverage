package ru.spbu.mas;
import jade.core.Agent;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import jade.core.*;
import java.util.ArrayList;


public class DefaultAgent extends Agent{
    protected ArrayList<AID> agents = new ArrayList<AID>();
    private float number;

    protected float getNumber() {
        return this.number;
    }

    protected void setNumber(float newNumber) {
        this.number = newNumber;
    }

    protected void setup() {
        number = new Random().nextFloat();
        System.out.println("Agent " + getAID().getName() + " " + number);
        Object[] args = getArguments();
        for (Object o: args)
        {
            agents.add(new AID((String)o, AID.ISLOCALNAME));
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }

        addBehaviour(new FindAverage(this));
    }
}