package ru.spbu.mas;

import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SendMessage extends Behaviour {
    private final FinalAgent agent;

    SendMessage(FinalAgent agent) {
        super(agent);
        this.agent = agent;
           }

    @Override
    public void action() {
        ACLMessage msg = agent.blockingReceive();
        if (msg != null) {
            System.out.println (msg.getContent());
        }
        agent.doDelete();
    }

    @Override
    public boolean done() {
        return true;
    }
}
