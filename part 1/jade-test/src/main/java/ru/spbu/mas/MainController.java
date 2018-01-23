package ru.spbu.mas;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

class MainController {
    private static final int numberOfAgents = 6;

    void initAgents() {
// Retrieve the singleton instance of the JADE Runtime
        Runtime rt = Runtime.instance();
//Create a container to host the Default Agent
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "10098");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createMainContainer(p);
        java.lang.String[][] top = new String[6][];
        top[0] = new String[] {"3", "5", "6", "|", "2", "4"};
        top[1] = new String[] {"1", "|", "3"};
        top[2] = new String[] {"2", "|", "1", "4"};
        top[3] = new String[] {"1", "3", "|", "5"};
        top[4] = new String[] {"4", "6", "|", "1"};
        top[5] = new String[] {"|", "1", "5"};
        try {
            for (int i = 1; i <= MainController.numberOfAgents; i++) {
                AgentController agent = cc.createNewAgent
                        (Integer.toString(i), "ru.spbu.mas.DefaultAgent", top[i - 1]);
                agent.start();
            }
            AgentController finalAgent = cc.createNewAgent
                    ("7", "ru.spbu.mas.FinalAgent", null);
            finalAgent.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}