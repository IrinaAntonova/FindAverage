package ru.spbu.mas;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;
import java.util.concurrent.TimeUnit;


//public class FindAverage extends TickerBehaviour {
//    private final DefaultAgent agent;
//    private int currentStep;
//    private final int MAX_STEPS = 10;
//    FindAverage(DefaultAgent agent, long period) {
//        super(agent, period);
//        this.setFixedPeriod(true);
//        this.agent = agent;
//        this.currentStep = 0;
//    }
//    @Override
//    protected void onTick() {
//        if (currentStep < MAX_STEPS) {
//            System.out.println("Agent " + this.agent.getLocalName() + ": tick=" + getTickCount());
//            this.currentStep++;
//        } else {
//            this.stop();
//        }
//    }
//}

public class FindAverage extends OneShotBehaviour {
    FindAverage(DefaultAgent agent) {
        super(agent);
        this.agent = agent;
    }

    private final DefaultAgent agent;
    private final static int delay = 10;
    private final static float chanesToFall = 0.3f;
    private final static float corruptionRate = 0.05f;

    public void action() {

        for (int i = 0; i < 1000; i++) {
            sendMessage(agent);
            receiveMessage(agent);
        }

        System.out.println(agent.getNumber());
        agent.doDelete();
    }

    private void sendMessage (DefaultAgent agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        for (AID name : agent.agents) {
            float connectionRate = new Random().nextFloat();

            if (connectionRate > chanesToFall) msg.addReceiver(name);
        }

        float error = agent.getNumber() * ((new Random().nextFloat() - 0.5f) * corruptionRate);
        msg.setContent(Float.toString(agent.getNumber() - error));

        int delay = (int)(new Random().nextFloat() * FindAverage.delay);

        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }

        agent.send(msg);
    }

    private void receiveMessage (DefaultAgent agent) {
        ACLMessage msg;
        while ((msg = agent.receive()) != null) {
            if (msg.getPerformative() == ACLMessage.INFORM) {
                float numberFromAgent = Float.parseFloat(msg.getContent());
                float number = agent.getNumber();

                if (numberFromAgent >= number) {
                    continue;
                }
                float average = (number + numberFromAgent) / 2;
                agent.setNumber(average);

                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REQUEST);

                double error = (average - numberFromAgent) * ((Math.random() - 0.5) * corruptionRate);
                reply.setContent(Double.toString(average - numberFromAgent - error));

                //generate a delay in sending messages to 10 milliseconds
                int delay = (int)(Math.random() * FindAverage.delay);

                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }

                agent.send(reply);
            } else {
                if (msg.getPerformative() != ACLMessage.REQUEST) {
                    continue;
                }
                float numberFromAgent = Float.parseFloat(msg.getContent());
                agent.setNumber(agent.getNumber() + numberFromAgent);
            }
        }
    }
}