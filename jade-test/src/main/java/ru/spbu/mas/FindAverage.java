package ru.spbu.mas;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class FindAverage extends Behaviour {
    private final DefaultAgent agent;
    private float average=0;
    private int currentStep;
    private final int MAX_STEPS = 10;
    FindAverage(DefaultAgent agent) {
        super(agent);
        this.agent = agent;
        this.currentStep = 0;
    }
    private int step=0;
    @Override
    public void action() {
        while (true) {
            ACLMessage msgSent = new ACLMessage(ACLMessage.INFORM);
            for (AID reciever: this.agent.getOutputAgents()) {
                msgSent.addReceiver(reciever);
            }
            msgSent.setContent(this.agent.getLastValue());
            this.agent.send(msgSent);


            ACLMessage msgRec = this.agent.receive();
            if (msgRec == null || msgRec.getPerformative() != ACLMessage.INFORM) continue;
            if (!msgRec.getContent().equals("It's the end")) {
                this.agent.setLastValue(msgRec.getContent());
                if (this.agent.getCountOfIndexes()==6) {
                    average= this.agent.getAverage();
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    for (AID reciever: this.agent.getInputAgents()) {
                        msg.addReceiver(reciever);
                    }
                    msg.setContent("It's the end");
                    this.agent.send(msg);
                    ACLMessage aver = new ACLMessage(ACLMessage.INFORM);
                    aver.addReceiver(this.agent.getFinalAgent());
                    aver.setContent(Float.toString(average));
                    this.agent.send(aver);
                    this.agent.doDelete();
                }
            } else {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                for (AID reciever: this.agent.getInputAgents()) {
                    msg.addReceiver(reciever);
                }
                msg.setContent("It's the end");
                this.agent.send(msg);
                this.agent.doDelete();
                return;
            }
        }
    }

    @Override
    public boolean done() {

        return true;
    }
}